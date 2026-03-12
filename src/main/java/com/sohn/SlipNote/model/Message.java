package com.sohn.SlipNote.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "messages")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // auto-increment numeric ID

    @Column(name = "string_id", nullable = false, unique = true)
    private String stringId;         // your string ID

    @Column(name = "message_key", nullable = false)
    private String messageKey;       // key string

    @Column(name = "message", nullable = false)
    private String message;          // message content
}