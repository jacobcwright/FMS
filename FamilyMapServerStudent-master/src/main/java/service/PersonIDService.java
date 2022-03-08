package service;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDAO;
import model.Authtoken;
import model.Person;
import request.PersonIDRequest;
import result.EventIDResult;
import result.PersonIDResult;
import result.PersonResult;

import java.io.IOException;

/**
 * Person ID Service Class
 */
public class PersonIDService extends AuthtokenChecker{


    /**
     * Finds person based on person ID
     * @param p
     * @return
     * @throws DataAccessException
     */
    public PersonIDResult personID(PersonIDRequest p) throws DataAccessException {
        Database db = new Database();

        try {
            db.openConnection();
            // check if authtoken is associated with user
            Authtoken authtoken = this.getUser(p.getAuthtoken(), db.getConnection());
            if(authtoken == null){
                throw new IOException("No authtoken provided");
            }

            // try to find person based on personID
            Person found = new PersonDAO(db.getConnection()).getPerson(p.getPersonID());
            if(found == null){
                throw new IOException("Invalid personID provided");
            }

            // return results
            db.closeConnection(true);
            return new PersonIDResult(found);

        } catch (IOException e) {
            e.printStackTrace();
            db.closeConnection(false);
            PersonIDResult result = new PersonIDResult(false, "Error: " + e.getMessage());
            return result;
        }
    }
}
