package service;

import dao.*;
import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;
import result.LoadResult;

/**
 * Load Service Class
 */
public class LoadService {
    /**
     * Clears all data from the database (just like the /clear API)
     * Loads the user, person, and event data from the request body into the database.
     * @param l
     * @return
     */
    public LoadResult load(LoadRequest l) throws DataAccessException {
        System.out.println("in LoadService");
        Database db = new Database();

        try{
            db.openConnection();
            // clear database
            new UserDAO(db.getConnection()).clear();
            new PersonDAO(db.getConnection()).clear();
            new EventDAO(db.getConnection()).clear();

            // load database with Users, Persons, and Events
            for(User u : l.getUsers()){
                new UserDAO(db.getConnection()).insert(u);
            }
            for(Person p : l.getPersons()){
                new PersonDAO(db.getConnection()).insert(p);
            }
            for(Event e : l.getEvents()){
                new EventDAO(db.getConnection()).insert(e);
            }

            db.closeConnection(true);

            LoadResult result = new LoadResult(true, "Successfully added " + l.getUsers().length + " users, " +
                    l.getPersons().length + " persons, and " + l.getEvents().length + " events to the database.");
            return result;

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new LoadResult(false, "Error: " + e.getMessage());
        }
    }

}
