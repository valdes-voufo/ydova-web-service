package com.ydova.ahub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LanguageLevel {
    private String language;
    private float level;
    private String description;
}
