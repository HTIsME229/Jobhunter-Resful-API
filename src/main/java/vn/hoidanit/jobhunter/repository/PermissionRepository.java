package vn.hoidanit.jobhunter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.hoidanit.jobhunter.domain.Permissions;

@Repository
public interface PermissionRepository extends JpaRepository<Permissions, Long>, JpaSpecificationExecutor<Permissions> {
    boolean existsByMethodAndApiPathAndModule(String method, String apiPath, String module);

    Page<Permissions> findAll(Pageable pageable);

}
