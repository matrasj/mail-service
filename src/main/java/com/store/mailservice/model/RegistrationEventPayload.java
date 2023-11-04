package com.store.mailservice.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegistrationEventPayload  {
    private String email;
    private String firstname;
    private String lastname;
    private Long confirmationTokenId;
}
