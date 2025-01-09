package com.ydova.ahub.converter;

import com.ydova.ahub.dto.AHubClientDto;
import com.ydova.ahub.entity.AHubClient;
import com.ydova.ahub.dto.LanguageLevel;
import com.ydova.ahub.dto.TimeLineEntryDto;

import java.util.List;
import java.util.stream.Collectors;

public class AHubClientConverter {

    // Converts an entity to a DTO
    public static AHubClientDto toDto(AHubClient client) {
        if (client == null) {
            return null;
        }

        // Create the DTO from the entity
        return AHubClientDto.builder()
                .lastname(client.getLastname())
                .firstname(client.getFirstname())
                .street(client.getStreet())
                .city(client.getCity())
                .country(client.getCountry())
                .email(client.getEmail())
                .phone(client.getPhone())
                .birthday(client.getBirthday())
                .birthplace(client.getBirthplace())
                .children(client.getChildren())
                .civilState(client.getCivilState())
                .nationality(client.getNationality())
                .status(client.getStatus())
                .build();
    }

    // Converts a DTO to an entity
    public static AHubClient toEntity(AHubClientDto dto) {
        if (dto == null) {
            return null;
        }

        // Create the entity from the DTO
        AHubClient client = new AHubClient();
        client.setLastname(dto.getLastname());
        client.setFirstname(dto.getFirstname());
        client.setStreet(dto.getStreet());
        client.setCity(dto.getCity());
        client.setCountry(dto.getCountry());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());
        client.setBirthday(dto.getBirthday());
        client.setBirthplace(dto.getBirthplace());
        client.setChildren(dto.getChildren());
        client.setCivilState(dto.getCivilState());
        client.setNationality(dto.getNationality());
        client.setStatus(dto.getStatus());

        return client;
    }

    // Convert a list of entities to DTOs
    public static List<AHubClientDto> toDtoList(List<AHubClient> clients) {
        return clients.stream()
                .map(AHubClientConverter::toDto)
                .collect(Collectors.toList());
    }

    // Convert a list of DTOs to entities
    public static List<AHubClient> toEntityList(List<AHubClientDto> dtos) {
        return dtos.stream()
                .map(AHubClientConverter::toEntity)
                .collect(Collectors.toList());
    }
}
