package com.ydova.ahub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ah_timeline_entry")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DiscriminatorColumn(name = "note_type", discriminatorType = DiscriminatorType.STRING)
public class TimeLineEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String institution;
    private String startMonth;
    private String endMonth;
    private String startYear;
    private String endYear;
    private String place;
    private String task;

    @ElementCollection
    @CollectionTable(name = "timeline_task_descriptions", joinColumns = @JoinColumn(name = "timeline_entry_id"))
    @Column(name = "description")
    private Set<String> taskDescription = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "client_id")
    private AHubClient client;
}
