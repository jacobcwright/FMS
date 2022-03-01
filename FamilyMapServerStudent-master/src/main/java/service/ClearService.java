package service;

import dao.*;
import model.Authtoken;
import result.ClearResult;
import result.Response;

/**
 * Clear Service class
 * Deletes ALL data from the database, including user, authtoken, person, and event data
 */
public class ClearService {
    /**
     * Deletes ALL data from the database, including user, authtoken, person, and event data
     * @return
     */
    public ClearResult clear() throws DataAccessException {
        Database db = new Database();

        try {
            db.openConnection();
            new AuthtokenDAO(db.getConnection()).clear();
            new EventDAO(db.getConnection()).clear();
            new PersonDAO(db.getConnection()).clear();
            new UserDAO(db.getConnection()).clear();
            ClearResult result = new ClearResult(true, "Clear succeeded");
            db.closeConnection(true);
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            db.closeConnection(false);
            Response result = new Response(false, "Error");
        }
        return null;
    }
}
