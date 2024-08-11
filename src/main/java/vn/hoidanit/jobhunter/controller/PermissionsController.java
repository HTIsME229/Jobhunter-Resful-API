package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Permissions;
import vn.hoidanit.jobhunter.domain.res.RestPaginateDTO;
import vn.hoidanit.jobhunter.service.PermissionService;
import vn.hoidanit.jobhunter.utils.annotation.ApiMessage;

@RestController
@RequestMapping("api/v1/")
public class PermissionsController {
    private final PermissionService permissionService;

    public PermissionsController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @ApiMessage("Create Permission Success ")
    @PostMapping("/permissions")
    public ResponseEntity<Permissions> CreatePermission(@RequestBody @Valid Permissions permissions) {
        return ResponseEntity.ok(this.permissionService.handleCreatePermission(permissions));
    }

    @ApiMessage("Update Permission Success ")
    @PutMapping("/permissions")
    public ResponseEntity<Permissions> UpdatePermission(@RequestBody @Valid Permissions permissions) {
        return ResponseEntity.ok(this.permissionService.handleUpdatePermission(permissions));
    }

    @ApiMessage("Fetch Resume Success")
    @GetMapping("/permissions")
    public ResponseEntity<RestPaginateDTO> GetResumesWithPaginate(Pageable pageable) {
        RestPaginateDTO res = this.permissionService.handleGetPermissionWithPaginate(pageable);
        return ResponseEntity.ok(res);
    }

    @ApiMessage("Delete Permission Resume Success")
    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> DeletePermission(@PathVariable long id) {
        this.permissionService.handleDeletePermission(id);
        return ResponseEntity.ok().build();
    }

}
