package service;

import dao.AuthtokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.UserDAO;
import model.Authtoken;
import model.User;
import request.LoginRequest;
import result.LoginResult;
import result.Response;

import java.util.UUID;


/**
 * Login service class
 * Logs the user in
 * Returns an authtoken.
 */
public class LoginService {

    /**
     * Login Service
     * @param l
     * @return
     */
    public LoginResult login(LoginRequest l) throws DataAccessException {
        Database db = new Database();
        try {
            User u = new UserDAO(db.getConnection()).getUser(l.getUsername());
            if(u == null){
                db.closeConnection(false);
                LoginResult result = new LoginResult(false, "Error: Username or password was incorrect");
                return result;
            }
            if(u.getPassword().equals(l.getPassword())){
                // generate authtoken
                String token = UUID.randomUUID().toString();
                // make authtoken model
                Authtoken a = new Authtoken(token, l.getUsername());
                // add to authtoken db
                new AuthtokenDAO(db.getConnection()).add(a);
                db.closeConnection(true);

                LoginResult result = new LoginResult(a.getAuthtoken(),l.getUsername(), u.getPersonID());
                return result;
            }
            // password is incorrect
            else {
                db.closeConnection(false);
                LoginResult result = new LoginResult(false, "Error: Username or password was incorrect");
                return result;
            }
        } catch(DataAccessException e){
            e.printStackTrace();
            db.closeConnection(false);
            LoginResult result = new LoginResult(false, "Error: Username or password was incorrect");
            return result;
        }
    }
}
