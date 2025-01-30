package com.ydova.ahub.entity;



import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


@Table(name = "app_user")
@Entity
@Data
@ToString
public class AppUser {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String email;
    private  String password;
    private String lastname;
    private String firstname;
    private String  key;

}
