package result;

/**
 * Response parent class, contains success & message data members
 */
public class Response {
    /**
     * Success boolean, true or false
     */
    Boolean success;

    /**
     * Message to be used for failed requests.
     */
    String message;

    public Response() {
    }

    public Response(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
