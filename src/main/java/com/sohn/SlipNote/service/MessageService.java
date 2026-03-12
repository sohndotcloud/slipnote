package com.sohn.SlipNote.service;

import com.sohn.SlipNote.model.PostResponse;
import com.sohn.SlipNote.repository.MessageRepository;
import com.sohn.SlipNote.util.MessageUtil;
import org.springframework.stereotype.Service;
import com.sohn.SlipNote.model.Message;

import java.time.Duration;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private RedisService redisService;

    public MessageService(MessageRepository messageRepository, RedisService redisService) {
        this.messageRepository = messageRepository;
        this.redisService = redisService;
    }

    public String validate(String id, String key) throws Exception {
        Message message = messageRepository.findByStringId(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        if (!message.getMessageKey().equals(key)) {
            throw new RuntimeException("Invalid key");
        }

        var retrievedMessage = redisService.getDecrypted(id + ":" + key);
        return retrievedMessage;
    }

    public PostResponse post(String message) throws Exception {
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
        redisService.setEncrypted(id + ":" + key, message, Duration.ofMinutes(10));
        PostResponse postResponse = new PostResponse();
        postResponse.setId(id);
        postResponse.setKey(key);
        return postResponse;
    }
}
