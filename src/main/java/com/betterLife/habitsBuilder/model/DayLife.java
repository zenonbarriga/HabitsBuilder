package com.betterLife.habitsBuilder.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class DayLife {

    @Id 
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Column( nullable = false, unique = true )
    private LocalDate date;

    @ManyToMany
    private List<Task> approvedTasks = new ArrayList<>();

    @ManyToMany
    @JoinColumn(name = "task_id")
    private List<Task> tasks = new ArrayList<>();

    public double getScore(){
        return ( approvedTasks.size() * 100 ) / ( approvedTasks.size() + tasks.size() );
    }

}
