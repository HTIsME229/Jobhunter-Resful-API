package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User handleSaveuUser(User datauser) {
        User user = new User();
        user.setEmail(datauser.getEmail());
        user.setName(datauser.getName());
        user.setPassword(this.passwordEncoder.encode(datauser.getPassword()));
        return this.userRepository.save(user);
    }

    public Page<User> handleGetUserWithPaginate(Pageable pageable) {
        return this.userRepository.findAll(pageable);

    }

    public User handleGetUserById(long id) {

        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            return userData.get();
        }
        return null;
    }

    public boolean checkIdExist(long id) {
        return this.userRepository.existsById(id);
    }

    public void handleDeleteUser(long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User handleUpdateUser(User dataUser) {
        Optional<User> currentUser = userRepository.findById(dataUser.getId());
        if (currentUser.isPresent()) {
            currentUser.get().setPassword(dataUser.getPassword());
            currentUser.get().setName(dataUser.getName());
            currentUser.get().setEmail(dataUser.getEmail());
            return userRepository.save(currentUser.get());

        } else return null;

    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
