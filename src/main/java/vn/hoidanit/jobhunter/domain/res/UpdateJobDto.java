package vn.hoidanit.jobhunter.domain.res;

import vn.hoidanit.jobhunter.utils.Enum.LevelEnum;

import java.time.Instant;
import java.util.List;

public class UpdateJobDto {
    private long id;
    private String name;
    private double salary;
    private int quantity;
    private LevelEnum level;

    private String location;


    private Instant startDate;
    private Instant endDate;
    private boolean Active;

    private Instant updatedAt;


    private String updatedBy;
    private List<String> skills;

    public UpdateJobDto(String updatedBy, List<String> skills, Instant updatedAt, boolean active, Instant startDate, Instant endDate,
                        String location, LevelEnum level, int quantity, double salary, String name, long id) {
        this.updatedBy = updatedBy;
        this.skills = skills;
        this.updatedAt = updatedAt;
        Active = active;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.level = level;
        this.quantity = quantity;
        this.salary = salary;
        this.name = name;
        this.id = id;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updateBy) {
        this.updatedBy = updateBy;
    }

    public UpdateJobDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
