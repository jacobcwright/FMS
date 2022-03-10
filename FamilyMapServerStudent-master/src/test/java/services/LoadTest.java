package services;

import com.google.gson.Gson;
import dao.DataAccessException;
import dao.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import result.LoadResult;
import service.ClearService;
import service.LoadService;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class LoadTest {
    @BeforeEach
    public void setUp() throws DataAccessException {
        Database db = new Database();
        try{
            new ClearService().clear();
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void loadValid() throws DataAccessException {
        Database db = new Database();
        try{
            // create Gson instance
            Gson gson = new Gson();
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("passoffFiles/LoadData.json"));
            LoadRequest req = gson.fromJson(reader, LoadRequest.class);
            LoadService service = new LoadService();
            LoadResult result = service.load(req);
            assertTrue(result.getSuccess());
            assertEquals("Successfully added 2 users, 11 persons, and 19 events to the database.", result.getMessage());
        } catch (DataAccessException | IOException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }
}
