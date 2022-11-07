package com.example.date4you.chat;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QueueConsumer {



    List<String> receivedMessages = new ArrayList<>();

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload String message) {
        System.out.println("New Message : " +message + " ");
        receivedMessages.add(message);
    }

    public List<String> listMessages() {
        return receivedMessages;
    }




}
