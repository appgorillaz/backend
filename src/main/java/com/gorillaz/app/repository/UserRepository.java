package com.gorillaz.app.repository;

import com.gorillaz.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserById(Long id);
}
