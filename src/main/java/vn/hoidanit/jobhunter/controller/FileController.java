package vn.hoidanit.jobhunter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.hoidanit.jobhunter.domain.res.file.UploadFileDto;
import vn.hoidanit.jobhunter.service.FileService;
import vn.hoidanit.jobhunter.utils.annotation.ApiMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FileController {
    private final FileService fileService;
    @Value("${hoidanit.upload-file.base-path}")
    private String basePath;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/files")
    @ApiMessage("Upload File Success")
    public ResponseEntity<UploadFileDto> UpdloadFile(@RequestParam(name = "file", required = false) MultipartFile file, @RequestParam("folder") String folder) throws URISyntaxException {
        // validate file
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (!allowedExtensions.contains(extension)) {
            throw new RuntimeException("Unsupported file format " + extension + " only allow " + allowedExtensions);
        }
        // create folder if not exits
        this.fileService.createUploadFolder(basePath + folder);
        //upload file
        try {
            fileName = this.fileService.store(file, folder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UploadFileDto uploadFileDto = new UploadFileDto();
        uploadFileDto.setFileName(fileName);
        uploadFileDto.setUploadedAt(Instant.now());
        return ResponseEntity.ok().body(uploadFileDto);

    }

    @GetMapping(path = "/download")
    public ResponseEntity<Resource> download(@RequestParam("fileName") String fileName, @RequestParam("folder") String folder) throws URISyntaxException, FileNotFoundException, IOException {

        URI uri = new URI(basePath + folder + "/" + fileName);


        Path path = Paths.get(uri);
//        File file = path.toFile();
        File tmpFile = path.toFile();
        if (!tmpFile.exists() || !tmpFile.isFile()) {
            throw new RuntimeException("File not found");
        }
        File file = Files.exists(path) ? path.toFile() : null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename=" + fileName);

        String mimeType = Files.probeContentType(path);


        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);

    }
}
