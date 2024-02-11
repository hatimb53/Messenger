package com.hb.messenger.controllers;

import com.hb.messenger.models.enums.Status;
import com.hb.messenger.models.request.SendMessageRequest;
import com.hb.messenger.models.response.GenericResponse;
import com.hb.messenger.models.response.ReceivedMessage;
import com.hb.messenger.services.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{username}/message")
@Tag(name="Chat Apis")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(value = "" ,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Fetch Unread Messages")
    @ApiResponse
    public ResponseEntity<GenericResponse<?>> fetchUnreadMessages(@PathVariable("username") String username) {

        List<ReceivedMessage> receivedMessageList = chatService.fetchUnreadMessages(username);
        if(receivedMessageList.isEmpty()){
            return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName()).message("No new messages")
                   .build());
        }
        return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName()).message("You have message(s)")
                .data(receivedMessageList).build());
    }
    @PostMapping(value="",  produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Send Message")
    @ApiResponse
    public ResponseEntity<GenericResponse<?>> sendMessage(@PathVariable("username") final String username,@RequestBody SendMessageRequest sendMessageRequest) {

        chatService.sendMessage(sendMessageRequest.getTo(),username,sendMessageRequest.getText());

        return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName()).build());
    }

    @GetMapping(value="/history",  produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Fetch Chat History")
    @ApiResponse
    public ResponseEntity<GenericResponse<?>> fetchChatHistory(@PathVariable("username") String username,@RequestParam("friend") String friend) {
        return ResponseEntity.ok(GenericResponse.builder().status(Status.SUCCES.getName()).data(chatService.fetchChatHistory(username,friend)).build());
    }

}
