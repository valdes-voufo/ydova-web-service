package com.ydova.ahub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchoolDto {
    private Long id;

    @NotEmpty @Email
    private String email;

    @NotEmpty
    private String  place;
}