package service;

import dao.AuthtokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.UserDAO;
import model.Authtoken;
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

            // Add user to database
            new UserDAO(db.getConnection()).insert(u);

                                                // log user in
            // generate authtoken
            String token = UUID.randomUUID().toString();
            // make authtoken model
            Authtoken a = new Authtoken(token, u.getUsername());
            // add to authtoken db
            new AuthtokenDAO(db.getConnection()).add(a);

            db.closeConnection(true);

            RegisterResult result = new RegisterResult(a.getAuthtoken(), u.getUsername(), u.getPersonID());
            return result;
        } catch(DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
            Response result = new Response(false, "Error");

        }
        return null;
    }
}
