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

    /**
     * Equals override for Authtoken
     * compares authtoken (String) & username (String)
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        Authtoken token = (Authtoken) obj;
        if(!this.authtoken.equals(token.getAuthtoken())) return false;
        if(!this.username.equals(token.getUsername())) return false;
        return true;
    }
}
