package com.gorillaz.app.service;

import com.gorillaz.app.domain.user.User;
import com.gorillaz.app.domain.user.UserProfileDTO;
import com.gorillaz.app.enums.Gender;
import com.gorillaz.app.enums.Period;
import com.gorillaz.app.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    public Optional<User> update(UUID id, UserProfileDTO user) {

        Optional<User> u = userRepository.findById(id);

        if (u.isEmpty()) {
            return Optional.empty();
        }

        u.get().setName(user.name());
        u.get().setEmail(user.email());
        u.get().setGender(user.gender());
        u.get().setRa(user.ra());
        u.get().setCourse(user.course());
        u.get().setSemester(user.semester() == 0 ? 1 : user.semester());
        u.get().setPeriod(user.period());
        u.get().setRepresentative(user.representative());

        User updated = userRepository.save(u.get());

        return Optional.of(updated);
   }

   public void delete(UUID id) {
       userRepository.deleteById(id);
   }
}
