package com.netcracker.edu.distancestudyplatform.controller;

import com.netcracker.edu.distancestudyplatform.dto.authentication.AuthenticationRequest;
import com.netcracker.edu.distancestudyplatform.dto.authentication.AuthenticationResponse;
import com.netcracker.edu.distancestudyplatform.exception.UserNotFoundException;
import com.netcracker.edu.distancestudyplatform.model.Student;
import com.netcracker.edu.distancestudyplatform.service.AuthenticationService;
import com.netcracker.edu.distancestudyplatform.service.StudentService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
@Profile("security")
public class AuthController {
    private AuthenticationService authService;
    private StudentService studentService;

    public AuthController(AuthenticationService authService, StudentService studentService) {
        this.authService = authService;
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response;
        try {
            response = authService.authenticate(request);
        } catch (BadCredentialsException | UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, OK);
    }
}
