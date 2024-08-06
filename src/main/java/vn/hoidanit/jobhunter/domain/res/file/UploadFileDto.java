package vn.hoidanit.jobhunter.domain.res.file;

import java.time.Instant;

public class UploadFileDto {
    private String fileName;
    private Instant uploadedAt;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
