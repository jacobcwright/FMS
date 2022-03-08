package result;

import model.Person;

import java.util.ArrayList;

/**
 * Person result class
 */
public class PersonResult extends Response{
    /**
     * Array of person objects
     */
    ArrayList<Person> persons;

    /**
     * constructor based on success
     * @param persons
     */
    public PersonResult(ArrayList<Person> persons) {
        this.persons = persons;
        this.success = true;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
}
