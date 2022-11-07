package com.betterLife.habitsBuilder.controller;

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

import com.betterLife.habitsBuilder.model.Task;
import com.betterLife.habitsBuilder.service.HabitsBuilderService;

@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class habitsBuilderController {

    @Autowired
    HabitsBuilderService habitsBuilderService;

    @GetMapping("/sayHi")
    public String sayHi(){
        return "Hi Habits Builder!";
    }

    @GetMapping( "/task/id/{id}" )
    public ResponseEntity < Task > getTaskById( @PathVariable (value = "id") Long id){
        
        Task task = habitsBuilderService.getTaskById( id ).map( tsk -> {
            return tsk;
        }).orElse( null );
         
        return task == null ? new ResponseEntity<>( null, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>( task , HttpStatus.OK);

        
    }

    @PostMapping( "/task/create" )
    public ResponseEntity < Task > createTask (@RequestBody Task task){
        habitsBuilderService.saveTask(task);

        //TODO recalculate the Daylifes here

        return new ResponseEntity<>(task , HttpStatus.CREATED);
    }
    
}
