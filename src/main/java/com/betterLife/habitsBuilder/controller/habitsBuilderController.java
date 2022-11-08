package com.betterLife.habitsBuilder.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betterLife.habitsBuilder.model.DayLife;
import com.betterLife.habitsBuilder.model.Task;
import com.betterLife.habitsBuilder.service.HabitsBuilderService;

@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class habitsBuilderController {

    @Autowired
    HabitsBuilderService habitsBuilderService;

    @Autowired
    DayLifeCreator dayLifeCreator;

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

    @PostMapping( "/task/create" )
    public ResponseEntity < Task > createTask (@RequestBody Task task){
        
        dayLifeCreator.recalculateDayLifes(habitsBuilderService.saveTask(task));

        return new ResponseEntity<>(task , HttpStatus.CREATED);
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

    
    
}
