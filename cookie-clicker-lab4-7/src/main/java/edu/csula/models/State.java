package edu.csula.models;

import java.util.Collection;

public class State {
    Collection<Event> events;
    Collection<Generator> generators;
    int counter;

    public State(Collection<Event> events, Collection<Generator> generators, int counter){
        this.events = events;
        this.generators = generators;
        this.counter = counter;
    }

    public int getCounter(){
        return this.counter;
    }

    public void setCounter(int counter){
        this.counter = counter;
    }



}
