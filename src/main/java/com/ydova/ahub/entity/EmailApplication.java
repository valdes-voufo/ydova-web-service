package com.ydova.ahub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Table(name = "email_application_table")
@Entity
@Data
public class EmailApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Sender email is required")
    @Email(message = "Sender email should be valid")
    private String sender;

    @Lob
    @Column(columnDefinition = "TEXT")
    @NotEmpty(message = "At least one recipient must be present")
    private String recipients;

    @Lob
    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "The email subject is required")
    private String subject;

    @Lob
    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Email body is required")
    private String body;


    @ElementCollection
    private List<String> attachmentNames; // Store file names

    @ElementCollection
    private List<String> attachmentTypes; // Store file content types

    @ElementCollection
    private List<byte[]> attachmentData; // Store file data as byte arrays

    @NotBlank(message = "The Gmail password of the recipient is required")
    private String password;
}
