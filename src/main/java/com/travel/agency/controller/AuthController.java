package com.travel.agency.controller;

import com.travel.agency.dto.UserDto;
import com.travel.agency.models.User;
import com.travel.agency.service.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/check")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody UserDto userDto, BindingResult result) {
        Optional<User> customerOpt = userRepository.findByEmail(userDto.getEmail());

        if (customerOpt.isEmpty()) {
            return ResponseEntity
                    .status(401) // 401 Unauthorized
                    .body("Invalid email or password");
        }

        User user =customerOpt.get();

        if (!user.getPassword().equals(userDto.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDto userDto, BindingResult result) {
        // Check for validation errors
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Date createdAt = new Date();

        try {
            // Create and save the user
            User user = new User();
            user.setCreated_at(createdAt);
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setMobile(userDto.getMobile());
            user.setRole("customer");
            userRepository.save(user);

            return ResponseEntity.ok(user);

        } catch (Exception ex) {
            // Catch any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }

}
