package service;

import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import model.Authtoken;
import model.Event;
import model.User;
import request.EventIDRequest;
import result.EventIDResult;
import result.Response;

import java.io.IOException;

/**
 * EventID Service Class
 */
public class EventIDService extends AuthtokenChecker{
    /**
     * Returns the single Event object with the specified ID (if the event is associated with the current user).
     * The current user is determined by the provided authtoken.
     * @param e
     * @return
     */
    public EventIDResult eventID(EventIDRequest e) throws DataAccessException {
        Database db = new Database();
        try {
            db.openConnection();
            // check if authtoken is associated with user
            Authtoken authtoken = this.getUser(e.getAuthtoken(), db.getConnection());
            if(authtoken == null){
                throw new IOException("No authtoken provided");
            }
            // return single event object
            Event found = new EventDAO(db.getConnection()).find(e.getEventID());

            if(found == null){
                throw new IOException("Invalid eventID provided");
            }

            // if person is not associated with user
            if(!found.getUsername().equals(authtoken.getUsername())){
                throw new IOException("Invalid User provided");
            }

            db.closeConnection(true);
            return new EventIDResult(found);

        } catch (DataAccessException ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            EventIDResult result = new EventIDResult(false, "Error: " + ex.getMessage());
            return result;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            EventIDResult result = new EventIDResult(false, "Error: " + ex.getMessage());
            return result;
        }
    }
}
