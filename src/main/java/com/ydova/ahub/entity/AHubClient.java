package com.ydova.ahub.entity;



import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name = "ah_client")
@Entity
@Data
@ToString
public class AHubClient {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client")
    private List<Experience> experience = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_id")
    private List<Education> education = new ArrayList<>();



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_id")
    private List<Application> applications= new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_id")
    private Set<LanguageLevel> languageLevels;


}
