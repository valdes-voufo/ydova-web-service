package com.ydova.ahub;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


@Setter
@Getter
@Builder
@ToString
public class LoginDto {
    private String email;
    private  String password;
}