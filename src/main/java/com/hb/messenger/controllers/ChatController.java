package com.hb.messenger.controllers;

import com.hb.messenger.exceptions.ErrorCode;
import com.hb.messenger.exceptions.MessengerException;
import com.hb.messenger.models.enums.Status;
import com.hb.messenger.models.request.SendMessageRequest;
import com.hb.messenger.models.response.GenericResponse;
import com.hb.messenger.models.response.UnreadMessagesDto;
import com.hb.messenger.services.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/{username}/message")
@Tag(name = "Chat Apis")
@SecurityScheme(name = "Bearer", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, paramName = "Authorization")
@SecurityRequirement(name = "Bearer")
public class ChatController {

  private final ChatService chatService;

  public ChatController(ChatService chatService) {
    this.chatService = chatService;
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Fetch Unread Messages")
  @ApiResponse
  public ResponseEntity<GenericResponse<?>> fetchUnreadMessages(UserDetails userDetails,
      @PathVariable("username") String username) {

    if(!userDetails.getUsername().equals(username)){
      throw MessengerException.error(ErrorCode.AUTHORIZATION_ERROR);
    }
    List<UnreadMessagesDto> unreadMessagesDtoList = chatService.fetchUnreadMessages(username);
    if (unreadMessagesDtoList.isEmpty()) {
      return ResponseEntity.ok(
          GenericResponse.builder().status(Status.SUCCES.getName()).message("No new messages")
              .build());
    }
    return ResponseEntity.ok(
        GenericResponse.builder().status(Status.SUCCES.getName()).message("You have message(s)")
            .data(unreadMessagesDtoList).build());
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Send Message")
  @ApiResponse
  public ResponseEntity<GenericResponse<?>> sendMessage(
      @PathVariable("username") final String username,
      @RequestBody SendMessageRequest sendMessageRequest) {

    chatService.sendMessage(sendMessageRequest.getTo(), username, sendMessageRequest.getText(),
        sendMessageRequest.getType());

    return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName()).build());
  }

  @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Fetch Chat History")
  @ApiResponse
  public ResponseEntity<GenericResponse<?>> fetchChatHistory(
      @PathVariable("username") String username, @RequestParam("friend") String friend) {
    return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName())
        .data(chatService.fetchChatHistory(username, friend)).build());
  }

  @GetMapping(value = "/group/history", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Fetch Group History")
  @ApiResponse
  public ResponseEntity<GenericResponse<?>> fetchGroupChatHistory(
      @RequestParam("group") String group) {
    return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName())
        .data(chatService.fetchGroupChatHistory(group)).build());
  }

}
