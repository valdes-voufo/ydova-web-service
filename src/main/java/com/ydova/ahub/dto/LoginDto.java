package com.ydova.ahub.dto;

import lombok.*;


@Setter
@Getter
@Builder
@ToString
public class LoginDto {
    private String email;
    private  String password;
}