package com.fd.finema.services;

import javax.mail.MessagingException;

public interface MailSenderService {
    void sendMail(String recipient, String subject, String msg) throws MessagingException;
}
