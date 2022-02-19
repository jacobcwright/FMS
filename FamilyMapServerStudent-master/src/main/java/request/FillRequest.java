package request;

/**
 * Fill Request class
 */
public class FillRequest {
    /**
     * username of user to fill
     */
    String username;

    /**
     * # of generations to fill for user
     */
    int generations;

    /**
     * Constructor for fill request
     * @param username
     * @param generations
     */
    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
