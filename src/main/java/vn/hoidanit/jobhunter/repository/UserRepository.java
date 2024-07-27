package vn.hoidanit.jobhunter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.hoidanit.jobhunter.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(long id);

    Page<User> findAll(Pageable pageable);

    User findByEmail(String email);


    User deleteUserById(long id);
}
