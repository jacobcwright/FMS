package services;

import dao.DataAccessException;
import dao.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventRequest;
import request.FillRequest;
import request.RegisterRequest;
import result.EventResult;
import result.RegisterResult;
import service.ClearService;
import service.EventService;
import service.FillService;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;


public class EventsTest {
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
            new FillService().fill(new FillRequest("test1", 4));
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void validEvents() throws DataAccessException {
        Database db = new Database();
        try{
            EventRequest req = new EventRequest(this.authtoken);
            EventService service = new EventService();
            EventResult result = service.event(req);
            assertTrue(result.getSuccess());
            assertEquals(92, result.getEvents().size());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }


    @Test
    public void noAuth() throws DataAccessException {
        Database db = new Database();
        try{
            new ClearService().clear();
            db.closeConnection(true);
            EventRequest req = new EventRequest(this.authtoken);
            EventService service = new EventService();
            EventResult result = service.event(req);
            assertFalse(result.getSuccess());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }
}
