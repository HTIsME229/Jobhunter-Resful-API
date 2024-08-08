package vn.hoidanit.jobhunter.domain.res;

import java.time.Instant;

public class UpdateResumeDto {
    private Instant updatedAt;
    private String updatedBy;

    public UpdateResumeDto(String updatedBy, Instant updatedAt) {
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
