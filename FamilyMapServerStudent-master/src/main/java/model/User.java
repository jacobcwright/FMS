package model;

/**
 * User model class. Models data from user table.
 */
public class User {
    /**
     * Unique username for user
     */
    private String username;
    /**
     * User’s password
     */
    private String password;
    /**
     * User’s email address
     */
    private String email;
    /**
     * User’s first name
     */
    private String firstName;
    /**
     * User’s last name
     */
    private String lastName;
    /**
     * User’s gender, String "f" or "m"
     */
    private String gender;
    /**
     * Unique Person ID assigned to this user’s generated Person
     */
    private String personID;

    /**
     * User constructor with all params.
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     * @param personID
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object obj) {
        User u = (User) obj;

        if(!this.getUsername().equals(u.getUsername())){
            return false;
        }
        if(!this.getPassword().equals(u.getPassword())){
            return false;
        }
        if(!this.getEmail().equals(u.getEmail())){
            return false;
        }
        if(!this.getFirstName().equals(u.getFirstName())){
            return false;
        }
        if(!this.getLastName().equals(u.getLastName())){
            return false;
        }
        if(!this.getPersonID().equals(u.getPersonID())){
            return false;
        }
        return true;
    }
}
