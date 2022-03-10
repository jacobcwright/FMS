package services;

import dao.*;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.FillRequest;
import request.RegisterRequest;
import result.FillResult;
import service.FillService;
import service.RegisterService;

import java.io.IOException;
import java.sql.Connection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FillTest {
    @BeforeEach
    public void setup() throws DataAccessException {
        Database db = new Database();
        try {
            new UserDAO(db.getConnection()).clear();
            new PersonDAO(db.getConnection()).clear();
            new EventDAO(db.getConnection()).clear();
            db.closeConnection(true);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void fillTest() throws DataAccessException {
        Database db = new Database();
        try{
            RegisterRequest registerReq = new RegisterRequest("test1", "password", "gmail",
                    "jacob", "wright", "m");
            RegisterService registerService = new RegisterService();
            registerService.register(registerReq);
            db.closeConnection(true);
            FillRequest req = new FillRequest("test1", 4);
            FillService service = new FillService();
            FillResult result = service.fill(req);
            String expected = "Successfully added 31 persons and 92 events to the database.";
            assertEquals(expected, result.getMessage());
            assertEquals(31, new PersonDAO(db.getConnection()).getPeople("test1").size());
            assertEquals(92, new EventDAO(db.getConnection()).getEvents("test1").size());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void invalidFillTest() throws DataAccessException {
        FillRequest req = new FillRequest("bad_User", 4);
        FillService service = new FillService();
        FillResult result = service.fill(req);
        assertFalse(result.getSuccess());
        assertTrue(result.getMessage().contains("Error"));
    }
}
