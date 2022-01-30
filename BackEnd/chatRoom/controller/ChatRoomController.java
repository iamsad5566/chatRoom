package com.chatRoom.chatRoom.controller;

import com.chatRoom.chatRoom.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ChatRoomController {
    @Autowired
    WebSocketMessageBrokerStats webSocketMessageBrokerStats;
    Map<String, String> map = new HashMap<>();

    @Async("doSomethingExecutor")
    @MessageMapping("message")
    @SendTo("/topic/chatRoom")
    public Message receiveMessage(@Payload Message message) {
        String date = getDate();
        message.setTime(date);
        System.out.println(webSocketMessageBrokerStats.getWebSocketSessionStatsInfo());
        return message;
    }

    @Async("doSomethingExecutor")
    public String getDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        String dateS = formatter.format(date);

        int taiwanHour = Integer.valueOf(dateS.substring(0,2))+8;
        if (taiwanHour >= 24)
            taiwanHour -= 24;

        dateS = (taiwanHour < 10 ? "0":"") + String.valueOf(taiwanHour) + dateS.substring(2, dateS.length());

        return dateS;
    }

    @Async("doSomethingExecutor")
    @MessageMapping("getActiveSessions")
    @SendTo("/sessions/number")
    public int getNumber(@Payload Message message) throws Throwable{
        return Integer.valueOf(webSocketMessageBrokerStats.getWebSocketSessionStatsInfo().split(" ")[0]);
    }
}
