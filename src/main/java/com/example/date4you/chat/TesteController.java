package com.example.date4you.chat;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TesteController {

    @Autowired
    private QueueSender queueSender;

    @Autowired
    private QueueConsumer queueConsumer;

    @GetMapping(value = "/teste")
    public String getPage(Model model) {

        return "teste";
    }

    @PostMapping(value = "/teste")
    public String send(@RequestParam(value = "mess") String mess) {

        queueSender.send(mess);

        return "teste";
    }
}
