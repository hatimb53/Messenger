package com.hb.messenger.controllers;

import com.hb.messenger.exceptions.ErrorCode;
import com.hb.messenger.exceptions.MessengerException;
import com.hb.messenger.models.CustomUserDetails;
import com.hb.messenger.models.enums.Status;
import com.hb.messenger.models.request.GroupMemberRequest;
import com.hb.messenger.models.request.GroupRequest;
import com.hb.messenger.models.response.GenericResponse;
import com.hb.messenger.services.GroupService;
import com.hb.messenger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@Tag(name = "Group Apis")
@SecurityScheme(name = "Bearer", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, paramName = "Authorization")
@SecurityRequirement(name = "Bearer")
public class GroupController {

  private final GroupService groupService;

  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Create Group")
  @ApiResponse
  public ResponseEntity<GenericResponse<?>> createGroup(Authentication authentication,
      @RequestBody GroupRequest groupRequest) {
    try {
      CustomUserDetails customUserDetails=(CustomUserDetails)authentication.getPrincipal();
      groupService.createGroup(customUserDetails.getUsername(), groupRequest.getName());
    } catch (MessengerException messengerException) {
      if (messengerException.getErrorCode() == ErrorCode.DUPLICATE_GROUP) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(GenericResponse.builder().
            status(Status.FAILURE.getName()).message(messengerException.getErrorCode().getMessage())
            .build());
      }
    }
    return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName())
        .message("Group created successfully").build());
  }

  @PostMapping(value = "/member", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Add Member")
  @ApiResponse
  public ResponseEntity<GenericResponse<?>> addMember(Authentication authentication,
      @RequestBody GroupMemberRequest groupMemberRequest) {
    try {
      CustomUserDetails customUserDetails=(CustomUserDetails)authentication.getPrincipal();

      groupService.addMember(customUserDetails.getUsername(), groupMemberRequest.getGroupName(),
          groupMemberRequest.getUsername());
    } catch (MessengerException messengerException) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponse.builder().
          status(Status.FAILURE.getName()).message(messengerException.getErrorCode().getMessage())
          .build());

    }

    return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName())
        .message("Member added successfully").build());
  }

  @DeleteMapping(value = "/member", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Remove Member")
  @ApiResponse
  public ResponseEntity<GenericResponse<?>> removeMember(Authentication authentication,
      @RequestBody GroupMemberRequest groupMemberRequest) {
    CustomUserDetails customUserDetails=(CustomUserDetails)authentication.getPrincipal();

      groupService.removeMember(customUserDetails.getUsername(),groupMemberRequest.getGroupName(),
          groupMemberRequest.getUsername());

    return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName())
        .message("Member removed successfully").build());
  }

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Fetch All groups")
  @ApiResponse
  public ResponseEntity<GenericResponse<?>> fetchGroups() {
    return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName())
        .data(groupService.fetchAllGroups()).build());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Fetch All Members of the Group")
  @ApiResponse
  public ResponseEntity<GenericResponse<?>> fetchMembers(@RequestParam String name) {
    return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName())
        .data(groupService.fetchAllMembers(name)).build());
  }
}
