package service;

import dao.AuthtokenDAO;
import dao.DataAccessException;
import model.Authtoken;
import model.User;

import java.sql.Connection;

public class AuthtokenChecker {
    public boolean checkAuthtoken(Authtoken a, Connection conn) throws DataAccessException {
        return (new AuthtokenDAO(conn).get(a.getAuthtoken()).equals(a));
    }
    public Authtoken getUser(String authtoken, Connection conn) throws DataAccessException{
        return(new AuthtokenDAO(conn).get(authtoken));
    }
}
