package vn.hoidanit.jobhunter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.hoidanit.jobhunter.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    boolean existsById(long id);

    boolean existsByEmail(String email);

    Page<User> findAll(Pageable pageable);

    Page<User> findAll(Specification<User> specification, Pageable pageable);

    User findByEmail(String email);

    User findByEmailAndRefreshToken(String email, String refreshToken);

    User deleteUserById(long id);
}
