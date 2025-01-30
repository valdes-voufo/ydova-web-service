package com.ydova.ahub.entity;



import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;


@Table(name = "user_table")
@Entity
@Data
@ToString
public class AppUser {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid")
    private Long id;
    private String email;
    private  String password;
    private String lastname;
    private String firstname;
    private String  key;

}
