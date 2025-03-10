package com.ydova.ahub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Table(name = "application_job_table")
@Entity
@Data
@ToString
public class ApplicationJob {

        @Id
        @GeneratedValue
        private Long id;

        @NotNull
        private Long emailApplicationID;

        @NotNull @Email
        private  String sender;

        @NotNull
        private String receiver;

}
