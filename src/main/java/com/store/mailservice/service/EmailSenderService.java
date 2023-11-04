package com.store.mailservice.service;

public interface EmailSenderService {
    void sendEmail(String fromEmail, String toEmail, String subject, String emailBody);
}
