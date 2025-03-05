package com.ydova.ahub.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "email_table")
@Entity
@Data
public class Email {

    @Id
    @GeneratedValue
    private Long id;
    @jakarta.validation.constraints.Email
    private String email;
    private String  category;
    private String place;
    private String groupName;

}