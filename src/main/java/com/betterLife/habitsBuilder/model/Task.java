package com.betterLife.habitsBuilder.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table ( name = "tasks" )
public class Task {

    @Id 
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Column( nullable = false, unique = true )
    private String name;

    @Column
    private String instructions;

    @Column( nullable = false )
    private String goal;

    @Column( nullable = false )
    private LocalDate initialDate;

    @Column( nullable = false )
    private LocalDate endDate;

    @Column
    private LocalTime initialTime;

    @Column
    private LocalTime endTime;

    @Column
    private boolean monday = false;

    @Column
    private boolean tuesday = false;

    @Column
    private boolean wednesday = false;

    @Column
    private boolean thursday = false;

    @Column
    private boolean friday = false;

    @Column
    private boolean saturday = false;

    @Column
    private boolean sunday = false;
 
    @ManyToAny
    @JsonIgnore
    private List<DayLife> dayLifes;

}