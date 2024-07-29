package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.DTO.CreateUserDTO;
import vn.hoidanit.jobhunter.domain.DTO.GetUserDTO;
import vn.hoidanit.jobhunter.domain.DTO.UpdateUserDTO;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.service.Specfication.SpecificationsBuilder;
import vn.hoidanit.jobhunter.utils.Enum.GenderEnum;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    SpecificationsBuilder specificationsBuilder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SpecificationsBuilder specificationsBuilder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.specificationsBuilder = specificationsBuilder;
    }

    public CreateUserDTO handleSaveuUser(User datauser) {
        if (this.userRepository.existsByEmail(datauser.getEmail())) {
            throw new RuntimeException(datauser.getEmail() + " is already in use");
        }
        User user = new User();
        user.setEmail(datauser.getEmail());
        user.setName(datauser.getName());
        user.setAge(datauser.getAge());
        user.setGender(datauser.getGender());
        user.setAddress(datauser.getAddress());
        user.setPassword(this.passwordEncoder.encode(datauser.getPassword()));
        user = this.userRepository.save(user);
        CreateUserDTO res = new CreateUserDTO(user.getId(), user.getCreatedAt(), user.getGender(), user.getAddress(), user.getAge(),
                user.getEmail(), user.getName());
        return res;
    }

    public Page<User> handleGetUserWithPaginate(Pageable pageable, String email) {
        Specification<User> searchSpecificationsBuilder = specificationsBuilder.whereAttributeContains("email", email);
        return this.userRepository.findAll(searchSpecificationsBuilder, pageable);

    }

    public GetUserDTO handleGetUserById(long id) {

        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            GetUserDTO res = new GetUserDTO();
            res.setId(userData.get().getId());
            res.setName(userData.get().getName());
            res.setAge(userData.get().getAge());
            res.setGender(userData.get().getGender());
            res.setAddress(userData.get().getAddress());
            res.setEmail(userData.get().getEmail());
            res.setCreatedAt(userData.get().getCreatedAt());
            res.setUpdatedAt(userData.get().getUpdatedAt());
            return res;
        }
        throw new RuntimeException("User With " + id + " not found");
    }

    public boolean checkIdExist(long id) {
        return this.userRepository.existsById(id);
    }

    public void handleDeleteUser(long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (!user.isPresent()) {
            throw new RuntimeException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UpdateUserDTO handleUpdateUser(User dataUser) {
        Optional<User> currentUser = userRepository.findById(dataUser.getId());
        if (currentUser.isPresent()) {
            currentUser.get().setAddress(dataUser.getAddress());
            currentUser.get().setAge(dataUser.getAge());
            currentUser.get().setName(dataUser.getName());
            currentUser.get().setGender(dataUser.getGender());
            User user = userRepository.save(currentUser.get());
            UpdateUserDTO updateUserDTO = new UpdateUserDTO(dataUser.getName(), user.getId(), dataUser.getAge(), dataUser.getGender(), dataUser.getAddress(), user.getUpdatedAt());
            return updateUserDTO;

        } else throw new RuntimeException("User with id " + dataUser.getId() + " not found");

    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void UpdateUserToken(String Token, String email) {
        User user = getUserByEmail(email);
        if (user != null) {
            user.setRefreshToken(Token);
            this.userRepository.save(user);
        }
    }

    public User handleFindByEmailAndRefreshToken(String email, String refreshToken) {
        return this.userRepository.findByEmailAndRefreshToken(email, refreshToken);
    }
}
