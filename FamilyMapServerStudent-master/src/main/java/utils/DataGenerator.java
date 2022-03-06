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
        people = new ArrayList<Person>();
        events = new ArrayList<Event>();
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


    /**
     * Parents must be born at least 13 years before their children.
     * Parents must be at least 13 years old when they are married.
     * Parents must not die before their child is born.
     * Women must not give birth when older than 50 years old.
     * Birth events must be the first event for a person chronologically.
     * Death events must be the last event for a person chronologically.
     * Nobody must die at an age older than 120 years old.
     * Each person in a married couple has their own marriage event.
     * Each event will have a unique event ID, but both marriage events must have matching years and locations.
     * Event locations may be randomly selected, or you may try to make them more realistic
     * @param person current person object
     */
    public void Generate(Person person){
        if(generations == 0){
            GenerateBirth(person);
            GenerateDeath(person);
            return;
        }
        // Generate current Person
        GenerateBirth(person);
        GenerateDeath(person);

        // go to next generation
        year -= GENERATION_GAP;
        generations--;

        // Make Parents
        Person mother = GeneratePerson(person,"f");
        Person father = GeneratePerson(person, "m");

        // call Generate on Mother & Father
        Generate(mother);
        Generate(father);

        // marry them
        GenerateMarriage(mother, father);

        // set parent ID's
        person.setMotherID(mother.getPersonID());
        person.setFatherID(father.getPersonID());

        // add to family tree
        people.add(person);
        return;
    }

    /**
     * Generates Birth event for person
     * @param person
     */
    private void GenerateBirth(Person person){
        // get location for event
        Event event = GenerateLocation();
        // set eventID
        event.setEventID(String.valueOf(UUID.randomUUID()));
        //set remaining parameters
        event.setUsername(person.getAssociatedUsername());
        event.setPersonID(person.getPersonID());
        event.setEventType("Birth");
        event.setYear(GenerateBirthYear());
        person.setBirthYear(event.getYear());
        events.add(event);
    }

    /**
     * Generates death event for person
     * @param person
     */
    private void GenerateDeath(Person person){
        // get location for event
        Event event = GenerateLocation();
        // set eventID
        event.setEventID(String.valueOf(UUID.randomUUID()));
        //set remaining parameters
        event.setUsername(person.getAssociatedUsername());
        event.setPersonID(person.getPersonID());
        event.setEventType("Death");
        event.setYear(GenerateDeathYear(person.getBirthYear()));
        person.setDeathYear(event.getYear());
        events.add(event);
    }

    /**
     * Generates marriage events for wife & husband
     * @param wife Person Object
     * @param husband Person Object
     */
    private void GenerateMarriage(Person wife, Person husband){
        // set IDs
        husband.setSpouseID(wife.getPersonID());
        wife.setSpouseID(husband.getPersonID());

        // create marriage event for wife
        Event wifeMarriage = GenerateLocation();
        wifeMarriage.setEventID(UUID.randomUUID().toString());
        wifeMarriage.setUsername(wife.getAssociatedUsername());
        wifeMarriage.setPersonID(wife.getPersonID());
        wifeMarriage.setEventType("Marriage");
        wifeMarriage.setYear(GenerateMarriageYear(wife.getBirthYear(), wife.getDeathYear()));
        events.add(wifeMarriage);

        // create marriage event for husband
        Event husbandMarriage = new Event((float) wifeMarriage.getLatitude(), (float) wifeMarriage.getLongitude(),
                wifeMarriage.getCountry(), wifeMarriage.getCity());
        husbandMarriage.setEventID(String.valueOf(UUID.randomUUID()));
        husbandMarriage.setUsername(husband.getAssociatedUsername());
        husbandMarriage.setPersonID(husband.getPersonID());
        husbandMarriage.setEventType("Marriage");
        husbandMarriage.setYear(wifeMarriage.getYear());
        events.add(husbandMarriage);
    }

    private void GenerateEvent(Person person){

    }

    /**
     * Generate new person object using GeneratePersonName()
     * @param currentPerson
     * @param gender
     * @return
     */
    private Person GeneratePerson(Person currentPerson, String gender){
        // create Person with name based on gender
        Person newPerson = GeneratePersonName(gender);

        // set remaining params
        newPerson.setPersonID(UUID.randomUUID().toString());
        newPerson.setAssociatedUsername(currentPerson.getAssociatedUsername());

        return newPerson;
    }

    /**
     * Generates random name for person based on gender
     * @param gender
     * @return
     */
    private Person GeneratePersonName(String gender){
        Random ran = new Random();
        Gson gson = new Gson();
        Reader reader;
        try {
            // if male
            if(gender == "m"){
                // get male first names
                reader = Files.newBufferedReader(Paths.get("json/mnames.json"));
            }
            // female
            else{
                // get female first names
                reader = Files.newBufferedReader(Paths.get("json/fnames.json"));
            }
            // get json data
            JsonArray firstName = new JsonParser().parse(reader).getAsJsonObject().getAsJsonArray("data");

            // get last name data
            reader = Files.newBufferedReader(Paths.get("json/snames.json"));
            JsonArray lastName = new JsonParser().parse(reader).getAsJsonObject().getAsJsonArray("data");

            // turn random first name into string
            JsonElement first = firstName.get(ran.nextInt(firstName.size()-1));
            String randomName = gson.fromJson(first, String.class);

            // turn random last name into string
            JsonElement last = lastName.get(ran.nextInt(lastName.size()-1));
            String randomSurname = gson.fromJson(last, String.class);

            // return first & last name
            return new Person(randomName, randomSurname, gender);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Generates random location from json files provided
     * @return Event
     */
    private Event GenerateLocation(){
        try{
            // create random to get random int for event
            Random ran = new Random();
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("json/locations.json"));

            // get json into array of locations
            JsonArray locations = new JsonParser().parse(reader).getAsJsonObject().getAsJsonArray("data");

            // get random location & instantiate event & return it
            int ranInt = ran.nextInt(locations.size()-1);
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

    /**
     * Generates random birth year
     * @return birthYear
     */
    private int GenerateBirthYear(){
        Random ran = new Random();
        int birthYear = year - ran.nextInt(10);
        return birthYear;
    }

    /**
     * Generates random death year
     * @return deathYear
     */
    private int GenerateDeathYear(int birth){
        Random ran = new Random();
        int deathYear = birth + ran.nextInt(80) + 13;
        if(deathYear >= 2022){
            deathYear = 2022;
        }
        return deathYear;
    }

    /**
     * Generates random marriage year
     * @return marriageYear
     */
    private int GenerateMarriageYear(int birth, int death){
        Random ran = new Random();
        int marriage = birth + ran.nextInt(10) + 13;
        if(marriage >= 2022){
            marriage = 2022;
        }
        if(marriage > death){
            marriage = death - 1;
        }
        return marriage;
    }

}
