package com.ydova.mail.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydova.Log;
import com.ydova.cv.YdovaException;
import com.ydova.mail.dto.EmailDto;
import com.ydova.mail.dto.EmailDto2;
import com.ydova.mail.dto.EmailSendingResponseDto;
import com.ydova.mail.service.GmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "*") // Allows all origins
@RestController
@Tag(name = "Email Sending Controller", description = "APIs for sending emails with optional attachments")
public class EmailSendingController {
    private final GmailService gmailService;

    @Autowired
    public EmailSendingController(GmailService gmailService) {
        this.gmailService = gmailService;
    }

    @Operation(summary = "Send an email", description = "Sends an email with optional attachments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid email data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @PostMapping("/send-mail")
    public List<EmailSendingResponseDto> sendMail(
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            @RequestPart("content") String otherData) throws YdovaException {

        ObjectMapper objectMapper = new ObjectMapper();
        EmailDto2 email;
        try {
            email = objectMapper.readValue(otherData, EmailDto2.class);
        } catch (JsonProcessingException e) {
            throw new YdovaException(e.getMessage());
        }

        // Validate the EmailDto object
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<EmailDto2>> violations = validator.validate(email);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors:");
            for (ConstraintViolation<EmailDto2> violation : violations) {
                errorMessage.append(" ").append(violation.getPropertyPath())
                        .append(": ").append(violation.getMessage()).append(";");
            }
            throw new YdovaException(errorMessage.toString());
        }

        if (files != null) {
            List<File> convertedFiles = Arrays.stream(files)
                    .map(this::convertMultipartFileToFile)
                    .toList();
            email.setAttachments(new ArrayList<>(convertedFiles));
        }

        Log.info("Sending mail: " + email, this.getClass());

        return gmailService.sendEmail(email);
    }

    @Operation(summary = "Test API", description = "Returns a welcome message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API is running")
    })
    @GetMapping("/")
    public String get() {
        return "Welcome to YDOVA WEB SERVER 1";
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) {
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            Log.error("Error while converting the MultipartFile to a File");
        }
        return file;
    }
}
