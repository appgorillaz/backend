package com.gorillaz.app.controller;

import com.gorillaz.app.config.security.TokenService;
import com.gorillaz.app.domain.user.AuthenticationDTO;
import com.gorillaz.app.domain.user.LoginResponseDTO;
import com.gorillaz.app.domain.user.RegisterDTO;
import com.gorillaz.app.domain.user.User;
import com.gorillaz.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data) {
        if (this.userService.findUserByEmail(data.email()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail utilizado por outro usuário.");

        String passwordHashed = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.name(), data.email(), passwordHashed, data.ra(), data.gender(), data.course(), data.period(), data.isRepresentative(), data.role());

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
}
