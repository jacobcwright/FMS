package services;

import dao.AuthtokenDAO;
import dao.DataAccessException;
import dao.Database;
import model.Authtoken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import result.RegisterResult;
import service.AuthtokenChecker;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;


public class AuthtokenCheckerTest {
    @BeforeEach
    public void setUp() throws DataAccessException {
        Database db = new Database();
        try{
            new AuthtokenDAO(db.getConnection()).clear();
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void validAuthtokenTest() throws DataAccessException {
        Database db = new Database();
        RegisterRequest req = new RegisterRequest("test1", "password", "gmail",
                "jacob", "wright", "m");
        RegisterService service = new RegisterService();
        RegisterResult result = service.register(req);
        String auth = result.getAuthtoken();
        assertEquals("test1", new AuthtokenChecker().getUser(auth, db.getConnection()).getUsername());
    }

    @Test
    public void invalidAuthtokenTest() throws DataAccessException {
        Database db = new Database();
        RegisterRequest req = new RegisterRequest("test1", "password", "gmail",
                "jacob", "wright", "m");
        RegisterService service = new RegisterService();
        RegisterResult result = service.register(req);
        String wrongAuth = result.getAuthtoken() + "wrong";
        Authtoken authtoken = new AuthtokenChecker().getUser(wrongAuth, db.getConnection());
        assertNull(authtoken);
    }

}
