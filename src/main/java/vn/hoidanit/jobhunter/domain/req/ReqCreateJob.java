package vn.hoidanit.jobhunter.domain.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.Skills;
import vn.hoidanit.jobhunter.utils.Enum.LevelEnum;

import java.time.Instant;
import java.util.List;

public class ReqCreateJob {

    private String name;
    private double salary;
    private int quantity;
    private LevelEnum level;
    private String description;
    private String location;
    private Instant startDate;
    private Instant endDate;
    private boolean Active;
    private companyData company;
    private List<skill_id> skills;

    public static class companyData {
        private String id;
        private String name;
        private String logo;

        public companyData(String name, String id, String logo) {
            this.name = name;
            this.id = id;
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }

    public companyData getCompany() {
        return company;
    }

    public void setCompany(companyData company) {
        this.company = company;
    }

    public List<skill_id> getSkills() {
        return skills;
    }

    public void setSkills(List<skill_id> skills) {
        this.skills = skills;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
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

    public static class skill_id {
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
