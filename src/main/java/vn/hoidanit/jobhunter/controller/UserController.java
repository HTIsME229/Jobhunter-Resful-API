package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.res.*;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.utils.annotation.ApiMessage;
import vn.hoidanit.jobhunter.utils.error.IdInvalidExeption;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1")
@RestController
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiMessage("Create New User Success ")
    @PostMapping("/users")
    public ResponseEntity<CreateUserDTO> CreateNewUser(@Valid @RequestBody User datauser) {


        CreateUserDTO newUser = this.userService.handleSaveuUser(datauser);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @ApiMessage("Fetch User Success ")
    @GetMapping("/users/{id}")
    public ResponseEntity<GetUserDTO> getUser(@PathVariable long id) {
        GetUserDTO user = this.userService.handleGetUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);

    }


    @GetMapping("/users")
    @ApiMessage("Fetch User Success ")
    public ResponseEntity<RestPaginateDTO> GetAllUsersWithPaginate(@RequestParam("email") Optional<String> optionalEmail, Pageable pageable) {
        String email = optionalEmail.isPresent() ? optionalEmail.get() : "";
        Page<User> User = this.userService.handleGetUserWithPaginate(pageable, email);
        List<User> listUser = User.getContent();
        List<GetUserDTO> data = new ArrayList<>();
        for (User u : listUser) {
            UserCompany userCompany = new UserCompany();
            userCompany.setId(u.getCompany() != null ? u.getCompany().getId() : 0);
            userCompany.setName(u.getCompany() != null ? u.getCompany().getName() : "");
            GetUserDTO.UserRole userRole = new GetUserDTO.UserRole(u.getRole() != null ? u.getRole().getId() : 0, u.getRole() != null ? u.getRole().getName() : "");
            GetUserDTO dto = new GetUserDTO(u.getId(), u.getName(), u.getEmail(), u.getAge(), u.getGender(), u.getAddress(), u.getCreatedAt(), u.getUpdatedAt()
                    , userCompany, userRole);
            data.add(dto);
        }
        Meta meta = new Meta();
        meta.setCurrent(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalsItems((int) User.getTotalElements());
        meta.setTotalsPage(User.getTotalPages());
        RestPaginateDTO restPaginateDTO = new RestPaginateDTO();
        restPaginateDTO.setMeta(meta);
        restPaginateDTO.setResult(data);
        return ResponseEntity.status(200).body(restPaginateDTO);
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws IdInvalidExeption {

        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");


    }

    @PutMapping("/users")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody User datauser) {
        UpdateUserDTO userUpdate = this.userService.handleUpdateUser(datauser);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdate);
    }

}
