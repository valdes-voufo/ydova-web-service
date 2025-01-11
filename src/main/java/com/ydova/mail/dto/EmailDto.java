package com.ydova.mail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.File;
import java.util.List;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    @NotBlank(message = "Sender email is required")
    @Email(message = "sender email should be valid")
    private String sender;

    @NotEmpty(message = "at least one recipient must be present")
    private String recipients;

    @NotBlank(message = "the email subject is required")
    @NotNull
    private String subject;

    @NotBlank(message = "Sender email is required")
    @NotNull
    private String body;

    private List<File> attachments;

    @NotBlank(message = "The gmail password of the recipient is required is required")
    @NotNull
    private String password;


}
