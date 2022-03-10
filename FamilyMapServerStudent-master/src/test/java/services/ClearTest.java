package services;

import dao.DataAccessException;
import dao.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.ClearRequest;
import request.LoadRequest;
import request.RegisterRequest;
import result.ClearResult;
import result.RegisterResult;
import service.ClearService;
import service.RegisterService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ClearTest {
    @BeforeEach
    public void setUp() throws DataAccessException {
        Database db = new Database();
        try{
            RegisterRequest req = new RegisterRequest("test1", "password", "gmail",
                    "jacob", "wright", "m");
            RegisterService service = new RegisterService();
            RegisterResult result = service.register(req);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void validClear(){
        try{
            ClearRequest req = new ClearRequest();
            ClearService clear = new ClearService();
            ClearResult result = clear.clear();
            assertTrue(result.getSuccess());
            assertEquals("Clear succeeded",result.getMessage());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
