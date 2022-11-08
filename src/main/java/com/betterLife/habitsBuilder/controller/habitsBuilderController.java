package com.betterLife.habitsBuilder.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betterLife.habitsBuilder.model.DayLife;
import com.betterLife.habitsBuilder.model.Task;
import com.betterLife.habitsBuilder.service.HabitsBuilderService;
import com.betterLife.habitsBuilder.service.TaskFinder;

@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class habitsBuilderController {

    @Autowired
    HabitsBuilderService habitsBuilderService;

    @Autowired
    DayLifeCreator dayLifeCreator;

    @Autowired
    TaskFinder taskFinder;

    @GetMapping("/sayHi")
    public String sayHi(){
        return "Hi Habits Builder!";
    }

    @GetMapping( "/task/id/{id}" )
    public ResponseEntity < Task > getTaskById( @PathVariable (value = "id") Long id ){
        
        Task task = habitsBuilderService.getTaskById( id ).map( tsk -> {
            return tsk;
        }).orElse( null );
         
        return task == null ? new ResponseEntity<>( null, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>( task , HttpStatus.OK);      
    }

    @GetMapping( "/task/getall")
    public ArrayList < Task > getAllTasks(){
        return habitsBuilderService.getAllTask();
    }

    @GetMapping( "/task/date/{date}")
    public ResponseEntity <ArrayList<Task>> getTasksByDate( 
        @PathVariable ( value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date ){
        
        return new ResponseEntity<ArrayList<Task>>( habitsBuilderService.getTasksByDate( date ) , HttpStatus.OK );
        
    }

    @PostMapping( "/task/create" )
    public ResponseEntity < Task > createTask (@RequestBody Task task){
        
        dayLifeCreator.recalculateDayLifes(habitsBuilderService.saveTask(task));

        return new ResponseEntity<>(task , HttpStatus.CREATED);
    }

    @DeleteMapping( "/task/delete/{taskId}" )
    public boolean deleteTaskById( @PathVariable( value = "taskId" ) Long taskId ){
        System.out.println( "name " + habitsBuilderService.getTaskById( taskId ).get().getName() );
        return habitsBuilderService.deleteTaskById( taskId );
    }

    @PutMapping( "/daylife/approve/{dayLifeId}/{taskId}" )
    public ResponseEntity < DayLife > approveTask( @PathVariable( value = "dayLifeId" ) Long dayLifeId, 
                                                    @PathVariable( value = "taskId" ) Long taskId ){
        DayLife dayLife = habitsBuilderService.getDayLifeById( dayLifeId ).get();
        Task task = habitsBuilderService.getTaskById( taskId ).get();
        
        if( taskFinder.findTaskWithinDayLife(dayLife, taskId)){
            dayLife.getApprovedTasks().add(task);
            dayLife.getTasks().remove(task);
        }

        return new ResponseEntity(habitsBuilderService.saveDayLife(dayLife), HttpStatus.OK);
    }

    @PutMapping( "/daylife/fail/{dayLifeId}/{taskId}" )
    public ResponseEntity < DayLife > removeTask( @PathVariable( value = "dayLifeId" ) Long dayLifeId, 
                                                    @PathVariable( value = "taskId" ) Long taskId ){
        DayLife dayLife = habitsBuilderService.getDayLifeById( dayLifeId ).get();
        Task task = habitsBuilderService.getTaskById( taskId ).get();
        
        if( taskFinder.findTaskWithinDayLife(dayLife, taskId)){
            dayLife.getApprovedTasks().remove(task);
            dayLife.getTasks().add(task);
            
        }

        return new ResponseEntity(habitsBuilderService.saveDayLife(dayLife), HttpStatus.OK);
    }

    @GetMapping("/daylife/id/{id}")
    public ResponseEntity < DayLife > getDaylifesById( @PathVariable (value = "id") Long id ){
        DayLife dayLife = habitsBuilderService.getDayLifeById( id ).map( dl -> {
            return dl;
        }).orElse( null );
         
        return dayLife == null ? new ResponseEntity<>( null, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>( dayLife , HttpStatus.OK);
    }

    @GetMapping( "/daylife/getall")
    public ArrayList < DayLife > getAllDayLifes(){
        return habitsBuilderService.getAllDayLife();
    }

    @GetMapping( "/daylife/id/{dayLifeId}/getscore" )
    public double getDayLifeScore( @PathVariable( value = "dayLifeId") Long dayLifeId ){
        DayLife dayLife = habitsBuilderService.getDayLifeById( dayLifeId ).get();

        return dayLife.getScore();
    }

    
    
}
