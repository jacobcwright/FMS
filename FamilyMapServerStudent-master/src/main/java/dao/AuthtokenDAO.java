package dao;

import model.Authtoken;

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
    public void add(String a){

    }

    /**
     * removes authtoken a from table
     * @param a
     */
    public void remove(String a){

    }

    /**
     * returns authtoken from table
     * @param a
     * @return
     */
    public Authtoken get(String a){
        return null;
    }

    /**
     * clear authtokens
     */
    public void clear(){

    }

}
