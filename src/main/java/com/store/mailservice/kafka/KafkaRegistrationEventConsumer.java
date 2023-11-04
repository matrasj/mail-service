package com.store.mailservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.mailservice.model.RegistrationEventPayload;
import com.store.mailservice.service.EmailSenderService;
import com.store.mailservice.templatesfactory.MailBodyTemplatesFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaRegistrationEventConsumer {
    @Value("${email.properties.our-email}")
    private String ourEmail;
    @Value("${email.properties.confirmation-token-url}")
    private String confirmationTokenUrl;
    private final ObjectMapper objectMapper;
    private final EmailSenderService emailSenderService;

    @KafkaListener(topics = "${kafka.topic.registrations.name}", groupId = "${kafka.topic.registrations.group-id}")
    public void sendMailAboutAccountConfirmation(String registrationEvent) throws JsonProcessingException {
        RegistrationEventPayload registrationEventPayload
                = this.objectMapper.readValue(registrationEvent, RegistrationEventPayload.class);

        this.emailSenderService.sendEmail(
                this.ourEmail,
                registrationEventPayload.getEmail(),
                "Confirm your email",
                MailBodyTemplatesFactory.buildTemplateForEmailConfirmation(
                        String.format("%s %s", registrationEventPayload.getFirstname(), registrationEventPayload.getLastname()),
                        String.format("%s/%s", this.confirmationTokenUrl, registrationEventPayload.getConfirmationTokenId()))
        );

    }
}
