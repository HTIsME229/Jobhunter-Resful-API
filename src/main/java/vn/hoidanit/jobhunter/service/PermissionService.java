package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Permissions;
import vn.hoidanit.jobhunter.domain.Role;
import vn.hoidanit.jobhunter.domain.res.Meta;
import vn.hoidanit.jobhunter.domain.res.RestPaginateDTO;
import vn.hoidanit.jobhunter.repository.PermissionRepository;
import vn.hoidanit.jobhunter.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PermissionService {
    private PermissionRepository permissionRepository;
    private RoleRepository roleRepository;

    public PermissionService(PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;

    }

    public Permissions handleCreatePermission(Permissions permission) {
        Permissions newPermission = new Permissions();
        if (this.permissionRepository.existsByMethodAndApiPathAndModule(permission.getMethod(), permission.getApiPath(), permission.getModule()))
            throw new RuntimeException("Permission already exists");

        newPermission.setName(permission.getName());
        newPermission.setApiPath(permission.getApiPath());
        newPermission.setMethod(permission.getMethod());
        newPermission.setModule(permission.getModule());

        return this.permissionRepository.save(newPermission);
    }

    public Permissions handleUpdatePermission(Permissions permission) {
        Optional<Permissions> currentPermission = this.permissionRepository.findById(permission.getId());
        if (!currentPermission.isPresent()) {
            throw new RuntimeException("Permission not found");
        }
        if (currentPermission.get().getName().equals(permission.getName())) {
            throw new RuntimeException("Permission already exists");
        }

        currentPermission.get().setName(permission.getName());
        currentPermission.get().setApiPath(permission.getApiPath());
        currentPermission.get().setMethod(permission.getMethod());
        currentPermission.get().setModule(permission.getModule());
        return this.permissionRepository.save(currentPermission.get());
    }

    public RestPaginateDTO handleGetPermissionWithPaginate(Pageable pageable) {
        Page<Permissions> permissions = this.permissionRepository.findAll(pageable);
        RestPaginateDTO paginateDTO = new RestPaginateDTO();
        paginateDTO.setResult(permissions.getContent());
        Meta meta = new Meta();
        meta.setCurrent(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalsItems((int) permissions.getTotalElements());
        meta.setTotalsPage(permissions.getTotalPages());
        paginateDTO.setMeta(meta);
        return paginateDTO;
    }

    public void handleDeletePermission(long id) {
        Optional<Permissions> currentPermission = this.permissionRepository.findById(id);
        if (!currentPermission.isPresent()) {
            throw new RuntimeException("Permission not found");
        } else {
            List<Role> roleList = this.roleRepository.findByPermissions(currentPermission.get());
            for (Role role : roleList) {
                role.getPermissions().remove(currentPermission.get());
            }
            this.permissionRepository.delete(currentPermission.get());
        }

    }
}
