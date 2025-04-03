package edu.iu.habahram.ducksservice.controllers;

import edu.iu.habahram.ducksservice.model.Ducks;
import edu.iu.habahram.ducksservice.repository.DucksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private DucksRepository ducksRepository;

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Ducks ducks) {
        try {
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            String passwordEncoded = bc.encode(ducks.getPassword());
            ducks.setPassword(passwordEncoded);
            ducksRepository.save(ducks);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user");
        }
    }

    // Signin endpoint
    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody Ducks loginRequest) {
        try {
            // Find user by username
            Ducks existingUser = ducksRepository.findByUsername(loginRequest.getUsername());
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }

            // Verify password
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            boolean isPasswordMatch = bc.matches(loginRequest.getPassword(), existingUser.getPassword());
            if (!isPasswordMatch) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }

            // Authentication successful
            return ResponseEntity.ok("User signed in successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during sign-in");
        }
    }
}
