package com.losolved.nasa.controller;

import com.losolved.nasa.dto.MessageQueue;
import com.losolved.nasa.dto.ResponseDTO;
import com.losolved.nasa.services.PublishMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NavigationController {

    private final PublishMessageService publishMessageService;

    @Autowired
    public NavigationController(PublishMessageService publishMessageService) {
        this.publishMessageService = publishMessageService;
    }

    @PostMapping("/api/navigation")
    public ResponseDTO publishMessage(@RequestBody MessageQueue message) {
        // Assuming publishMessageService.sendMessage returns a ResponseDTO
      //  ResponseDTO response = publishMessageService.sendMessage(message);
        return null;
    }
}