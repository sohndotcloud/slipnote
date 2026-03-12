package com.sohn.SlipNote.controller;


import com.sohn.SlipNote.model.MessageRequest;
import com.sohn.SlipNote.model.PostRequest;
import com.sohn.SlipNote.model.PostResponse;
import com.sohn.SlipNote.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/{messageId}")
    public String getMessageById(@PathVariable String messageId, @RequestBody MessageRequest request) {
        String message;
        try {
            message = messageService.validate(messageId, request.getKey());
        } catch (Exception e) {
            return "Invalid key";
        }

        return message;
    }

    @PostMapping("/post")
    public PostResponse saveMessage(@RequestBody PostRequest request) {
        String message = request.getMessage();
        PostResponse postResponse;
        try {
            postResponse = messageService.post(message);
        } catch (Exception e) {
            return null;
        }
        return postResponse;
    }
}
