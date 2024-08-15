package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Resume;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.req.reqCreateResume;
import vn.hoidanit.jobhunter.domain.res.*;
import vn.hoidanit.jobhunter.repository.JobRepository;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.service.ResumeService;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.utils.annotation.ApiMessage;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;

    }

    @ApiMessage("Create Resume Success")
    @PostMapping("/resumes")
    public ResponseEntity<CreateResumeDto> CreateResume(@RequestBody @Valid reqCreateResume resume) {

        CreateResumeDto res = this.resumeService.handleCreateResume(resume);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @ApiMessage("Update Resume Success")
    @PutMapping("/resumes")
    public ResponseEntity<UpdateResumeDto> UpdateResume(@RequestBody Resume resume) {
        UpdateResumeDto res = this.resumeService.handleUpdate(resume);
        return ResponseEntity.ok(res);
    }

    @ApiMessage("Delete Resume Success")
    @DeleteMapping("/resumes/{id}")
    public ResponseEntity<Void> DeleteResume(@PathVariable long id) {
        this.resumeService.handleDelete(id);
        return ResponseEntity.ok().build();
    }

    @ApiMessage("Fetch Resume Success")
    @GetMapping("/resumes/{id}")
    public ResponseEntity<GetResumeDto> GetResumesById(@PathVariable long id) {
        GetResumeDto res = this.resumeService.handleGetResume(id);
        return ResponseEntity.ok(res);
    }

    @ApiMessage("Fetch Resume Success")
    @GetMapping("/resumes")
    public ResponseEntity<RestPaginateDTO> GetResumesWithPaginate(Pageable pageable) {

        RestPaginateDTO res = this.resumeService.handleGetResumeWithPaginate(pageable);
        return ResponseEntity.ok(res);
    }

    @ApiMessage("Fetch Resume Success")
    @PostMapping("/resumes/by-user")
    public ResponseEntity<RestPaginateDTO> GetResumesWithPaginateByUser(Pageable pageable) {
        RestPaginateDTO res = this.resumeService.handleGetResumeWithPaginateByUser(pageable);
        return ResponseEntity.ok(res);
    }


}
