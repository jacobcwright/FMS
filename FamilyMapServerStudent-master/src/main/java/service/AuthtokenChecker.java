package service;

import dao.AuthtokenDAO;
import dao.DataAccessException;
import model.Authtoken;
import model.User;

import java.sql.Connection;

/**
 * base class to be inherited from
 * to check authtoken and return boolean success or authtokens
 */
public class AuthtokenChecker {
    /**
     * get authtoken object based on authtoken string
     * @param authtoken
     * @param conn
     * @return
     * @throws DataAccessException
     */
    public Authtoken getUser(String authtoken, Connection conn) throws DataAccessException{
        return(new AuthtokenDAO(conn).get(authtoken));
    }
}
