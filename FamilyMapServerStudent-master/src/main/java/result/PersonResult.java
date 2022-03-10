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
    ArrayList<Person> data;

    /**
     * constructor based on success
     * @param persons
     */
    public PersonResult(ArrayList<Person> persons) {
        this.data = persons;
        this.success = true;
    }

    /**
     * constructor for failed
     * @param b
     * @param s
     */
    public PersonResult(boolean b, String s) {
        success = b;
        message = s;
    }

    public ArrayList<Person> getPersons() {
        return data;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.data = persons;
    }
}
