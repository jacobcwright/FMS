package model;

/**
 * Authtoken model class. Models data in Authtoken table.
 */
public class Authtoken {
    /**
     * authtoken value. Unique.
     */
    private String authtoken;

    /**
     * Username value. Unique.
     */
    private String username;

    /**
     * Authtoken Constructor with params.
     * @param authtoken
     * @param username
     */
    public Authtoken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
