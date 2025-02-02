package com.ydova.ahub.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Table(name = "client_table")
@Entity
@Data
@ToString
public class Client {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private  String password;
    private String lastname;
    private String firstname;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    @NotNull
    private AppUser  creator;
}
