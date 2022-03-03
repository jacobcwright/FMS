package utils;

import model.Event;
import model.Person;

import java.util.ArrayList;

public class DataGenerator {
    ArrayList<Person> people;
    ArrayList<Event> events;
    int generations;


    public DataGenerator(ArrayList<Person> people, ArrayList<Event> events, int generations) {
        this.people = people;
        this.events = events;
        this.generations = generations;
    }

    public DataGenerator(int generations) {
        this.generations = generations;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public void Generate(Person currentPerson){
        if(generations == 0){
            // create events for person
        }
        // create Mother & Father of current person
        // Set Mother & Father IDs of current person
        // Create Events for current person
        // decrement generation
        generations--;
        // Call Generate on Mother
        // Call Generate on Father

    }
}
