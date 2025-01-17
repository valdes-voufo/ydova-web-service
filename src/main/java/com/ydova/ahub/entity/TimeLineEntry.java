package com.ydova.ahub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public class TimeLineEntry {

    @Id
    @GeneratedValue
    protected Long id;

    @ElementCollection
    @CollectionTable(name = "timeline_task_descriptions", joinColumns = @JoinColumn(name = "timeline_entry_id"))
    @Column(name = "description")
    protected Set<String> taskDescription = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "client_id")
    protected AHubClient client;

    protected String institution;
    protected String startMonth;
    protected String endMonth;
    protected String startYear;
    protected String endYear;
    protected String place;
    protected String task;

    public Set<String> getDescription(){
        return  new HashSet<>();
    }


}
