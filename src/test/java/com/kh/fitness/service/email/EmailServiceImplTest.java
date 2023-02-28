package com.kh.fitness.service.email;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.kh.fitness.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class EmailServiceImplTest extends IntegrationTestBase {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("testuser@greenmail.com", "testPasswordGreenMail"))
            .withPerMethodLifecycle(false);

    private final EmailServiceImpl emailService;

    public static final String TO_ADDRESS = "to@test.com";
    public static final String SUBJECT = "testSubjectMail";
    public static final String CONTENT = "testMessageContent";

    @Test
    void sendSimpleEmail() throws MessagingException, IOException {
        emailService.sendSimpleEmail(TO_ADDRESS, SUBJECT, CONTENT);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        MimeMessage receivedMessage = receivedMessages[0];

        assertThat(receivedMessage.getAllRecipients()[0].toString()).isEqualTo(TO_ADDRESS);
        assertThat(receivedMessage.getSubject().trim()).isEqualTo(SUBJECT);
        assertThat(receivedMessage.getContent().toString().trim()).isEqualTo(CONTENT);
    }
}