package com.ydova.ahub.converter;

import com.ydova.ahub.dto.SchoolDto;
import com.ydova.ahub.dto.SchoolDto;
import com.ydova.ahub.entity.School;
import com.ydova.ahub.dto.LanguageLevel;
import com.ydova.ahub.dto.TimeLineEntryDto;
import com.ydova.ahub.entity.School;

import java.util.List;
import java.util.stream.Collectors;

public class SchoolConverter {

    // Converts an entity to a DTO
    public static SchoolDto toDto(School school) {
        if (school == null) {
            return null;
        }

        // Create the DTO from the entity
        return SchoolDto.builder().email(school.getEmail()).id(school.getId()).place(school.getPlace())
                .build();
    }

    // Converts a DTO to an entity
    public static School toEntity(SchoolDto dto) {
        if (dto == null) {
            return null;
        }

        // Create the entity from the DTO
        School school = new School();
        school.setEmail(dto.getEmail());
        school.setPlace(dto.getPlace());
        school.setId(dto.getId());


        return school;
    }

    // Convert a list of entities to DTOs
    public static List<SchoolDto> toDtoList(List<School> schools) {
        return schools.stream()
                .map(SchoolConverter::toDto)
                .collect(Collectors.toList());
    }

    // Convert a list of DTOs to entities
    public static List<School> toEntityList(List<SchoolDto> dtos) {
        return dtos.stream()
                .map(SchoolConverter::toEntity)
                .collect(Collectors.toList());
    }
}
