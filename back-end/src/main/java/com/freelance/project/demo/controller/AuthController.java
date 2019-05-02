package com.freelance.project.demo.controller;

import com.freelance.project.demo.config.security.jwt.JwtTokenProvider;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.repository.PersonRepository;
import com.freelance.project.demo.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PersonService personService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @PostMapping("/singin")
    public ResponseEntity signin(@RequestBody AuthenticationRequest credentials) {
        logger.info("Request to sign in: credentials - {}", credentials);
        try {
            String email = credentials.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, credentials.getPassword()));
            String token = jwtTokenProvider.createToken(email, this.personService.findByEmail(email).getRole());

            Map<Object, Object> model = new HashMap<>();
            model.put("email", email);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password");
        }
    }

    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails){
        // careful!!! in custom userDetails username is EMAIL
        logger.info("Request to get current user: {}", userDetails);
        Person currentPerson = personService.findByEmail(userDetails.getUsername());
        Map<Object, Object> model = new HashMap<>();
        model.put("name", currentPerson.getName());
        return ok(model);
    }
}
