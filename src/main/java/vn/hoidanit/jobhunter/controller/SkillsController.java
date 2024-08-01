package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.Skills;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.req.ReqUpdateSkill;
import vn.hoidanit.jobhunter.domain.res.CreateUserDTO;
import vn.hoidanit.jobhunter.domain.res.Meta;
import vn.hoidanit.jobhunter.domain.res.ResCompanyDTO;
import vn.hoidanit.jobhunter.domain.res.RestPaginateDTO;
import vn.hoidanit.jobhunter.service.SkillService;
import vn.hoidanit.jobhunter.utils.annotation.ApiMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping(("/api/v1"))
@RestController
public class SkillsController {
    private final SkillService skillService;

    public SkillsController(final SkillService skillService) {
        this.skillService = skillService;
    }

    @ApiMessage("Create New Skill Success ")
    @PostMapping("/skills")
    public ResponseEntity<Skills> CreateNewSkill(@Valid @RequestBody Skills skill) {


        Skills newSkill = this.skillService.handleCreate(skill);

        return ResponseEntity.status(HttpStatus.CREATED).body(newSkill);
    }

    @ApiMessage("Update Skill Success ")
    @PutMapping("/skills")
    public ResponseEntity<Skills> UpdateSkills(@Valid @RequestBody ReqUpdateSkill skill) {


        Skills newSkill = this.skillService.handleUpdate(skill);

        return ResponseEntity.status(HttpStatus.CREATED).body(newSkill);
    }

    @ApiMessage("Fetch Skill Success")
    @GetMapping("/skills")
    public ResponseEntity<RestPaginateDTO> GetAllSkillsWithPaginate(Pageable pageable) {

        Page<Skills> Skill = this.skillService.handleGetSkillWithPaginate(pageable);

        List<Skills> listSkill = Skill.getContent();

        Meta meta = new Meta();
        meta.setCurrent(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalsItems((int) Skill.getTotalElements());
        meta.setTotalsPage(Skill.getTotalPages());
        RestPaginateDTO restPaginateDTO = new RestPaginateDTO();
        restPaginateDTO.setMeta(meta);
        restPaginateDTO.setResult(listSkill);
        return ResponseEntity.status(200).body(restPaginateDTO);
    }


}
