package com.ydova.ahub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@ToString
public class TimeLineEntry {

    @Id
    @GeneratedValue
    protected Long id;

    @ElementCollection
    @CollectionTable(name = "timeline_task_descriptions", joinColumns = @JoinColumn(name = "timeline_entry_id"))
    @Column(name = "description")
    protected Set<String> taskDescription = new HashSet<>();

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
