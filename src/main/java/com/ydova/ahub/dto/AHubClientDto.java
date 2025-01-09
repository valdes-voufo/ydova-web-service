package com.ydova.ahub.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AHubClientDto {
    @Id
    @GeneratedValue
    private Long id;
    private String lastname;
    private String firstname;
    private List<LanguageLevel> languageLevels;
    private List<TimeLineEntryDto> experience;
    private List<TimeLineEntryDto> education;
    private String street;
    private String city;
    private String country;
    private String email;
    private String phone;
    private String birthday;
    private String birthplace;
    private int children;
    private String civilState;
    private String nationality;;
    private String status;


    public String getFullName(){
        return getFirstname() + " " + getLastname();
    }

    public String geStreetAndCity() {
        return getStreet() + ", " + getCity();
    }
}
