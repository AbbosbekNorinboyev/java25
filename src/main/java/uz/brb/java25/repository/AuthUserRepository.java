package uz.brb.java25.repository;

import module java.base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.java25.entity.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);
}