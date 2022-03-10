package services;

import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventIDRequest;
import request.PersonIDRequest;
import request.RegisterRequest;
import result.EventIDResult;
import result.PersonIDResult;
import result.RegisterResult;
import service.ClearService;
import service.EventIDService;
import service.PersonIDService;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;


public class PersonIDTest {
    String authtoken;
    String personID;
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
            this.personID = result.getPersonID();
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void validPersonID() throws DataAccessException {
        Database db = new Database();
        try{
            db.closeConnection(true);
            PersonIDRequest req = new PersonIDRequest(this.personID, this.authtoken);
            PersonIDService getPerson = new PersonIDService();
            PersonIDResult person = getPerson.personID(req);
            assertTrue(person.getSuccess());
            assertEquals(this.personID, person.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void invalidPersonID() throws DataAccessException {
        Database db = new Database();
        try{
            db.closeConnection(true);
            PersonIDRequest req = new PersonIDRequest("wrong", this.authtoken);
            PersonIDService getPerson = new PersonIDService();
            PersonIDResult person = getPerson.personID(req);
            assertFalse(person.getSuccess());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }
}
