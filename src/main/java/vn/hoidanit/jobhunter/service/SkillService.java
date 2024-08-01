package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Skills;
import vn.hoidanit.jobhunter.domain.req.ReqUpdateSkill;
import vn.hoidanit.jobhunter.repository.SkillsRepository;

import java.util.Optional;

@Service
public class SkillService {
    private final SkillsRepository skillsRepository;

    public SkillService(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    public Skills handleCreate(Skills skill) {
        if (skillsRepository.existsByName(skill.getName())) {
            throw new RuntimeException("This Skill already exists");
        }
        Skills newSkill = new Skills();
        newSkill.setName(skill.getName());

        return skillsRepository.save(newSkill);
    }

    public Skills handleUpdate(ReqUpdateSkill skills) {
        Optional<Skills> currentSkill = skillsRepository.findById(skills.getId());
        if (currentSkill.isPresent()) {
            Skills updatedSkill = currentSkill.get();
            updatedSkill.setName(skills.getName());
            return skillsRepository.save(updatedSkill);
        } else throw new RuntimeException("This Skill does not exist");

    }

    public Page<Skills> handleGetSkillWithPaginate(Pageable pageable) {
        return skillsRepository.findAll(pageable);

    }

}
