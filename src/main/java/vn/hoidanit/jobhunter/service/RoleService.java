package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import vn.hoidanit.jobhunter.domain.Permissions;
import vn.hoidanit.jobhunter.domain.Role;
import vn.hoidanit.jobhunter.domain.req.ReqRole;
import vn.hoidanit.jobhunter.domain.res.Meta;
import vn.hoidanit.jobhunter.domain.res.RestPaginateDTO;
import vn.hoidanit.jobhunter.repository.PermissionRepository;
import vn.hoidanit.jobhunter.repository.RoleRepository;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role handleCreateRole(ReqRole role) {
        if (this.roleRepository.existsByName(role.getName())) {
            throw new RuntimeException("Role already exists");
        }
        List<Long> PermissionId = Arrays.stream(role.getPermissions()).map(x -> x.getId()).toList();
        List<Permissions> permissionList = this.permissionRepository.findByIdIn(PermissionId);
        Role newRole = new Role();
        newRole.setName(role.getName());
        newRole.setDescription(role.getDescription());
        newRole.setActive(role.isActive());

        newRole.setPermissions(permissionList);
        return this.roleRepository.save(newRole);
    }

    public Role handleUpdateRole(ReqRole role) {
        Optional<Role> currentRole = this.roleRepository.findById(role.getId());
        if (!currentRole.isPresent()) {
            throw new RuntimeException("Role does not exist");

        }
        List<Long> PermissionId = Arrays.stream(role.getPermissions()).map(x -> x.getId()).toList();
        List<Permissions> permissionList = this.permissionRepository.findByIdIn(PermissionId);
        currentRole.get().setName(role.getName());
        currentRole.get().setDescription(role.getDescription());
        currentRole.get().setActive(role.isActive());

        currentRole.get().setPermissions(permissionList);
        return this.roleRepository.save(currentRole.get());

    }

    public RestPaginateDTO handleGetRoleWithPaginate(Pageable pageable) {
        Page<Role> Roles = this.roleRepository.findAll(pageable);
        RestPaginateDTO paginateDTO = new RestPaginateDTO();
        paginateDTO.setResult(Roles.getContent());
        Meta meta = new Meta();
        meta.setCurrent(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalsItems((int) Roles.getTotalElements());
        meta.setTotalsPage(Roles.getTotalPages());
        paginateDTO.setMeta(meta);
        return paginateDTO;
    }

    public void handleDeleteRole(long id) {
        Optional<Role> currentRole = this.roleRepository.findById(id);
        if (!currentRole.isPresent()) {
            throw new RuntimeException("Role does not exist");
        }
        this.roleRepository.deleteById(id);
    }
}
