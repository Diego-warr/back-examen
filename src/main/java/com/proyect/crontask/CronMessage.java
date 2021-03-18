package com.proyect.crontask;

import com.proyect.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CronMessage {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    //@Scheduled(fixedRate = 2000)
    public void sendMessage()
    {

        simpMessagingTemplate.convertAndSend("/topic/greetings",new User(1,"Diego","Rodriguez","diego@gmail.com","------------","A"));
    }
}
