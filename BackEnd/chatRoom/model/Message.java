package com.chatRoom.chatRoom.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    private String name;
    private String message;
    private String time;
    private String status;
}
