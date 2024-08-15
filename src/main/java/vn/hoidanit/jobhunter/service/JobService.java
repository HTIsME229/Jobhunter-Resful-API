package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Skills;
import vn.hoidanit.jobhunter.domain.req.ReqCreateJob;
import vn.hoidanit.jobhunter.domain.req.ReqUpdateJob;
import vn.hoidanit.jobhunter.domain.res.CreateJobDto;
import vn.hoidanit.jobhunter.domain.res.UpdateJobDto;
import vn.hoidanit.jobhunter.repository.CompanyRepository;
import vn.hoidanit.jobhunter.repository.JobRepository;
import vn.hoidanit.jobhunter.repository.SkillsRepository;
import vn.hoidanit.jobhunter.service.Specfication.SpecificationsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    private JobRepository jobRepository;
    private SkillsRepository skillsRepository;
    private CompanyRepository companyRepository;
    private SpecificationsBuilder specificationsBuilder;

    public JobService(JobRepository jobRepository, SkillsRepository skillsRepository, CompanyRepository companyRepository, SpecificationsBuilder specificationsBuilder) {
        this.jobRepository = jobRepository;
        this.skillsRepository = skillsRepository;
        this.companyRepository = companyRepository;
        this.specificationsBuilder = specificationsBuilder;
    }

    public CreateJobDto handleCreate(ReqCreateJob datajob) {
        Job newJob = new Job();
        List<ReqCreateJob.skill_id> skill_Id_List = datajob.getSkills();
        List<Skills> skillsList = new ArrayList<Skills>();
        List<String> skillResList = new ArrayList<>();
        for (ReqCreateJob.skill_id skillId : skill_Id_List) {
            Optional<Skills> currentSkill = this.skillsRepository.findById(skillId.getId());

            if (currentSkill.isPresent()) {
                skillsList.add(currentSkill.get());
                skillResList.add(currentSkill.get().getName());
            }
        }
        newJob.setSkills(skillsList);
        long id = (long) Integer.parseInt(datajob.getCompany().getId());
        Optional<Company> company = companyRepository.findById(id);
        if (!company.isPresent()) {
            throw new RuntimeException("company with id " + datajob.getCompany().getId() + " doesn't exist");
        }
//        newJob.setCompany(company.get());
        newJob.setDescription(datajob.getDescription());
        newJob.setStartDate(datajob.getStartDate());
        newJob.setEndDate(datajob.getEndDate());
        newJob.setLevel(datajob.getLevel());
        newJob.setName(datajob.getName());
        newJob.setSalary(datajob.getSalary());
        newJob.setLocation(datajob.getLocation());
        newJob.setQuantity(datajob.getQuantity());
        newJob.setActive(datajob.isActive());
        Job SaveJob = jobRepository.save(newJob);
        CreateJobDto createJobDto = new CreateJobDto(SaveJob.getCreatedBy(), skillResList, SaveJob.getCreatedAt(), SaveJob.isActive(), SaveJob.getStartDate(), SaveJob.getEndDate(),
                SaveJob.getLocation(), SaveJob.getLevel(), SaveJob.getQuantity(), SaveJob.getSalary(), SaveJob.getName(), SaveJob.getId());

        return createJobDto;
    }

    public UpdateJobDto updateJob(ReqUpdateJob datajob) {
        List<String> skillResList = new ArrayList<>();
        Optional<Job> job = jobRepository.findById(datajob.getId());
        Job currentJob = new Job();

        if (!job.isPresent()) {
            throw new RuntimeException("job with id " + datajob.getId() + " doesn't exist");
        } else {
            currentJob = job.get();

        }
        List<ReqCreateJob.skill_id> listSkillId = datajob.getSkills();
        List<Long> list_id = new ArrayList<>();
        for (ReqCreateJob.skill_id skillId : listSkillId) {
            list_id.add(skillId.getId());
        }
        List<Skills> skillsList = this.skillsRepository.findByIdIn(list_id);
        for (Skills skill : skillsList) {
            skillResList.add(skill.getName());
        }
        Optional<Company> company = companyRepository.findById((long) Integer.parseInt(datajob.getCompany().getId()));
        if (!company.isPresent()) {
            throw new RuntimeException("company with id " + datajob.getCompany().getId() + " doesn't exist");
        }
        if (skillsList != null && !skillsList.isEmpty()) {
            currentJob.setSkills(skillsList);
        }
        if (company.isPresent()) {
            currentJob.setCompany(company.get());
        }
        if (datajob.getDescription() != null)
            currentJob.setDescription(datajob.getDescription());
        if (datajob.getStartDate() != null)
            currentJob.setStartDate(datajob.getStartDate());
        if (datajob.getEndDate() != null)
            currentJob.setEndDate(datajob.getEndDate());
        if (datajob.getLevel() != null)
            currentJob.setLevel(datajob.getLevel());
        if (datajob.getName() != null)
            currentJob.setName(datajob.getName());
        if (datajob.getSalary() != 0)
            currentJob.setSalary(datajob.getSalary());
        if (datajob.getLocation() != null)
            currentJob.setLocation(datajob.getLocation());
        if (datajob.getQuantity() != 0)
            currentJob.setQuantity(datajob.getQuantity());

        currentJob.setActive(datajob.isActive());
        Job SaveJob = jobRepository.save(currentJob);

        UpdateJobDto updateJobDto = new UpdateJobDto(SaveJob.getUpdatedBy(), skillResList, SaveJob.getUpdatedAt(), SaveJob.isActive(), SaveJob.getStartDate(), SaveJob.getEndDate(),
                SaveJob.getLocation(), SaveJob.getLevel(), SaveJob.getQuantity(), SaveJob.getSalary(), SaveJob.getName(), SaveJob.getId());

        return updateJobDto;

    }

    public void deleteJob(Long id) {
        this.jobRepository.deleteById(id);
    }

    public Job handleGetJob(long id) {
        Optional<Job> job = jobRepository.findById(id);

        if (job.isPresent()) {
            return job.get();

        }
        throw new RuntimeException("job with id " + id + " doesn't exist");

    }

    public Page<Job> handleGetJobsWithPaginate(Pageable pageable, List<Skills> skillsList, List<String> location) {
        Specification<Job> jobSpecification = Specification.where(null);
        if (location != null && !location.isEmpty()) {
            jobSpecification = jobSpecification.and(specificationsBuilder.whereAttributewhereAttributeIn("location", location));

        }
        if (skillsList != null && !skillsList.isEmpty()) {
            jobSpecification = jobSpecification.and(specificationsBuilder.whereAttributewhereAttributeObjectIn("skills", skillsList));
        }

        return this.jobRepository.findAll(jobSpecification, pageable);
    }

}
