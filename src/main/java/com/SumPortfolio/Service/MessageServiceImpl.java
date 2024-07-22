package com.SumPortfolio.Service;

import com.SumPortfolio.Model.Chat;
import com.SumPortfolio.Model.Message;
import com.SumPortfolio.Model.User;
import com.SumPortfolio.Repository.MessageRepository;
import com.SumPortfolio.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService{
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IProjectService projectService;
    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepository.findById(senderId)
                .orElseThrow(()-> new Exception("User not found having userId : " + senderId));
        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedDateTime(LocalDateTime.now());
        message.setChat(chat);

        Message savedMessage = messageRepository.save(message);
        chat.getMessageList().add(savedMessage);

        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        return messageRepository.findChatOrderByCreationTimeAsc(chat.getId());
    }
}
