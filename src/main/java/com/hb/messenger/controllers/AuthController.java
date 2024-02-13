package com.hb.messenger.controllers;

import com.hb.messenger.exceptions.ErrorCode;
import com.hb.messenger.models.enums.Status;
import com.hb.messenger.models.request.AuthRequest;
import com.hb.messenger.models.response.AuthResponse;
import com.hb.messenger.models.response.GenericResponse;
import com.hb.messenger.services.JwtService;
import com.hb.messenger.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth Apis")
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }



    @PostMapping("/login")
    public ResponseEntity<GenericResponse<?>> login(@RequestBody AuthRequest authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName()).data(AuthResponse.builder().token(jwtService.generateToken(authRequestDTO.getUsername())).build()).build());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(GenericResponse.builder().
                    status(Status.FAILURE.getName()).message(ErrorCode.AUTH_ERROR.getMessage()).build());
        }
    }
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestHeader("Authorization") String auth) {
//        // Extract token from request header or cookie
//
//        JwtParser jwtParser = Jwts.parser()
//                .build();
//        try {
//            jwtParser.parse(auth);
//        } catch (Exception e) {
//            throw new Exception("Could not verify JWT token integrity!", e);
//        }
//        String token = jwt.
//
//        // Here, you could add the token to a blacklist in a database or cache
//        // For simplicity, let's assume we just invalidate the token without storing it
//
//        return ResponseEntity.ok("Logged out successfully");
//    }


}
