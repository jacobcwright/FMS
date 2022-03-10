package services;

import dao.DataAccessException;
import dao.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventRequest;
import request.FillRequest;
import request.PersonRequest;
import request.RegisterRequest;
import result.EventResult;
import result.PersonResult;
import result.RegisterResult;
import service.*;

import static org.junit.jupiter.api.Assertions.*;


public class PersonsTest {
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
    public void validPeople() throws DataAccessException {
        Database db = new Database();
        try{
            PersonRequest req = new PersonRequest(this.authtoken);
            PersonService service = new PersonService();
            PersonResult result = service.person(req);
            assertTrue(result.getSuccess());
            assertEquals(31, result.getPersons().size());
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
            PersonRequest req = new PersonRequest(this.authtoken);
            PersonService service = new PersonService();
            PersonResult result = service.person(req);
            assertFalse(result.getSuccess());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }
}
