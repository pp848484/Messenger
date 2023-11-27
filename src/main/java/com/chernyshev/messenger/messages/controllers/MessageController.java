package com.chernyshev.messenger.messages.controllers;

import com.chernyshev.messenger.exception.myExceptions.FriendshipException;
import com.chernyshev.messenger.messages.dtos.MessageRequest;
import com.chernyshev.messenger.messages.dtos.MessageResponse;
import com.chernyshev.messenger.messages.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/{id}")
    public ResponseEntity<String> sendMessage(@RequestBody @Valid MessageRequest messageRequest, @PathVariable Long id, Principal principal) throws FriendshipException,IllegalStateException {
        messageService.sendMessage(principal.getName(), id,messageRequest.getText());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<MessageResponse>> getMessageHistory(@PathVariable Long id, Principal principal) throws IllegalStateException {
        List<MessageResponse> responses=messageService.getMessageHistory(principal.getName(),id);
        return ResponseEntity.ok().body(responses);
    }
    @DeleteMapping("/{id}")
    public  void deleteMessageHistory(@PathVariable Long id , Principal principal) {
        messageService.deleteMessageHistory(principal.getName(),id);
    }
}