package com.kh.fitness.service;

import com.kh.fitness.service.email.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {
    @Mock
    private JavaMailSender emailSender;
    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void sendSimpleEmail() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("dummy");
        msg.setSubject("dummy");
        msg.setText("dummy");

        doNothing().when(emailSender).send(msg);
        emailService.sendSimpleEmail("dummy", "dummy", "dummy");

        verify(emailSender).send(msg);
    }

    @Test
    void sendSimpleEmail_shouldThrowException() {
        var text = "dummy";
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(text);
        msg.setSubject(text);
        msg.setText(text);

        doThrow(MailParseException.class).when(emailSender).send(msg);

        assertThrows(MailParseException.class, () -> emailService.sendSimpleEmail(text, text, text));
        verify(emailSender).send(msg);
    }
}