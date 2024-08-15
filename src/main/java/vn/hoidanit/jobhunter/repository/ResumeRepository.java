package vn.hoidanit.jobhunter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Resume;
import vn.hoidanit.jobhunter.domain.User;

import java.util.List;

@Repository

public interface ResumeRepository extends JpaRepository<Resume, Long>, JpaSpecificationExecutor<Resume> {
    Page<Resume> findAll(Pageable pageable);

    Page<Resume> findByJobIn(List<Job> job, Pageable pageable);

    Page<Resume> findByUser(Pageable pageable, User user);
}
