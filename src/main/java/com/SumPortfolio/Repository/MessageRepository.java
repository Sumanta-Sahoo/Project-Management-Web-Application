package com.SumPortfolio.Repository;

import com.SumPortfolio.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findChatOrderByCreationTimeAsc(Long chatId);
}
