package dao;

import model.Authtoken;
import model.Event;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class AuthtokenTest {
    private Database db;
    private Authtoken token;
    private AuthtokenDAO aDAO;

    @BeforeEach
    public void setUp() throws DataAccessException{
        // create database connection
        db = new Database();
        // random authtoken
        token = new Authtoken("abc123", "JoeSmith");
        // make connection
        Connection conn = db.getConnection();
        // make dao object
        aDAO = new AuthtokenDAO(conn);
        // clear just in case
        aDAO.clear();
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
        // Start by inserting an event into the database.
        aDAO.add(token);
        // Let's use a find method to get the event that we just put in back out.
        Authtoken compareTest = aDAO.get(token.getAuthtoken());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(token, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        aDAO.add(token);
        assertThrows(DataAccessException.class, () -> aDAO.add(token));
    }

    @Test
    public void retrievePass() throws DataAccessException {
        // insert person
        aDAO.add(token);

        // change personID to make new person
        token.setAuthtoken("new123");

        // insert new person
        aDAO.add(token);

        // get Person
        Authtoken found = aDAO.get(token.getAuthtoken());

        // Did we get something?
        assertNotNull(found);

        // Assert we got the right token
        assertEquals(this.token, found);
    }

    @Test
    public void retrieveNull() throws DataAccessException {
        // get authtoken
        Authtoken found = aDAO.get(token.getAuthtoken());
        // Did we get something?
        assertNull(found);
    }

    @Test
    public void clearTest() throws DataAccessException {
        // insert token
        aDAO.add(token);

        // clear database
        aDAO.clear();

        //check if cleared
        Authtoken found = aDAO.get(token.getAuthtoken());

        assertNull(found);
    }

}
