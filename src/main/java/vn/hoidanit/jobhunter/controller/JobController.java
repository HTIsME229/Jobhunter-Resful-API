package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.req.ReqCreateJob;
import vn.hoidanit.jobhunter.domain.req.ReqUpdateJob;
import vn.hoidanit.jobhunter.domain.res.CreateJobDto;
import vn.hoidanit.jobhunter.domain.res.Meta;
import vn.hoidanit.jobhunter.domain.res.RestPaginateDTO;
import vn.hoidanit.jobhunter.domain.res.UpdateJobDto;
import vn.hoidanit.jobhunter.repository.SkillsRepository;
import vn.hoidanit.jobhunter.service.JobService;
import vn.hoidanit.jobhunter.utils.annotation.ApiMessage;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class JobController {
    private final JobService jobService;


    public JobController(JobService jobService, SkillsRepository skillsRepository) {
        this.jobService = jobService;
    }

    @ApiMessage("Create New Job Success ")
    @PostMapping("/jobs")
    public ResponseEntity<CreateJobDto> CreateNewJob(@Valid @RequestBody ReqCreateJob job) {


        CreateJobDto newJob = this.jobService.handleCreate(job);

        return ResponseEntity.status(HttpStatus.CREATED).body(newJob);
    }

    @ApiMessage("Update Job Success")
    @PutMapping("/jobs")
    public ResponseEntity<UpdateJobDto> UpdateJob(@Valid @RequestBody ReqUpdateJob job) {
        UpdateJobDto updateJob = this.jobService.updateJob(job);
        return ResponseEntity.status(HttpStatus.OK).body(updateJob);

    }

    @ApiMessage("Delete Job Success")
    @DeleteMapping("jobs/{id}")
    public ResponseEntity<Void> DeleteJob(@PathVariable long id) {
        this.jobService.deleteJob(id);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @ApiMessage("Fetch Job Success")
    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJob(@PathVariable long id) {
        Job job = this.jobService.handleGetJob(id);
        return ResponseEntity.status(HttpStatus.OK).body(job);
    }

    @ApiMessage("Fetch Job Success")
    @GetMapping("/jobs")
    public ResponseEntity<RestPaginateDTO> GetAllJobsWithPaginate(Pageable pageable) {

        Page<Job> Job = this.jobService.handleGetJobsWithPaginate(pageable);
        List<Job> listJob = Job.getContent();
        Meta meta = new Meta();
        meta.setCurrent(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalsItems((int) Job.getTotalElements());
        meta.setTotalsPage(Job.getTotalPages());
        RestPaginateDTO restPaginateDTO = new RestPaginateDTO();
        restPaginateDTO.setMeta(meta);
        restPaginateDTO.setResult(listJob);
        return ResponseEntity.status(200).body(restPaginateDTO);
    }


}
