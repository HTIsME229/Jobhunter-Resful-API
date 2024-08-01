package vn.hoidanit.jobhunter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.hoidanit.jobhunter.domain.Skills;

import java.util.List;

public interface SkillsRepository extends JpaRepository<Skills, Long>, JpaSpecificationExecutor<Skills> {
    boolean existsByName(String name);

    Page<Skills> findAll(Pageable pageable);

    List<Skills> findByIdIn(List<Long> id);
}
