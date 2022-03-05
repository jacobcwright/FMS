package utils;

import com.google.gson.*;
import model.Event;
import model.Person;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class DataGenerator {
    ArrayList<Person> people;
    ArrayList<Event> events;
    int generations;
    int year;
    static int GENERATION_GAP = 30;


    public DataGenerator(ArrayList<Person> people, ArrayList<Event> events, int generations) {
        this.people = people;
        this.events = events;
        this.generations = generations;
        // year at 2003, so each user is 18+
        year = 2003;
    }

    public DataGenerator(int generations) {
        this.generations = generations;
        // year at 2003, so each user is 18+
        year = 2003;
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


    // Parents must be born at least 13 years before their children.
    // Parents must be at least 13 years old when they are married.
    // Parents must not die before their child is born.
    // Women must not give birth when older than 50 years old.
    // Birth events must be the first event for a person chronologically.
    // Death events must be the last event for a person chronologically.
    // Nobody must die at an age older than 120 years old.
    // Each person in a married couple has their own marriage event.
    // Each event will have a unique event ID, but both marriage events must have matching years and locations.
    // Event locations may be randomly selected, or you may try to make them more realistic
    public void Generate(Person currentPerson){
        if(generations == 0){
            // create events for person
            // birth event

        }
        Event birth = GenerateBirth(currentPerson);
        // create Mother & Father of current person
        // Set Mother & Father IDs of current person
        // Create Events for current person
        // decrement generation & year
        generations--;
        year -= GENERATION_GAP; // 30 years for each generation
        // Call Generate on Mother
        // Call Generate on Father

    }


    private Event GenerateBirth(Person person){
        // get location for event
        Event event = GenerateLocation();
        // set eventID
        event.setEventID(String.valueOf(UUID.randomUUID()));
        //set remaining parameters
        event.setUsername(person.getAssociatedUsername());
        event.setPersonID(person.getPersonID());
        event.setEventType("Birth");
        event.setYear(GenerateYear());
        return event;
    }

    private void GenerateDeath(Person person){

    }

    private void GenerateMarriage(Person person){

    }

    private void GenerateEvent(Person person){

    }

    private void GeneratePerson(Person person){
        
    }

    private Event GenerateLocation(){
        try{
            // create random to get random int for event
            Random ran = new Random();
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("json/locations.json"));

            // get json into array of locations
            JsonArray locations = new JsonParser().parse(reader).getAsJsonObject().getAsJsonArray("data");

            // get random location & instantiate event & return it
            int ranInt = ran.nextInt(locations.size());
            JsonElement eventJson = locations.get(ranInt);
            Event event = gson.fromJson(eventJson, Event.class);
            assert(event != null);
            reader.close();
            return event;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int GenerateYear(){
        Random ran = new Random();
        year -= ran.nextInt(10);
        return year;
    }

}
