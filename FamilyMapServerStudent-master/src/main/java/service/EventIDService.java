package service;

import dao.DataAccessException;
import dao.Database;
import request.EventIDRequest;
import result.EventIDResult;

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
    public EventIDResult eventID(EventIDRequest e){
        Database db = new Database();
        try {
            db.openConnection();
            // check if authtoken is associated with user
            // this.checkAuthtoken()

        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
