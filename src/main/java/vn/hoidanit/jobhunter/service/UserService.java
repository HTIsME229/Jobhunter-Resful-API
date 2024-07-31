package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.res.CreateUserDTO;
import vn.hoidanit.jobhunter.domain.res.GetUserDTO;
import vn.hoidanit.jobhunter.domain.res.UpdateUserDTO;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.res.UserCompany;
import vn.hoidanit.jobhunter.repository.CompanyRepository;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.service.Specfication.SpecificationsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    SpecificationsBuilder specificationsBuilder;
    CompanyRepository companyRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SpecificationsBuilder specificationsBuilder, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.specificationsBuilder = specificationsBuilder;
        this.companyRepository = companyRepository;
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
        if (datauser.getCompany() != null) {
            Optional<Company> company = this.companyRepository.findById(datauser.getCompany().getId());
            if (!company.isPresent())
                throw new RuntimeException("Company not found");
            user.setCompany(company.get());
        }
        user = this.userRepository.save(user);

        UserCompany userCompany = new UserCompany();
        if (datauser.getCompany() != null) {
            userCompany.setId(user.getCompany().getId());
            userCompany.setName(user.getCompany().getName());
        }
        CreateUserDTO res = new CreateUserDTO(user.getId(), user.getCreatedAt(), user.getGender(), user.getAddress(), user.getAge(),
                user.getEmail(), user.getName(), userCompany);
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
            UserCompany userCompany = new UserCompany();
            userCompany.setId(userData.get().getCompany().getId());
            userCompany.setName(userData.get().getCompany().getName());
            res.setCompany(userCompany);
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
        Optional<Company> company = this.companyRepository.findById(dataUser.getCompany().getId());
        if (currentUser.isPresent()) {
            currentUser.get().setAddress(dataUser.getAddress());
            currentUser.get().setAge(dataUser.getAge());
            currentUser.get().setName(dataUser.getName());
            currentUser.get().setGender(dataUser.getGender());
            currentUser.get().setCompany(company.isPresent() ? company.get() : null);
            User user = userRepository.save(currentUser.get());
            UserCompany userCompany = new UserCompany();
            userCompany.setId(user.getCompany().getId());
            userCompany.setName(user.getCompany().getName());
            UpdateUserDTO updateUserDTO = new UpdateUserDTO(dataUser.getName(), user.getId(), dataUser.getAge(), dataUser.getGender(), dataUser.getAddress(), user.getUpdatedAt(), userCompany);
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

    public void handleRemoveRefreshToken(User user) {
        user.setRefreshToken(null);
        this.userRepository.save(user);
    }

    public User handleFindByEmailAndRefreshToken(String email, String refreshToken) {
        return this.userRepository.findByEmailAndRefreshToken(email, refreshToken);
    }
}
