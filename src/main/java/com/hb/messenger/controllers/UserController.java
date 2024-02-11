package com.hb.messenger.controllers;

import com.hb.messenger.exceptions.ErrorCode;
import com.hb.messenger.exceptions.MessengerException;
import com.hb.messenger.models.enums.Status;
import com.hb.messenger.models.request.UserRequest;
import com.hb.messenger.models.response.GenericResponse;
import com.hb.messenger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation
    @ApiResponse
    public ResponseEntity<GenericResponse<?>> createUser(@RequestBody UserRequest userRequest) {
        try {
            userService.createUser(userRequest.getUsername(), userRequest.getPasscode());
        } catch (MessengerException messengerException) {
            if (messengerException.getErrorCode() == ErrorCode.DUPLICATE_USER) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(GenericResponse.builder().
                        status(Status.FAILURE.getName()).message(messengerException.getErrorCode().getMessage()).build());

            }
        }
        return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName())
                .message("User created successfully").build());
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation
    @ApiResponse
    public ResponseEntity<GenericResponse> fetchUsers() {

        return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName())
                .data(userService.fetchAllUsers()).build());
    }
}


