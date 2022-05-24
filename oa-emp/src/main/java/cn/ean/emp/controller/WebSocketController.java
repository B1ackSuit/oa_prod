package cn.ean.emp.controller;

import cn.ean.emp.model.dto.ChatMessageDTO;
import cn.ean.emp.model.dto.UserDTO;
import cn.ean.emp.model.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;


/**
 * @author ean
 * @FileName WebSocketController
 * @Date 2022/5/24 14:30
 **/
@Controller
public class WebSocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public WebSocketController() {
    }

    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMessageDTO chatMessage) {
        UserPO user = (UserPO) authentication.getPrincipal();

        chatMessage.setFrom(user.getUsername());
        chatMessage.setFromNickName(user.getName());
        chatMessage.setDate(LocalDateTime.now());

        simpMessagingTemplate.convertAndSendToUser(
                chatMessage.getTo(), "/queue/chat", chatMessage);
    }
}
