package com.gorillaz.app.repository;

import com.gorillaz.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findUserById(UUID id);
    UserDetails findByEmail(String email);
}
