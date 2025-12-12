package com.cinema_system.repository;

import com.cinema_system.model.Role;
import com.cinema_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByRole(Role role);
    boolean existsByEmail(String email);
}