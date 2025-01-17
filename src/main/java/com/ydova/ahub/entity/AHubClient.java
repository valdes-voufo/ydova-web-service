package com.ydova.ahub.entity;



import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeLineEntry> experience = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeLineEntry> highSchool = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeLineEntry> primarySchool = new ArrayList<>();



    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications= new ArrayList<>();


}
