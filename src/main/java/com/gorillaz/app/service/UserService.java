package com.gorillaz.app.service;

import com.gorillaz.app.domain.user.User;
import com.gorillaz.app.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {
   private final UserRepository userRepository;

   public UserService (UserRepository userRepository) {
       this.userRepository = userRepository;
   }

   public User save(User user) {
       return userRepository.save(user);
   }

   public UserDetails findUserByEmail (String email) {
       return userRepository.findByEmail(email);
   }
}
