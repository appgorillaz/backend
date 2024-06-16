package com.gorillaz.app.controller;

import com.gorillaz.app.config.security.TokenService;
import com.gorillaz.app.domain.user.*;
import com.gorillaz.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getUserProfile() {
        User user = userService.getAuthenticatedUser();
        UserProfileDTO userProfile = new UserProfileDTO(user.getName(), user.getEmail(), user.getGender(), user.getRa(), user.getCourse(), user.getPeriod(), user.getSemester(), user.isRepresentative());
        return ResponseEntity.ok().body(userProfile);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data) {
        if (this.userService.findUserByEmail(data.email()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail utilizado por outro usuário.");

        int semester = data.semester() == 0 ? 1 : data.semester();

        String passwordHashed = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.name(), data.email(), passwordHashed, data.ra(), data.gender(), data.course(), semester, data.period(), data.isRepresentative(), data.role());

        var userCreated = this.userService.save(user);

        if (userCreated == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data) {
        var emailPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(emailPassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PutMapping("/me")
    public ResponseEntity<HttpStatus> updateProfile(@RequestBody UserProfileDTO userDto) {
        User user = userService.getAuthenticatedUser();

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        var userId = user.getId();
        Optional<User> update = userService.update(userId, userDto);

        if (update.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<HttpStatus> deleteProfile() {
        User user = userService.getAuthenticatedUser();

        if (user == null) {
            return (ResponseEntity<HttpStatus>) ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        }

        var userId = user.getId();
        userService.delete(userId);

        return ResponseEntity.noContent().build();
    }
}
