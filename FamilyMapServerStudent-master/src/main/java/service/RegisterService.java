package service;

import dao.*;
import model.Authtoken;
import model.Person;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;
import result.Response;

import java.util.UUID;

/**
 * Register Service class
 * Creates a new user account (user row in the database)
 * Generates 4 generations of ancestor data for the new user
 * (just like the /fill endpoint if called with a generations value of 4 and this new user’s username as parameters)
 * Logs the user in
 * Returns an authtoken
 */
public class RegisterService {

    /**
     * Registers user from request r
     * @param r
     * @return result from request
     */
    public RegisterResult register(RegisterRequest r) throws DataAccessException {
        Database db = new Database();

        try {
            // open Database
            db.openConnection();


            // create User
            User u = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(),
                    r.getGender(), UUID.randomUUID().toString());

            // check if user is already in DB
            User found = new UserDAO(db.getConnection()).getUser(u.getUsername());
            if(found != null){
                db.closeConnection(false);
                RegisterResult result = new RegisterResult(false, "Error: User already registered");
                return result;
            }

            // Add user to database
            new UserDAO(db.getConnection()).insert(u);

                                                // log user in
            // generate authtoken
            String token = UUID.randomUUID().toString();
            // make authtoken model
            Authtoken a = new Authtoken(token, u.getUsername());
            // add to authtoken db
            new AuthtokenDAO(db.getConnection()).add(a);

            Person p = new Person(u.getPersonID(), u.getUsername(), u.getFirstName(), u.getLastName(), u.getGender());
            // add person to Person db
            new PersonDAO(db.getConnection()).insert(p);

            db.closeConnection(true);

            // return result
            RegisterResult result = new RegisterResult(a.getAuthtoken(), u.getUsername(), u.getPersonID());
            return result;
        } catch(DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
            RegisterResult result = new RegisterResult(false, "Error");
            return result;
        }
    }
}
