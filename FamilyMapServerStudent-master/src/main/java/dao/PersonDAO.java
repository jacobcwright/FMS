package dao;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void deletePerson(String personID){

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

}
