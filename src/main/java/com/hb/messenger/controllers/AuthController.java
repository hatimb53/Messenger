package com.hb.messenger.controllers;

import com.hb.messenger.exceptions.ErrorCode;
import com.hb.messenger.exceptions.MessengerException;
import com.hb.messenger.models.enums.Status;
import com.hb.messenger.models.request.AuthRequest;
import com.hb.messenger.models.response.AuthResponse;
import com.hb.messenger.models.response.GenericResponse;
import com.hb.messenger.services.AuthService;
import com.hb.messenger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth Apis")
@SecurityScheme(name = "Bearer", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, paramName = "Authorization")
@SecurityRequirement(name = "Bearer")
@Slf4j
public class AuthController {

  private final UserService userService;

  private final AuthenticationManager authenticationManager;

  private final AuthService authService;


  public AuthController(UserService userService, AuthenticationManager authenticationManager,
      AuthService authService) {
    this.userService = userService;
    this.authenticationManager = authenticationManager;
    this.authService = authService;
  }


  @PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "User Login")
  @ApiResponse
  public ResponseEntity<GenericResponse<?>> login(@RequestBody AuthRequest authRequestDTO) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(),
            authRequestDTO.getPassword()));
    if (authentication.isAuthenticated()) {
      return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName()).data(
          AuthResponse.builder().token(authService.generateToken(authRequestDTO.getUsername()))
              .build()).build());
    } else {
      throw MessengerException.error(ErrorCode.AUTHENTICATION_ERROR);
    }
  }

//  @GetMapping(value="/logout",produces = MediaType.APPLICATION_JSON_VALUE)
//  @Operation(summary = "User Logout")
//  @ApiResponse
//  public ResponseEntity<GenericResponse<?>> logout(@HeaderParam("Authorization") @Parameter(hidden = true,required = true) String auth) {
//
//        // Here, you could add the token to a blacklist in a database or cache
//        // For simplicity, let's assume we just invalidate the token without storing it
//
//        return ResponseEntity.ok("Logged out successfully");
//    }


}
