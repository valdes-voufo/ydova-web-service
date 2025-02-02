package com.ydova.ahub.entity;


import jakarta.persistence.*;
import lombok.Data;

@Table(name = "email")
@Entity
@Data
public class Email {

    @Id @GeneratedValue
    private Long id;
    private String email;
    private String  category;
    private String place;
    private String groupName;

}