package com.ydova.ahub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydova.Log;
import com.ydova.ahub.entity.Application;
import com.ydova.ahub.entity.Email;
import com.ydova.ahub.repository.ApplicationRepository;
import com.ydova.ahub.service.ApplicationService;
import com.ydova.ahub.service.EmailService;
import com.ydova.cv.YdovaException;
import com.ydova.mail.dto.EmailDto;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Tag(name = "Application Operations", description = "Operations for managing applications")
@RestController
@RequestMapping("/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {


    private final ApplicationRepository applicationRepository;

    private final ApplicationService applicationService;
    private final EmailService emailService;


    @Autowired
    public ApplicationController(ApplicationRepository clientService, ApplicationService applicationService, EmailService emailService) {
        this.applicationRepository = clientService;
        this.applicationService = applicationService;
        this.emailService = emailService;
    }

    @Operation(summary = "Get all clients", description = "Returns a list of all clients")
    @GetMapping("{email}")
    public List<Application> getAllClients(@PathVariable String email) {
        System.out.println(email);
        return applicationRepository.findBySender(email);
    }


    @PostMapping("/send")
    public void sendMail(
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            @RequestPart("content") String otherData) throws YdovaException {

        ObjectMapper objectMapper = new ObjectMapper();
        EmailDto email;
        try {
            email = objectMapper.readValue(otherData, EmailDto.class);
        } catch (JsonProcessingException e) {
            throw new YdovaException(e.getMessage());
        }

        // Validate the EmailDto object
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<EmailDto>> violations = validator.validate(email);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors:");
            for (ConstraintViolation<EmailDto> violation : violations) {
                errorMessage.append(" ").append(violation.getPropertyPath())
                        .append(": ").append(violation.getMessage()).append(";");
            }
            throw new YdovaException(errorMessage.toString());
        }

        if (files != null) {

            email.setAttachments(Arrays.asList(files));
        }

        Log.info("Sending mail: " + email, this.getClass());


        if (email.getRecipients().equals("ALL_PFLEGE_ALL")) {
            email.setRecipients(String.join(",", emailService.readAll().stream().map(Email::getEmail).toArray(String[]::new)));
        }

            applicationService.saveJob(email);

    }


}
