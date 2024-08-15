package vn.hoidanit.jobhunter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.hoidanit.jobhunter.domain.Permissions;
import vn.hoidanit.jobhunter.domain.Role;


import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    boolean existsByName(String name);

    List<Role> findByPermissions(Permissions permission);

    Role findByName(String name);

    Page<Role> findAll(Pageable pageable);
}
