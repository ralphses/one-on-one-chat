package com.example.oneononechat.controller;

import com.example.oneononechat.dto.User;
import com.example.oneononechat.model.AppUser;
import com.example.oneononechat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppUserController {

    private final UserService userService;
    private final SimpMessagingTemplate template;

    @MessageMapping("/user.addUser")
    public User addUser(@Payload User user) {

        System.out.println("user = " + user);
        AppUser user1 = AppUser.builder()
                .nickName(user.nickName())
                .fullName(user.fullName())
                .status(user.status())
                .build();
        userService.saveUser(user1);

        String id = "/user/" + user1.getNickName() + "/queue/messages";
        template.convertAndSend(id, user1);
        return user;
    }

    @MessageMapping("/user.disconnect")
    @SendTo("/user/public")
    public AppUser disconnect(@Payload AppUser user) {
        userService.disconnectUser(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> findConnectedUsers() {
        return ResponseEntity.ok(userService.findConnectedUsers());
    }
}
