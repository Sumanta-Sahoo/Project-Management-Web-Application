package com.SumPortfolio.Service;

import com.SumPortfolio.Model.Invitation;
import jakarta.mail.MessagingException;

public interface IInvitationService {

    void sendInvitation(String email, Long projectId) throws MessagingException;
    Invitation acceptInvitation(String token, Long userId) throws Exception;
    String getTokenByUserEmail(String userEmail);
    void deleteToken(String token);
}
