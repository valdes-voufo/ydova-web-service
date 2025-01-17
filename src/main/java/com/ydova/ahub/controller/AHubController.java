package com.ydova.ahub.controller;

import com.ydova.ahub.dto.AHubClientDto;
import com.ydova.ahub.service.AHubClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RequestMapping("/client")
@RestController
public class AHubController {
    private final AHubClientService service;

    @Autowired
    public AHubController(AHubClientService service) {
        this.service = service;
    }

    @PostMapping
    public AHubClientDto addClient(@RequestBody AHubClientDto AHubClientDto) {
        AHubClientDto.setStatus("Student");
        return service.create(AHubClientDto);
    }


    @DeleteMapping("/{id}")
    public String removeClient(@PathVariable Long id) {
        return service.remove(id);

    }


    @PutMapping("/{id}")
    public AHubClientDto updateClient(@PathVariable Long id, @RequestBody AHubClientDto updatedClient) {
        return service.create(updatedClient);
    }


    @GetMapping("/{id}")
    public AHubClientDto getClient(@PathVariable Long id) {
        return service.read(id);
    }


    @GetMapping
    public List<AHubClientDto> getClients() {
        return service.readAll();
    }
}
