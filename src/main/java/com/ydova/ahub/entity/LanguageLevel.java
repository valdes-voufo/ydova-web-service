package com.ydova.ahub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LanguageLevel {
    private String language;
    private float level;
    private String description;
    @Id @GeneratedValue
    private Long id;


    @ManyToOne
    @JoinColumn(name = "client_id")
    private AHubClient client;


}
