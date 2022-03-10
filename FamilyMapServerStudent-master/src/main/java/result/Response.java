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

    public Response() {}

    /**
     * base constructor to be used for failures
     * @param success
     * @param message
     */
    public Response(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
