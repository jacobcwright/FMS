package service;

import dao.*;
import model.Event;
import model.Person;
import model.User;
import request.FillRequest;
import result.FillResult;
import result.Response;
import utils.DataGenerator;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Fill Service class
 */
public class FillService {
    /**
     * Populates the server's database with generated data for the specified username.
     * The required "username" parameter must be a user already registered with the server.
     * If there is any data in the database already associated with the given username, it is deleted.
     * The optional "generations" parameter lets the caller specify the number of generations of ancestors to be generated,
     * and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
     * @param f
     * @return
     * @throws DataAccessException
     */
    public FillResult fill(FillRequest f) throws DataAccessException {
        System.out.println("in Fill Service");
        Database db = new Database();
        try {
            db.openConnection();


            // create DataGenerator Object for User
            User u = new UserDAO(db.getConnection()).getUser(f.getUsername());
            if(u == null){
                throw new IOException("Invalid username");
            }
            String personID = u.getPersonID();
            Person p = new PersonDAO(db.getConnection()).getPerson(personID);
            new PersonDAO(db.getConnection()).deletePeopleFromUser(f.getUsername());
            new EventDAO(db.getConnection()).deleteEventsFromUser(f.getUsername());
            DataGenerator data = new DataGenerator();

            // generate data
            data.Generate(p, f.getGenerations(), 2003); // year as 2003 so each User is 18 or older

            // get people & events generated
            ArrayList<Person> people = data.getPeople();
            ArrayList<Event> events = data.getEvents();

            // add people to database
            for(Person person : people){
                if(person.equals(p)){
                    new PersonDAO(db.getConnection()).deletePerson(p.getPersonID());
                }
                new PersonDAO(db.getConnection()).insert(person);
            }

            // add event to database
            for(Event event : events){
                new EventDAO(db.getConnection()).insert(event);
            }

            db.closeConnection(true);
            // return response
            FillResult result = new FillResult(true, "Successfully added " + people.size() +
                    " persons and " + events.size() + " events to the database.");
            return result;
        } catch (DataAccessException | IOException e) {
            e.printStackTrace();
            db.closeConnection(false);
            FillResult result = new FillResult(false, "Error " + e.getMessage());
            return result;
        }
    }
}
