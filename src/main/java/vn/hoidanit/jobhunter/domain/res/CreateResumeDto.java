package vn.hoidanit.jobhunter.domain.res;

import java.time.Instant;

public class CreateResumeDto {
    private long id;
    private Instant createdAt;
    private String createdBy;

    public CreateResumeDto() {
    }

    public CreateResumeDto(String createdBy, Instant createdAt, long id) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
