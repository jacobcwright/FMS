package dao;

import model.Event;
import model.Person;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data access object for Person Model Objects
 */
public class PersonDAO {
    /**
     * Person object
     */
    private Person person;
    /**
     * connection to database
     */
    private final Connection conn;

    /**
     * Constructor for connection only
     * @param conn
     */
    public PersonDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * PersonDAO Constructor with Person Object & Connection
     * @param person
     * @param conn
     */
    public PersonDAO(Person person, Connection conn) {
        this.person = person;
        this.conn = conn;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * deletes person from person table based on personID
     * @param personID
     */
    public void deletePerson(String personID) throws DataAccessException {
        String sql = "DELETE FROM Person WHERE PersonID=?;";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting from the database");
        }
    }

    /**
     * deletes people from person table based on associated username
     * @param username
     */
    public void deletePeopleFromUser(String username) throws DataAccessException {
        String sql = "DELETE FROM Person WHERE associatedUsername=?;";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting from the database");
        }
    }

    /**
     * inserts person into Person table
     * @param person
     * @throws DataAccessException
     */
    public void insert(Person person) throws DataAccessException {

        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?);";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * clear persons
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Person;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing database");
        }
    }

    /**
     * Returns person object based on personID
     * @param personID
     * @return
     */
    public Person getPerson(String personID) throws DataAccessException {
        Person person;
        String sql = "SELECT * FROM Person WHERE PersonID = ?;";
        ResultSet result = null;

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, personID);
            result = stmt.executeQuery();
            if(result.next()){
                //parse result
                person = new Person(result.getString("personID"),result.getString("associatedUsername"),
                        result.getString("firstName"),result.getString("lastName"),
                        result.getString("gender"), result.getString("fatherID"),
                        result.getString("motherID"), result.getString("spouseID"));
                return person;
            }
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
     * Gets array of Person objects of people associated with User
     * @param username of user
     * @return array of person objects
     * @throws DataAccessException
     */
    public ArrayList<Person> getPeople(String username) throws DataAccessException {
        Person p;
        ArrayList<Person> people = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                p = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                people.add(p);
            }
            return people;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error encountered while finding events");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
