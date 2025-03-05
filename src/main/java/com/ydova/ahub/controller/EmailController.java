package com.ydova.ahub.controller;

import com.ydova.ahub.entity.Email;
import com.ydova.ahub.service.EmailService;
import com.ydova.cv.YdovaException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Email Controller", description = "APIs for managing school-related emails")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "Add a new school email", description = "Stores a new school email in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/emails")
    public Email add(@RequestBody @Valid Email email) throws YdovaException {
        return emailService.create(email);
    }

    @Operation(summary = "Get all school emails", description = "Retrieves a list of all school emails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of emails retrieved successfully")
    })
    @GetMapping("/emails")
    public List<Email> readAll() {
        return emailService.readAll();
    }

    @Operation(summary = "Add multiple school emails", description = "Stores a list of school emails in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emails successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/emails-list")
    public String addSchoolList(@RequestBody @Valid List<Email> email) throws YdovaException {
        emailService.createAll(email);
        return "School Successfully created";
    }

    @Operation(summary = "Delete a school email", description = "Removes a school email from the system using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Email not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/emails/{id}")
    public String remove(@PathVariable("id") Long id) {
        emailService.remove(id);
        return "School Successfully deleted";
    }


    @Operation(summary = "Delete a school email", description = "Removes a school email from the system using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Email not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/emails")
    public List<String> remove(@RequestBody List<String> emails) {
      return   emailService.removeALl(emails);
    }
}
