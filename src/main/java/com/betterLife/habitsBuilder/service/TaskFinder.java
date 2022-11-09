package com.betterLife.habitsBuilder.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betterLife.habitsBuilder.controller.habitsBuilderController;
import com.betterLife.habitsBuilder.model.DayLife;
import com.betterLife.habitsBuilder.model.Task;



@Service
public class TaskFinder {


    public TaskFinder(){

    }

    @Autowired
    OrphanDayLife orphanDayLife;

    @Autowired
    TaskFinderByDate taskFinderByDate;

    

    //CHECK IF TASK IS CONTAINED IN A DAYLIFE
    public boolean findTaskWithinDayLife( DayLife dayLife, Long taskId ){

        boolean isOnTasks = dayLife.getTasks().stream()
        .anyMatch( task -> task.getId() == taskId);

        boolean isOnApprovedTasks = dayLife.getApprovedTasks().stream()
        .anyMatch( task -> task.getId() == taskId );

        return isOnTasks || isOnApprovedTasks;
            
    }

    //DELETE TASK FROM DAYLIFE
    public boolean deleteTaskFromDayLife( DayLife daylife, Task task ){

        boolean removeFromTasks = daylife.getTasks().remove( task );
        boolean removeFromApprovedTasks = daylife.getApprovedTasks().remove( task );
        boolean removeDayLife = task.getDayLifes().remove( daylife );
        boolean removeApprovedDayLife = task.getApprovedDayLifes().remove( daylife );

        return ( removeFromTasks || removeFromApprovedTasks)
         && ( removeDayLife || removeApprovedDayLife );
    }

    public boolean deleteAllDayLifesFromTask( Task task ){

        ArrayList < DayLife > dayLifes = (ArrayList<DayLife>) task.getDayLifes().stream()
                                .collect(Collectors.toList());
        
        ArrayList < DayLife > approvedDayLifes = (ArrayList<DayLife>) task.getApprovedDayLifes().stream()
                                .collect(Collectors.toList());

        boolean deletedDayLifes = dayLifes.stream()
            .allMatch( dayLife -> deleteTaskFromDayLife( dayLife, task));


        boolean deletedApprovedDayLifes = approvedDayLifes.stream()
            .allMatch( dayLife -> deleteTaskFromDayLife( dayLife, task));

        ArrayList < DayLife > orphanDayLifes = new ArrayList<>();

        //Colect orphan daylifes
        dayLifes.stream().filter( dayLife -> orphanDayLife.isOrphanDayLife(dayLife) )
            .forEach( dayLife -> orphanDayLifes.add(dayLife));
        
        approvedDayLifes.stream().filter( dayLife -> orphanDayLife.isOrphanDayLife(dayLife) )
            .forEach( dayLife -> orphanDayLifes.add(dayLife));

        orphanDayLifes.stream().forEach( dayLife -> orphanDayLife.deleteOrphanDayLife(dayLife) );
        

        return deletedDayLifes && deletedApprovedDayLifes;
    }

    

    
    
}

    
