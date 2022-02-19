package request;

import model.Event;
import model.Person;
import model.User;

/**
 * Load Request Class
 */
public class LoadRequest {
    /**
     * Array of user model objects
     */
    User[] users;

    /**
     * Array of person model objects
     */
    Person[] persons;

    /**
     * Array of event model objects
     */
    Event[] events;

    /**
     * Constructor with params for Users, Persons, Events
     * @param users
     * @param persons
     * @param events
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
