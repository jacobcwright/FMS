package dao;

import model.Person;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for User Objects.
 */
public class UserDAO {
    /**
     * connection to database
     */
    private final Connection conn;

    /**
     * Constructor for connection
     * @param conn
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds user u to database
     * @param u
     */
    public void insert(User u) throws DataAccessException {

        String sql = "INSERT INTO User (username, password, email, firstName, lastName, gender, personID) VALUES(?,?,?,?,?,?,?);";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getFirstName());
            stmt.setString(5, u.getLastName());
            stmt.setString(6, u.getGender());
            stmt.setString(7, u.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Gets user from database based on username
     * @param username
     * @return User
     */
    public User getUser(String username) throws DataAccessException {
        User user;
        String sql = "SELECT * FROM User WHERE username = ?;";
        ResultSet result = null;

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            result = stmt.executeQuery();
            if(result.next()){
                //parse result
                user = new User(result.getString("username"),result.getString("password"),
                        result.getString("email"), result.getString("firstName"), result.getString("lastName"),
                        result.getString("gender"), result.getString("personID"));
                return user;
            }
//            else {
//                throw new DataAccessException("Error encountered while retrieving from database");
//            }
        } catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while retrieving from database");
        } finally {
            if(result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Get person from database based on personID
     * @param personID
     * @return
     */
    public Person getPerson(String personID){
        return null;
    }

    /**
     * Deletes user from database based on username
     * @param username
     */
    public void deleteUser(String username){

    }

    /**
     * clear users
     */
    public void clear() throws DataAccessException{
        String sql = "DELETE FROM User;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing database");
        }
    }

}
