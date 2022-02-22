package dao;

import model.Authtoken;
import model.Event;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void add(Authtoken a) throws DataAccessException {
        String sql = "INSERT INTO Authtoken (authtoken, username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, a.getAuthtoken());
            stmt.setString(2, a.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * removes authtoken a from table
     * @param a
     */
    public void remove(String a) throws DataAccessException {

    }

    /**
     * returns authtoken from table
     * @param token
     * @return authtoken
     */
    public Authtoken get(String token) throws DataAccessException {
        Authtoken authtoken;
        ResultSet rs = null;
        String sql = "SELECT * FROM Authtoken WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authtoken = new Authtoken(rs.getString("authtoken"), rs.getString("username"));
                return authtoken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * clear authtokens
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Authtoken;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing database");
        }
    }

}
