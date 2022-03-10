package services;

import dao.AuthtokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.UserDAO;
import model.User;
import org.junit.jupiter.api.*;
import request.LoginRequest;
import result.LoginResult;
import service.LoginService;


import javax.xml.crypto.Data;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    // take request obj and pass it to service
    // compare result objs

    @BeforeEach
    public void setUp() throws DataAccessException {
        // new Database connection
        Database db = new Database();
        // clear User table
        try {
            new UserDAO(db.getConnection()).clear();
            new AuthtokenDAO(db.getConnection()).clear();
            User user = new User("Username123", "StupidPassword", "fake@gmail.com", "Jacob",
                    "Wright","m", "Jacob_Wright30");
            new UserDAO(db.getConnection()).insert(user);
            db.closeConnection(true);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
        }

    }

    @Test
    public void loginTest() throws DataAccessException {
        LoginRequest req = new LoginRequest("Username123", "StupidPassword");
        LoginService service = new LoginService();
        LoginResult result = service.login(req);
        assertTrue(result.getSuccess());
        assertNotNull(result.getAuthtoken());
    }

    @Test
    public void invalidPassword() throws DataAccessException {
        LoginRequest req = new LoginRequest("Username123", "Wrong!");
        LoginService service = new LoginService();
        LoginResult result = service.login(req);
        assertTrue(!result.getSuccess());
    }

    @Test
    public void invalidUsername() throws DataAccessException {
        LoginRequest req = new LoginRequest("Username123456", "StupidPassword");
        LoginService service = new LoginService();
        LoginResult result = service.login(req);
        assertTrue(!result.getSuccess());
    }
}
