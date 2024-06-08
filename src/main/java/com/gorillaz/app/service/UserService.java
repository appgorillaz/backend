package com.gorillaz.app.service;

import com.gorillaz.app.domain.user.User;
import com.gorillaz.app.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

   public User getUserByEmail (String email) {
      return (User) userRepository.findByEmail(email);
   }

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return null;
        }

        return this.getUserByEmail(auth.getName());
    }
}
