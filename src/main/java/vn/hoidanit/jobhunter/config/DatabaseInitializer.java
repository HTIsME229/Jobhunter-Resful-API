//package vn.hoidanit.jobhunter.config;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import vn.hoidanit.jobhunter.domain.Permissions;
//
//import vn.hoidanit.jobhunter.domain.Role;
//import vn.hoidanit.jobhunter.domain.User;
//import vn.hoidanit.jobhunter.repository.PermissionRepository;
//import vn.hoidanit.jobhunter.repository.RoleRepository;
//import vn.hoidanit.jobhunter.repository.UserRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class DatabaseInitializer implements CommandLineRunner {
//    private final RoleRepository roleRepository;
//    private final PermissionRepository permissionRepository;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public DatabaseInitializer(RoleRepository roleRepository, PermissionRepository permissionRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.roleRepository = roleRepository;
//        this.permissionRepository = permissionRepository;
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Check Init Data ");
//        List<Permissions> arr = new ArrayList<>();
//
//        }
//
//
//        if (roleRepository.count() == 0) {
//            Role role = new Role();
//            role.setName("ADMIN");
//            role.setActive(true);
//            role.setDescription("FULl Permissions ");
//            role.setPermissions(arr);
//            roleRepository.save(role);
//        }
//        if (userRepository.count() == 0) {
//            User user = new User();
//            String hashPassword = passwordEncoder.encode("123456");
//
//            user.setName("admin");
//            user.setPassword(hashPassword);
//            user.setEmail("admin@admin.com");
//            user.setRole(roleRepository.findByName("ADMIN"));
//            this.userRepository.save(user);
//
//        }
//        if (userRepository.count() > 0 && roleRepository.count() > 0 && permissionRepository.count() > 0) {
//            System.out.println("Skip init Data ");
//        } else System.out.println(" init Data ");
//
//
//    }
//}
