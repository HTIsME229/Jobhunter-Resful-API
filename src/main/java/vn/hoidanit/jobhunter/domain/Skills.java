package vn.hoidanit.jobhunter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import vn.hoidanit.jobhunter.utils.SecurityUtil;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "skills")
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    @JsonIgnore
    @ManyToMany(mappedBy = "skills")
    private List<Job> jobs;
    @ManyToMany(mappedBy = "skills")
    private List<Subscriber> subscribers;

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void handleCreate() {

        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        this.createdAt = Instant.now();

    }

    @PreUpdate
    public void handleUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public String getUpdateBy() {
        return updatedBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updatedBy = updateBy;
    }

    public String getCreateBy() {
        return createdBy;
    }

    public void setCreateBy(String createBy) {
        this.createdBy = createBy;
    }

    public Instant getUpdateAt() {
        return updatedAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updatedAt = updateAt;
    }

    public Instant getCreateAt() {
        return createdAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createdAt = createAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
