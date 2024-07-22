package com.SumPortfolio.Controller;

import com.SumPortfolio.Model.Chat;
import com.SumPortfolio.Model.Message;
import com.SumPortfolio.Model.User;
import com.SumPortfolio.Request.CreateMessageRequest;
import com.SumPortfolio.Service.IMessageService;
import com.SumPortfolio.Service.IProjectService;
import com.SumPortfolio.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IMessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @RequestBody CreateMessageRequest createMessageRequest
            ) throws Exception{
        User user = userService.findUserById(createMessageRequest.getSenderId());

        Chat chat = projectService.getProjectById(createMessageRequest.getProjectId()).getChat();
        if(chat==null) throw new Exception("Chat not found");
        Message sentMessages = messageService
                .sendMessage(createMessageRequest.getSenderId(), createMessageRequest.getProjectId(), createMessageRequest.getContent());

        return ResponseEntity.ok(sentMessages);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByProjectId(
            @PathVariable(required = false) Long projectId
    ) throws Exception{
        List<Message> messageList = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messageList);
    }
}
