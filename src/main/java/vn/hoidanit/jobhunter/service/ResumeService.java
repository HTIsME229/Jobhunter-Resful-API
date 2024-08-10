package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Resume;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.res.*;
import vn.hoidanit.jobhunter.repository.JobRepository;
import vn.hoidanit.jobhunter.repository.ResumeRepository;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.domain.req.reqCreateResume;
import vn.hoidanit.jobhunter.utils.Enum.Status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {
    private ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository, JobRepository jobRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public CreateResumeDto handleCreateResume(reqCreateResume resume) {
        CreateResumeDto createResumeDto = new CreateResumeDto();
        Resume newResume = new Resume();
        newResume.setEmail(resume.getEmail());
        newResume.setUrl(resume.getUrl());
        Optional<User> user = this.userRepository.findById(resume.getUser().getId());
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }
        newResume.setUser(user.isPresent() ? user.get() : null);
        Optional<Job> job = this.jobRepository.findById(resume.getJob().getId());
        if (!job.isPresent()) {
            throw new RuntimeException("Job not found");
        }
        newResume.setJob(job.isPresent() ? job.get() : null);
        newResume.setStatus(resume.getStatus());
        Resume savedResume = this.resumeRepository.save(newResume);
        createResumeDto.setCreatedAt(savedResume.getCreatedAt());
        createResumeDto.setCreatedBy(savedResume.getCreatedBy());
        createResumeDto.setId(savedResume.getId());
        return createResumeDto;
    }

    public UpdateResumeDto handleUpdate(Resume resume) {
        Optional<Resume> current = this.resumeRepository.findById(resume.getId());
        if (!current.isPresent()) {
            throw new RuntimeException("Resume not found");
        } else {
            current.get().setStatus(resume.getStatus());
            Resume savaResume = this.resumeRepository.save(current.get());
            return new UpdateResumeDto(savaResume.getUpdatedBy(), savaResume.getUpdatedAt());
        }
    }

    public void handleDelete(long id) {
        Optional<Resume> current = this.resumeRepository.findById(id);
        if (!current.isPresent()) {
            throw new RuntimeException("Resume not found");
        } else {
            this.resumeRepository.deleteById(id);
        }
    }

    public GetResumeDto handleGetResume(long id) {
        Optional<Resume> resume = this.resumeRepository.findById(id);
        if (!resume.isPresent()) {
            throw new RuntimeException("Resume not found");
        } else {
            Resume currentResume = resume.get();
            Job currentJob = currentResume.getJob();
            GetResumeDto.JobData jobData = new GetResumeDto.JobData();
            jobData.setId(currentJob.getId());
            jobData.setName(currentJob.getName());
            User currentUser = currentResume.getUser();

            GetResumeDto.UserData userData = new GetResumeDto.UserData();
            userData.setId(currentUser.getId());
            userData.setName(currentUser.getName());
            String companyName = currentResume.getJob().getCompany().getName();
            GetResumeDto getResumeDto = new GetResumeDto(currentResume.getId(), jobData, userData, currentResume.getUpdatedBy(), currentResume.getCreatedBy(), currentResume.getUpdatedAt(), currentResume.getCreatedAt(), currentResume.getStatus(), currentResume.getUrl(), currentResume.getEmail(), companyName);
            return getResumeDto;
        }


    }

    public RestPaginateDTO handleGetResumeWithPaginate(Pageable pageable) {
        Page<Resume> resumePageList = this.resumeRepository.findAll(pageable);
        List<GetResumeDto> resumeDtoList = new ArrayList<>();
        List<Resume> resumeList = resumePageList.getContent();
        for (Resume currentResume : resumeList) {
            GetResumeDto.JobData jobData = new GetResumeDto.JobData();
            Job currentJob = currentResume.getJob();
            jobData.setId(currentJob.getId());
            jobData.setName(currentJob.getName());
            GetResumeDto.UserData userData = new GetResumeDto.UserData();
            User currentUser = currentResume.getUser();
            userData.setId(currentUser.getId());
            userData.setName(currentUser.getName());
            String companyName = currentResume.getJob().getCompany().getName();
            GetResumeDto getResumeDto = new GetResumeDto(currentResume.getId(), jobData, userData, currentResume.getUpdatedBy(), currentResume.getCreatedBy(), currentResume.getUpdatedAt(), currentResume.getCreatedAt(), currentResume.getStatus(), currentResume.getUrl(), currentResume.getEmail(), companyName);
            resumeDtoList.add(getResumeDto);
        }
        Meta meta = new Meta();
        meta.setCurrent(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalsItems((int) resumePageList.getTotalElements());
        meta.setTotalsPage(resumePageList.getTotalPages());
        RestPaginateDTO restPaginateDTO = new RestPaginateDTO();
        restPaginateDTO.setMeta(meta);
        restPaginateDTO.setResult(resumeDtoList);
        return restPaginateDTO;

    }
}

