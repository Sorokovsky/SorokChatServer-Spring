package pro.sorokovsky.sorokchatserverspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pro.sorokovsky.sorokchatserverspring.contract.NewMessagePayload;
import pro.sorokovsky.sorokchatserverspring.mapper.MessageMapper;
import pro.sorokovsky.sorokchatserverspring.service.MessagesService;

@Controller
@RequiredArgsConstructor
public class MessagesController {
    private final MessagesService service;
    private final MessageMapper mapper;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public NewMessagePayload sendMessage(@Valid @Payload NewMessagePayload message) {
        return message;
    }
}
