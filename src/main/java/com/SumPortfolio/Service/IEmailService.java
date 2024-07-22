package com.SumPortfolio.Service;

import jakarta.mail.MessagingException;

public interface IEmailService {

    void sendEmailWithToken(String userEmail, String link) throws MessagingException;
}
