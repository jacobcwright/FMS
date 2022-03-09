package dao;

import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {
    private Database db;
    private Person person;
    private PersonDAO pDAO;

    @BeforeEach
    public void setUp() throws DataAccessException{
        // create database connection
        db = new Database();
        // create fake person
        person = new Person("JohnSmith1","jsmith","John","Smith","m","","","");
        // open connection
        Connection conn = db.getConnection();
        // create Person DAO
        pDAO = new PersonDAO(conn);
        // clear any left-over data in Person table
        pDAO.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        pDAO.insert(this.person);
        Person found = pDAO.getPerson(person.getPersonID());
        assertNotNull(found);
        assertEquals(this.person, found);
    }

    @Test
    public void insertFail() throws DataAccessException {
        // inserting same person twice -- throws error
        pDAO.insert(person);
        assertThrows(DataAccessException.class, () -> pDAO.insert(person));
    }

    @Test
    public void retrievePass() throws DataAccessException {
        // insert person
        pDAO.insert(person);

        // change personID to make new person
        person.setPersonID("JohnSmith2");

        // insert new person
        pDAO.insert(person);

        // get Person
        Person found = pDAO.getPerson(person.getPersonID());

        // Did we get something?
        assertNotNull(found);

        // Assert we got the right person
        assertEquals(this.person, found);
    }

    @Test
    public void retrieveNull() throws DataAccessException {
        // get Person
        Person found = pDAO.getPerson(person.getPersonID());
        // Did we get something?
        assertNull(found);
    }

    @Test
    public void clearTest() throws DataAccessException {
        // insert person
        pDAO.insert(person);

        // clear database
        pDAO.clear();

        //check if cleared
        Person found = pDAO.getPerson(person.getPersonID());

        assertNull(found);
    }

    @Test
    public void getPeopleTest() throws DataAccessException{
        ArrayList<Person> people = new ArrayList<>();
        people.add(person);
        pDAO.insert(person);
        person = new Person("Rachel","jsmith","Rachel","Smith","f","","","");
        pDAO.insert(person);
        people.add(person);

        ArrayList<Person> found = pDAO.getPeople("jsmith");
        assertNotNull(found);

        assertEquals(people.size(), found.size());
    }

    @Test
    public void getPeopleNoUserTest() throws DataAccessException{
        pDAO.insert(person);
        ArrayList<Person> found = pDAO.getPeople("failure!");

        assertTrue(found.isEmpty());
    }

    @Test
    public void deletePersonTest() throws DataAccessException{
        pDAO.insert(person);
        pDAO.deletePerson(person.getPersonID());
        Person found = pDAO.getPerson(person.getPersonID());

        assertNull(found);
    }

    @Test
    public void deletePersonFailTest() throws DataAccessException{
        assertNull(pDAO.getPerson("failure"));
        pDAO.deletePerson("failure");
        assertNull(pDAO.getPerson("failure"));
    }

    @Test
    public void deletePeopleTest() throws DataAccessException{
        pDAO.insert(person);
        assertEquals(person, pDAO.getPerson(person.getPersonID()));
        pDAO.deletePeopleFromUser(person.getAssociatedUsername());
        assertTrue(pDAO.getPeople(person.getAssociatedUsername()).isEmpty());
    }

    @Test
    public void deletePeopleFailTest() throws DataAccessException{
        assertTrue(pDAO.getPeople("failure").isEmpty());
        pDAO.deletePeopleFromUser("failure");
        assertTrue(pDAO.getPeople("failure").isEmpty());
    }

}
