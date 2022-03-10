package services;

import dao.DataAccessException;
import dao.Database;
import dao.UserDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {
    @BeforeEach
    public void setup() throws DataAccessException {
        Database db = new Database();
        try {
            new UserDAO(db.getConnection()).clear();
            db.closeConnection(true);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void registerTest() throws DataAccessException {
        Database db = new Database();
        try {
            RegisterRequest req = new RegisterRequest("test1", "password", "gmail",
                    "jacob", "wright", "m");
            RegisterService service = new RegisterService();
            RegisterResult result = service.register(req);
            assertEquals("test1", result.getUsername());
            assertTrue(result.getSuccess());
            assertFalse(result.getAuthtoken().isEmpty());
            assertFalse(result.getPersonID().isEmpty());

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void reregisterTest() throws DataAccessException {
        Database db = new Database();
        try {
            RegisterRequest req = new RegisterRequest("test1", "password", "gmail",
                    "jacob", "wright", "m");
            RegisterService service = new RegisterService();
            RegisterResult result = service.register(req);
            assertEquals("test1", result.getUsername());
            assertTrue(result.getSuccess());
            assertFalse(result.getAuthtoken().isEmpty());
            assertFalse(result.getPersonID().isEmpty());
            // run again
            result = service.register(req);
            assertFalse(result.getSuccess());
            assertTrue(result.getMessage().contains("Error"));

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }



}
