package dao;

import model.Authtoken;
import model.Person;
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

    }

    @Test
    public void insertFail() throws DataAccessException {

    }

    @Test
    public void retrievePass() throws DataAccessException {

    }

    @Test
    public void retrieveNull() throws DataAccessException {

    }

    @Test
    public void clearTest() throws DataAccessException {
        // insert person
        aDAO.add(token.getAuthtoken());

        // clear database
        aDAO.clear();

        //check if cleared
        Authtoken found = aDAO.get(token.getAuthtoken());

        assertNull(found);
    }

}
