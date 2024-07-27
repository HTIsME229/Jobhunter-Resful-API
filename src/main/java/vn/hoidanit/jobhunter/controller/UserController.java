package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.DTO.Meta;
import vn.hoidanit.jobhunter.domain.DTO.RestPaginateDTO;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.utils.error.IdInvalidExeption;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> CreateNewUser(@RequestBody User datauser) {


        User newUser = this.userService.handleSaveuUser(datauser);

        return ResponseEntity.status(HttpStatus.CREATED).body(datauser);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = this.userService.handleGetUserById(id);
        if (user != null)
            return ResponseEntity.status(HttpStatus.OK).body(user);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<User>> getAllUser() {

        List<User> listUser = this.userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(listUser);

    }

    @GetMapping("/users")
    public ResponseEntity<RestPaginateDTO> GetAllUsersWithPaginate(@RequestParam("current") Optional<String> optionalCurrent, @RequestParam("pageSize") Optional<String> optionalPageSize) {
        int page = 0;
        int pageSize = 10;
        if (optionalCurrent.isPresent()) {
            page = Integer.parseInt(optionalCurrent.get());
        }
        if (optionalPageSize.isPresent()) {
            pageSize = Integer.parseInt(optionalPageSize.get());
        }
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> User = this.userService.handleGetUserWithPaginate(pageable);
        List<User> listUser = User.getContent();
        Meta meta = new Meta();
        meta.setCurrent(pageable.getPageNumber());
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalsItems((int) User.getTotalElements());
        meta.setTotalsPage(User.getTotalPages());
        RestPaginateDTO restPaginateDTO = new RestPaginateDTO();
        restPaginateDTO.setMeta(meta);
        restPaginateDTO.setResult(listUser);
        return ResponseEntity.status(200).body(restPaginateDTO);
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws IdInvalidExeption {
        if (id >= 1500) {
            throw new IdInvalidExeption("ID not Exits");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");


    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User datauser) {
        User userUpdate = this.userService.handleUpdateUser(datauser);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdate);
    }
}
