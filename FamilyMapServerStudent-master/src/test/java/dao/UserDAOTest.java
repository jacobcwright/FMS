package dao;

import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;



public class UserDAOTest {
    private Database db;
    private User user;
    private UserDAO uDAO;

    @BeforeEach
    public void setUp() throws DataAccessException{
        // create database connection
        db = new Database();
        // create fake person
        user = new User("JohnSmith1","Jsmith123","john@gmail.com","John","Smith", "m","js1");
        // open connection
        Connection conn = db.getConnection();
        // create Person DAO
        uDAO = new UserDAO(conn);
        // clear any left-over data in Person table
        uDAO.clear();
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
        uDAO.insert(this.user);
        User found = uDAO.getUser(user.getUsername());
        assertNotNull(found);
        assertEquals(this.user, found);
    }

    @Test
    public void insertFail() throws DataAccessException {
        // inserting same person twice -- throws error
        uDAO.insert(user);
        assertThrows(DataAccessException.class, () -> uDAO.insert(user));
    }

    @Test
    public void retrievePass() throws DataAccessException {
        // insert person
        uDAO.insert(user);

        // change personID to make new person
        user.setUsername("JohnSmith2");

        // insert new person
        uDAO.insert(user);

        // get Person
        User found = uDAO.getUser(user.getUsername());

        // Did we get something?
        assertNotNull(found);

        // Assert we got the right person
        assertEquals(this.user, found);
    }

    @Test
    public void retrieveNull() throws DataAccessException {
        // get Person
        User found = uDAO.getUser(user.getUsername());
        // Did we get something?
        assertNull(found);
    }

    @Test
    public void clearTest() throws DataAccessException {
        // insert person
        uDAO.insert(user);

        // clear database
        uDAO.clear();

        //check if cleared
        User found = uDAO.getUser(user.getUsername());

        assertNull(found);
    }

}
