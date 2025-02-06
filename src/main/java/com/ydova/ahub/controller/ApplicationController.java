package com.ydova.ahub.controller;

import com.ydova.ahub.entity.Application;
import com.ydova.ahub.entity.Client;
import com.ydova.ahub.repository.ApplicationRepository;
import com.ydova.ahub.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "Application Operations", description = "Operations for managing applications")
@RestController
@RequestMapping("/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {


        private final ApplicationRepository applicationRepository;


        @Autowired
        public ApplicationController(ApplicationRepository clientService) {
            this.applicationRepository = clientService;
        }

        @Operation(summary = "Get all clients", description = "Returns a list of all clients")
        @GetMapping("{email}")
        public List<Application> getAllClients(@PathVariable String email) {
            System.out.println(email);
            return applicationRepository.findBySender(email);
        }






}
