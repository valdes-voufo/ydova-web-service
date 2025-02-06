package com.ydova.ahub.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Table(name = "application_table")
@Entity
@Data
@ToString
public class Application {

        @Id
        @GeneratedValue
        private Long id;
        private String receiver;
        private String sender;
        private  boolean success;


}
