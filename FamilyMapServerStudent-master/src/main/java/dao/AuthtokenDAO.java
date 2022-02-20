package dao;

import model.Authtoken;

import javax.xml.crypto.Data;
import java.sql.Connection;

/**
 * Data access object for authtoken
 */
public class AuthtokenDAO {
    /**
     * Connection to database
     */
    private final Connection conn;

    /**
     * constructor for connection
     * @param conn
     */
    public AuthtokenDAO(Connection conn) {
        this.conn = conn;
    }


    /**
     * adds authtoken a to table
     * @param a
     */
    public void add(String a) throws DataAccessException {

    }

    /**
     * removes authtoken a from table
     * @param a
     */
    public void remove(String a) throws DataAccessException {

    }

    /**
     * returns authtoken from table
     * @param a
     * @return
     */
    public Authtoken get(String a) throws DataAccessException {
        return null;
    }

    /**
     * clear authtokens
     */
    public void clear() throws DataAccessException {

    }

}
