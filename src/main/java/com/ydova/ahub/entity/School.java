package com.ydova.ahub.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "school_or_company")
@Entity
@Data
public class School {

    @Id @GeneratedValue
    private Long id;
    private String email;
    private String  place;

}