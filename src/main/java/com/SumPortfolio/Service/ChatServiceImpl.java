package com.SumPortfolio.Service;

import com.SumPortfolio.Model.Chat;
import com.SumPortfolio.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements IChatService{

    @Autowired
    private ChatRepository chatReo;
    @Override
    public Chat createChat(Chat chat) {
        return chatReo.save(chat);
    }
}
