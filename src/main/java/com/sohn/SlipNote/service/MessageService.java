package com.sohn.SlipNote.service;

import com.sohn.SlipNote.model.PostResponse;
import com.sohn.SlipNote.repository.MessageRepository;
import com.sohn.SlipNote.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sohn.SlipNote.model.Message;

import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public String validate(String id, String key) {
        Message message = messageRepository.findByStringId(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        if (!message.getMessageKey().equals(key)) {
            throw new RuntimeException("Invalid key");
        }

        return message.getMessage();
    }

    public PostResponse post(String message) {
        String id = MessageUtil.generateId();
        while (!messageRepository.findByStringId(id).isEmpty()) {
            id = MessageUtil.generateId();
        }

        String key = MessageUtil.generateKey();
        Message data = new Message();
        data.setStringId(id);
        data.setMessageKey(key);
        data.setMessage(message);
        Message response = messageRepository.save(data);

        PostResponse postResponse = new PostResponse();
        postResponse.setId(id);
        postResponse.setKey(key);
        return postResponse;
    }


}
