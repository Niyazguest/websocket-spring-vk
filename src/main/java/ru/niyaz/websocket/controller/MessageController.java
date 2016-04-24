package ru.niyaz.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import ru.niyaz.websocket.dto.MessageDTO;
import ru.niyaz.websocket.service.AuthenticationService;
import ru.niyaz.websocket.service.MessageService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 16.04.2016.
 */

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AuthenticationService authenticationService;

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/addMessage")
    public void addStock(MessageDTO messageDTO) throws Exception {
        messageDTO.setDate(new Date());
        messageDTO.setAuthor(authenticationService.getCurrentUserInfo());
        messageService.saveMessage(messageDTO);
        broadcastUpdatedMessages(messageDTO);
    }


    @Autowired
    public void setSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    private void broadcastUpdatedMessages(MessageDTO message) {
        simpMessagingTemplate.convertAndSend("/topic/message", message);
    }

}
