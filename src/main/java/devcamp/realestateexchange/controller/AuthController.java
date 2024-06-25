package devcamp.realestateexchange.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import devcamp.realestateexchange.dto.APIResponse;
import devcamp.realestateexchange.dto.JwtResponse;
import devcamp.realestateexchange.dto.LoginRequest;
import devcamp.realestateexchange.dto.SignupRequest;
import devcamp.realestateexchange.entity.User;
import security.jwt.JwtUtils;
import security.services.UserDetailsImpl;
import security.services.UserDetailsServiceImpl;

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
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getPhone()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userDetailsServiceImpl.existsByUsername(signUpRequest.getUsername())) {
            APIResponse apiResponse = APIResponse.builder()
                        .message("Error: Username is already taken!")
                        .isSuccessful(false)
                        .statusCode(400)
                        .build();
            return  new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        if (userDetailsServiceImpl.existsByEmail(signUpRequest.getEmail())) {
            APIResponse apiResponse = APIResponse.builder()
                        .message("Error: Email is already in use!")
                        .isSuccessful(false)
                        .statusCode(400)
                        .build();
            return  new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
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
