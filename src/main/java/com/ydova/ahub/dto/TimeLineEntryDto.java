package com.ydova.ahub.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TimeLineEntryDto {
    String institution;
    String startMonth;
    String endMonth;
    String startYear;
    String endYear;
    String place;
    String task;
    Set<String> taskDescription;


    public String getPeriod() {
        return startMonth + " " + startYear + "-" + endMonth + " " + endYear;
    }
}