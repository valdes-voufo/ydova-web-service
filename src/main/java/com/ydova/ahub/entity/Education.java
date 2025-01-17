package com.ydova.ahub.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EDUCATION")
public class Education extends TimeLineEntry {
}
