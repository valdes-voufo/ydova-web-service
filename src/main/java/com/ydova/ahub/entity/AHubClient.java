package com.ydova.ahub.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "ah_client")
@Entity
@Data
public class AHubClient {

    @Id @GeneratedValue
    private Long id;
    private String lastname;
    private String firstname;
    private String street;
    private String city;
    private String country;
    private String email;
    private String phone;
    private String birthday;
    private String birthplace;
    private int children;
    private String civilState;
    private String nationality;
    private String status;

}
