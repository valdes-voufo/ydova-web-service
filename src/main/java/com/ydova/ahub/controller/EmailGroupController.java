package com.ydova.ahub.controller;

import com.ydova.ahub.dto.EmailGroupDto;
import com.ydova.ahub.entity.EmailGroup;
import com.ydova.ahub.service.EmailGroupService;
import com.ydova.cv.YdovaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/email-groups")
@Tag(name = "Email Group Controller", description = "APIs for managing email groups")
public class EmailGroupController {

    private final EmailGroupService emailGroupService;

    @Autowired
    public EmailGroupController(EmailGroupService emailGroupService) {
        this.emailGroupService = emailGroupService;
    }

    @Operation(summary = "Create a new email group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email group created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public EmailGroup create(@RequestBody EmailGroupDto emailGroup) {
        return emailGroupService.create(emailGroup);
    }

    @Operation(summary = "Get all email groups")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email groups retrieved successfully")
    })
    @GetMapping
    public List<EmailGroupDto> getAll() {
        return emailGroupService.getAll();
    }

    @Operation(summary = "Get email group by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email group found"),
            @ApiResponse(responseCode = "404", description = "Email group not found")
    })
    @GetMapping("/{id}")
    public Optional<EmailGroupDto> getById(@PathVariable Long id) {
        return emailGroupService.getById(id);
    }

    @Operation(summary = "Update an existing email group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email group updated successfully"),
            @ApiResponse(responseCode = "404", description = "Email group not found")
    })
    @PutMapping("/{id}")
    public EmailGroupDto update(@PathVariable Long id, @RequestBody EmailGroupDto emailGroup) throws YdovaException {
        return emailGroupService.update(id, emailGroup);
    }

    @Operation(summary = "Delete an email group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email group deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Email group not found")
    })
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        emailGroupService.delete(id);
        return "Email group deleted successfully";
    }
}
