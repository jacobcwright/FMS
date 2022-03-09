package service;

import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import model.Authtoken;
import model.Event;
import request.EventRequest;
import result.EventIDResult;
import result.EventResult;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Event Service Class
 */
public class EventService extends AuthtokenChecker{

    /**
     * Returns ALL events for ALL family members of the current user.
     * The current user is determined from the provided auth token.
     * @return
     * @param req
     */
    public EventResult event(EventRequest req) throws DataAccessException {
        Database db = new Database();
        try{
            db.getConnection();

            // check if authtoken is associated with user
            Authtoken authtoken = this.getUser(req.getAuthToken(), db.getConnection());
            if(authtoken == null){
                throw new IOException("No authtoken provided");
            }

            // return all events
            ArrayList<Event> events = new EventDAO(db.getConnection()).getEvents(authtoken.getUsername());
            db.closeConnection(true);
            return new EventResult(events);

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            EventResult result = new EventResult(false, "Error: " + e.getMessage());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            db.closeConnection(false);
            EventResult result = new EventResult(false, "Error: " + e.getMessage());
            return result;
        }
    }
}
