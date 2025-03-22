package devcamp.realestateexchange.controller;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.APIResponse;
import devcamp.realestateexchange.dto.JwtResponse;
import devcamp.realestateexchange.dto.authentication.LoginRequest;
import devcamp.realestateexchange.dto.authentication.SignupRequest;
import devcamp.realestateexchange.entity.authentication.User;
import devcamp.realestateexchange.entity.user.Customer;
import devcamp.realestateexchange.security.jwt.JwtUtils;
import devcamp.realestateexchange.security.services.UserDetailsImpl;
import devcamp.realestateexchange.security.services.UserDetailsServiceImpl;
import devcamp.realestateexchange.services.user.CustomerService;
import lombok.extern.slf4j.Slf4j;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin
@RestController
@Slf4j
public class AuthController {

        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        PasswordEncoder encoder;

        @Autowired
        JwtUtils jwtUtils;

        @Autowired
        UserDetailsServiceImpl userDetailsServiceImpl;

        @Autowired
        CustomerService customerService;

        private static final Set<String> blacklistedTokens = new HashSet<>();

        // PostMapping for login user
        @PostMapping("/auth/user")
        public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                        HttpServletResponse response) {
                // Authenticate user
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                loginRequest.getPassword()));

                // Set the authentication object to the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Generate JWT token
                String jwt = jwtUtils.generateJwtToken(authentication);
                // Create a new cookie
                Cookie jwtCookie = new Cookie("token", jwt);
                // Set the cookie to HTTP-only for security
                jwtCookie.setHttpOnly(true);
                jwtCookie.setPath("/");
                jwtCookie.setMaxAge(3600);
                // Optionally, set the cookie to secure if you're using HTTPS
                // jwtCookie.setSecure(true);

                // Generate refresh token
                String refreshToken = jwtUtils.generateRefreshToken(authentication);
                Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
                refreshCookie.setHttpOnly(true);
                refreshCookie.setPath("/");
                refreshCookie.setMaxAge(86400);
                // Optionally, set the cookie to secure if you're using HTTPS
                // refreshCookie.setSecure(true);

                // Add the cookie to the response
                response.addCookie(jwtCookie);
                response.addCookie(refreshCookie);

                // Get the UserDetailsImpl object from the authentication object
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                // Get the roles from the UserDetailsImpl object
                List<String> roles = userDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList());
                // Create a response object
                APIResponse apiResponse = APIResponse.builder()
                                .message("Login successful!")
                                .data(JwtResponse.builder()
                                                .id(userDetails.getId())
                                                .token(jwt)
                                                .username(userDetails.getUsername())
                                                .roles(roles)
                                                .build())
                                .isSuccessful(true)
                                .statusCode(200)
                                .build();
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        // PostMapping for login admin
        @PostMapping("/auth/admin")
        public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginRequest loginRequest,
                        HttpServletResponse response) {
                // Authenticate user
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                loginRequest.getPassword()));
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                // Check if the user is an admin
                log.info("User roles: {}", userDetails.getAuthorities());
                if (!userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body("Unauthorized access, user is not an admin.");
                }
                // Set the authentication object to the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                // Generate JWT token
                String jwt = jwtUtils.generateJwtToken(authentication);
                // Create a new cookie
                Cookie jwtCookie = new Cookie("token", jwt);
                jwtCookie.setHttpOnly(true);
                jwtCookie.setPath("/");
                jwtCookie.setMaxAge(3600);
                // Set the cookie to HTTP-only for security
                jwtCookie.setHttpOnly(true);
                // Optionally, set the cookie to secure if you're using HTTPS
                // jwtCookie.setSecure(true);

                // Generate refresh token
                String refreshToken = jwtUtils.generateRefreshToken(authentication);
                Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
                refreshCookie.setHttpOnly(true);
                refreshCookie.setPath("/");
                refreshCookie.setMaxAge(86400);
                // Optionally, set the cookie to secure if you're using HTTPS
                // refreshCookie.setSecure(true);
                
                // Add the cookie to the response
                response.addCookie(jwtCookie);
                response.addCookie(refreshCookie);
                // Get the UserDetailsImpl object from the authentication object
                // Get the roles from the UserDetailsImpl object
                List<String> roles = userDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList());
                // Create a response object
                APIResponse apiResponse = APIResponse.builder()
                                .message("Login successful!")
                                .data(JwtResponse.builder()
                                                .id(userDetails.getId())
                                                .token(jwt)
                                                .username(userDetails.getUsername())
                                                .roles(roles)
                                                .build())
                                .isSuccessful(true)
                                .statusCode(200)
                                .build();
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        // PostMapping for signup user
        @PostMapping("/auth/signup")
        public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
                // Check if the username is already taken
                if (userDetailsServiceImpl.existsByUsername(signUpRequest.getUsername())) {
                        APIResponse apiResponse = APIResponse.builder()
                                        .message("Error: Username is already taken!")
                                        .isSuccessful(false)
                                        .statusCode(400)
                                        .build();
                        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
                }

                // Check if the email is already in use
                if (userDetailsServiceImpl.existsByEmail(signUpRequest.getEmail())) {
                        APIResponse apiResponse = APIResponse.builder()
                                        .message("Error: Email is already in use!")
                                        .isSuccessful(false)
                                        .statusCode(400)
                                        .build();
                        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
                }

                // Check if the phone number is already in use
                if (userDetailsServiceImpl.existsByPhone(signUpRequest.getPhone())) {
                        APIResponse apiResponse = APIResponse.builder()
                                        .message("Error: Phone number is already in use!")
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

                // Saving user information to the customer table
                Customer customer = Customer.builder()
                                .phone(signUpRequest.getPhone())
                                .email(signUpRequest.getEmail())
                                .build();
                customerService.saveCustomer(customer);

                APIResponse apiResponse = APIResponse.builder()
                                .message("User registered successfully!")
                                .isSuccessful(true)
                                .statusCode(200)
                                .build();
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        @PostMapping("/auth/refreshtoken")
        public ResponseEntity<?> refreshToken(@CookieValue(name= "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
                if (refreshToken == null) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body("Unauthorized access, refresh token is missing.");
                }
                // Validate refresh token
                try {
                if (!jwtUtils.validateRefreshToken(refreshToken)) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body("Unauthorized access, refresh token is invalid.");
                }
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body("Unauthorized access, refresh token is invalid.");
                }
                // Get username from refresh token
                String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
                // Load user details
                UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(username);
                // Create authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                // Set authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // Generate JWT token
                String jwt = jwtUtils.generateJwtToken(authentication);
                // Create a new cookie
                Cookie jwtCookie = new Cookie("token", jwt);
                // Set the cookie to HTTP-only for security
                jwtCookie.setHttpOnly(true);
                // Optionally, set the cookie to secure if you're using HTTPS
                // jwtCookie.setSecure(true);
                jwtCookie.setPath("/");
                jwtCookie.setMaxAge(3600);
                // Add the cookie to the response
                response.addCookie(jwtCookie);
                // Create a response object
                APIResponse apiResponse = APIResponse.builder()
                                .message("Token refreshed successfully!")
                                .data(JwtResponse.builder()
                                                .id(userDetails.getId())
                                                .token(jwt)
                                                .username(userDetails.getUsername())
                                                .roles(userDetails.getAuthorities().stream()
                                                                .map(GrantedAuthority::getAuthority)
                                                                .collect(Collectors.toList()))
                                                .build())
                                .isSuccessful(true)
                                .statusCode(200)
                                .build();
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        @PostMapping("/auth/logout")
        public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
                // Get the token from the request
                String jwt = jwtUtils.getJwtFromCookie(request);

                if (jwt != null) {
                        blacklistedTokens.add(jwt); // Add token to blacklist
                }
                // Create a new JWT cookie
                Cookie jwtCookie = new Cookie("token", "");
                // Set the cookie to HTTP-only for security
                jwtCookie.setHttpOnly(true);
                // Optionally, set the cookie to secure if you're using HTTPS
                // jwtCookie.setSecure(true);
                // Set the cookie to expire immediately
                jwtCookie.setPath("/");
                jwtCookie.setMaxAge(0);
                // Create a new refresh token cookie
                Cookie refreshTokenCookie = new Cookie("refreshToken", "");
                // Set the cookie to HTTP-only for security
                refreshTokenCookie.setHttpOnly(true);
                // Optionally, set the cookie to secure if you're using HTTPS
                // jwtCookie.setSecure(true);
                // Set the cookie to expire immediately
                refreshTokenCookie.setPath("/");
                refreshTokenCookie.setMaxAge(0);
                // Add the cookie to the response
                response.addCookie(jwtCookie);
                response.addCookie(refreshTokenCookie);
                // Clear the SecurityContext
                SecurityContextHolder.clearContext();
                // Create a response object
                APIResponse apiResponse = APIResponse.builder()
                                .message("Logout successful!")
                                .isSuccessful(true)
                                .statusCode(200)
                                .build();
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        public static boolean isTokenBlacklisted(String token) {
                return blacklistedTokens.contains(token);
        }
}
