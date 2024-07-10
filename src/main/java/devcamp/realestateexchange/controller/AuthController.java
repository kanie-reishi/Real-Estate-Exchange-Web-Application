package devcamp.realestateexchange.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import devcamp.realestateexchange.dto.APIResponse;
import devcamp.realestateexchange.dto.JwtResponse;
import devcamp.realestateexchange.dto.authentication.LoginRequest;
import devcamp.realestateexchange.dto.authentication.SignupRequest;
import devcamp.realestateexchange.entity.authentication.User;
import devcamp.realestateexchange.security.jwt.JwtUtils;
import devcamp.realestateexchange.security.services.UserDetailsImpl;
import devcamp.realestateexchange.security.services.UserDetailsServiceImpl;

import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        // Set the authentication object to the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Generate JWT token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Create a new cookie
        Cookie jwtCookie = new Cookie("token", jwt);
        // Set the cookie to HTTP-only for security
        jwtCookie.setHttpOnly(true);
        // Optionally, set the cookie to secure if you're using HTTPS
        // jwtCookie.setSecure(true);
        // Add the cookie to the response
        response.addCookie(jwtCookie);
        // Get the UserDetailsImpl object from the authentication object
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        // Get the roles from the UserDetailsImpl object
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        // Create a response object
        APIResponse apiResponse = APIResponse.builder()
                .data(JwtResponse.builder()
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .roles(roles)
                        .build())
                .isSuccessful(true)
                .statusCode(200)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userDetailsServiceImpl.existsByUsername(signUpRequest.getUsername())) {
            APIResponse apiResponse = APIResponse.builder()
                    .message("Error: Username is already taken!")
                    .isSuccessful(false)
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        if (userDetailsServiceImpl.existsByEmail(signUpRequest.getEmail())) {
            APIResponse apiResponse = APIResponse.builder()
                    .message("Error: Email is already in use!")
                    .isSuccessful(false)
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail(),
                signUpRequest.getPhone());
        userDetailsServiceImpl.createUser(user);

        APIResponse apiResponse = APIResponse.builder()
                .message("User registered successfully!")
                .isSuccessful(true)
                .statusCode(200)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
