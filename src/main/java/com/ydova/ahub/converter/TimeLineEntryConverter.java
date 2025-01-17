package com.ydova.ahub.converter;

import com.ydova.ahub.dto.AHubClientDto;
import com.ydova.ahub.dto.TimeLineEntryDto;
import com.ydova.ahub.entity.AHubClient;
import com.ydova.ahub.entity.TimeLineEntry;

import java.util.List;
import java.util.stream.Collectors;

public class TimeLineEntryConverter {

    public static TimeLineEntryDto toDto(TimeLineEntry entity) {
        return TimeLineEntryDto.builder()
                .institution(entity.getInstitution())
                .startMonth(entity.getStartMonth())
                .endMonth(entity.getEndMonth())
                .startYear(entity.getStartYear())
                .endYear(entity.getEndYear())
                .place(entity.getPlace())
                .task(entity.getTask())
                .taskDescription(entity.getTaskDescription())
                .build();
    }

    public static TimeLineEntry toEntity(TimeLineEntryDto dto, AHubClient client) {
        return TimeLineEntry.builder()
                .institution(dto.getInstitution())
                .startMonth(dto.getStartMonth())
                .endMonth(dto.getEndMonth())
                .startYear(dto.getStartYear())
                .endYear(dto.getEndYear())
                .place(dto.getPlace())
                .task(dto.getTask())
                .taskDescription(dto.getTaskDescription())
                .client(client)
                .build();
    }


    // Convert a list of entities to DTOs
    public static List<TimeLineEntryDto> toDtoList(List<TimeLineEntry> clients) {
        return clients.stream()
                .map(TimeLineEntryConverter::toDto)
                .collect(Collectors.toList());
    }

    // Convert a list of DTOs to entities
    public static List<TimeLineEntry> toEntityList(List<TimeLineEntryDto> dtos,AHubClient client) {
        return dtos.stream()
                .map(x->toEntity(x,client))
                .collect(Collectors.toList());
    }
}
