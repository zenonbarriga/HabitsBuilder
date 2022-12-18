package com.betterLife.habitsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Functional {
    
    public static void main(String[] args) {

        Consumer<String> print = name -> System.out.println(name + " is my name");
        List<String> list =  Arrays.asList( "Juan", "Pedro", "Santiago", "Judas", "Nathanael", "Pablo");

        list.stream()
            .forEach(print);

        BiConsumer<String, Integer> showDetalails =  ( name, age ) -> System.out.println( "name is: "+name+" age is "+age);
        showDetalails.accept( "Ernesto Orozco", 39);

        Predicate<String> startsWithJ = (x) -> x.startsWith( "J" );
        Predicate<String> startsWithP = (x) -> x.startsWith( "P" );

        list.stream()
            .filter( startsWithJ.or(startsWithP) )
            .forEach( print );
        
        showDetalails
            .andThen(showDetalails)
                .andThen(showDetalails)
                    .accept("juan perez", 20);
        

        

    }

}
