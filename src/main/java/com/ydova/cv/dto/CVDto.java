package com.ydova.cv.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CVDto {
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
    private String birthCountry;
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
