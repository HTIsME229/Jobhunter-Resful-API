package vn.hoidanit.jobhunter.domain.res;

import java.util.List;

public class RestEmailJob {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SkillEmail> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillEmail> skills) {
        this.skills = skills;
    }

    public CompanyEmail getCompany() {
        return company;
    }

    public void setCompany(CompanyEmail company) {
        this.company = company;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    private String name;
    private double salary;
    private CompanyEmail company;
    private List<SkillEmail> skills;

    public static class CompanyEmail {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class SkillEmail {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
