package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Role;
import vn.hoidanit.jobhunter.domain.req.ReqRole;
import vn.hoidanit.jobhunter.domain.res.RestPaginateDTO;
import vn.hoidanit.jobhunter.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody ReqRole role) {
        Role res = this.roleService.handleCreateRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/roles")
    public ResponseEntity<Role> updateRole(@RequestBody ReqRole role) {
        Role res = this.roleService.handleUpdateRole(role);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/roles")
    public ResponseEntity<RestPaginateDTO> getAllRoles(Pageable pageable) {
        return ResponseEntity.ok(this.roleService.handleGetRoleWithPaginate(pageable));
    }
}
