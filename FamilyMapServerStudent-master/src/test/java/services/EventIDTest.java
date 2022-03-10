package services;

import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventIDRequest;
import request.EventRequest;
import request.RegisterRequest;
import result.EventIDResult;
import result.EventResult;
import result.RegisterResult;
import service.ClearService;
import service.EventIDService;
import service.EventService;
import service.RegisterService;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EventIDTest {
    String authtoken;
    @BeforeEach
    public void setUp() throws DataAccessException {
        Database db = new Database();
        try{
            new ClearService().clear();
            RegisterRequest req = new RegisterRequest("test1", "password", "gmail",
                    "jacob", "wright", "m");
            RegisterService service = new RegisterService();
            RegisterResult result = service.register(req);
            this.authtoken = result.getAuthtoken();
            Event event = new Event("1234", "test1", result.getPersonID(), 1, 2,
                    "Murica", "Dallas", "ate chicken", 2022);
            new EventDAO(db.getConnection()).insert(event);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void validEventID() throws DataAccessException {
        Database db = new Database();
        try{
            db.closeConnection(true);
            EventIDRequest req = new EventIDRequest("1234", this.authtoken);
            EventIDService getSingleEvent = new EventIDService();
            EventIDResult singleEvent = getSingleEvent.eventID(req);
            assertTrue(singleEvent.getSuccess());
            assertEquals("ate chicken", singleEvent.getEventType());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void invalidEventID() throws DataAccessException {
        Database db = new Database();
        try{
            db.closeConnection(true);
            EventIDRequest req = new EventIDRequest("12345", this.authtoken);
            EventIDService getSingleEvent = new EventIDService();
            EventIDResult singleEvent = getSingleEvent.eventID(req);
            assertFalse(singleEvent.getSuccess());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }
}
