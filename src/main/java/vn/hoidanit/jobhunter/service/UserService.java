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
import vn.hoidanit.jobhunter.repository.RoleRepository;
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
    CompanyRepository companyRepository;
    RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SpecificationsBuilder specificationsBuilder, CompanyRepository companyRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.specificationsBuilder = specificationsBuilder;
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;


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
        user.setRole(this.roleRepository.findById(datauser.getRole().getId()).orElse(null));

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

            UserCompany userCompany = new UserCompany();
            userCompany.setId(userData.get().getCompany().getId());
            userCompany.setName(userData.get().getCompany().getName());
            GetUserDTO.UserRole userRole = new GetUserDTO.UserRole(userData.get().getRole().getId(), userData.get().getRole().getName());
            GetUserDTO res = new GetUserDTO(userData.get().getId(), userData.get().getName(), userData.get().getEmail(), userData.get().getAge(), userData.get().getGender(), userData.get().getAddress(), userData.get().getCreatedAt(), userData.get().getUpdatedAt()
                    , userCompany, userRole);
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
            currentUser.get().setRole(roleRepository.findById(dataUser.getRole().getId()).orElse(null));
            User user = userRepository.save(currentUser.get());
            UserCompany userCompany = new UserCompany();
            userCompany.setId(user.getCompany().getId());
            userCompany.setName(user.getCompany().getName());
            UpdateUserDTO.UserRole userRole = new UpdateUserDTO.UserRole(user.getRole().getId(), user.getRole().getName());
            UpdateUserDTO updateUserDTO = new UpdateUserDTO(user.getId(), user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getUpdatedAt(), userCompany, userRole);
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
