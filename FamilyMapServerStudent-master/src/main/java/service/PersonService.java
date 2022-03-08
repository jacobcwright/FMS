package service;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDAO;
import model.Authtoken;
import model.Person;
import request.PersonRequest;
import result.EventResult;
import result.PersonResult;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Person Service Class
 */
public class PersonService extends AuthtokenChecker{
    /**
     * Returns ALL family members of the current user. The current user is determined by the provided authtoken.
     * @return
     * @param req
     */
    public PersonResult person(PersonRequest req) throws DataAccessException {
        Database db = new Database();
        try{
            db.openConnection();

            // check if authtoken is associated with user
            Authtoken authtoken = this.getUser(req.getAuthToken(), db.getConnection());
            if(authtoken == null){
                throw new IOException("No authtoken provided");
            }
            // get array of people and return it
            ArrayList<Person> people = new PersonDAO(db.getConnection()).getPeople(authtoken.getUsername());
            db.closeConnection(true);
            return new PersonResult(people);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            PersonResult result = new PersonResult(false, "Error: " + e.getMessage());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            db.closeConnection(false);
            PersonResult result = new PersonResult(false, "Error: " + e.getMessage());
            return result;
        }
    }
}
