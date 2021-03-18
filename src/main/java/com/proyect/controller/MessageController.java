package com.proyect.controller;

import com.proyect.entity.User;
import com.proyect.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class MessageController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public User notificationNewUser(User u) throws InterruptedException {

        u.setId(1);
        u.setFirstName("Diego");
        return u;
    }



}
