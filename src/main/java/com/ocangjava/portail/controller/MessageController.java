package com.ocangjava.portail.controller;

import com.ocangjava.portail.dto.MessageRequest;
import com.ocangjava.portail.dto.MessageResponse;
import com.ocangjava.portail.model.Message;
import com.ocangjava.portail.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> createMessage(@RequestBody MessageRequest request) {
        messageService.createMessage(request);
        return ResponseEntity.ok(MessageResponse.builder()
                .message("Message send with success")
                .build());
    }
}