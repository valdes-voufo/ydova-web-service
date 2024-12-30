package com.ydova.cv.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TimeLineEntryDto {
    String school;
    String period;
    String place;
    String task;
    Set<String> taskDescription;
}