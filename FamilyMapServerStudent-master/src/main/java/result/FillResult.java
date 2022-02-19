package result;

/**
 * Fill Result Class
 */
public class FillResult extends Response{
    /**
     * Constructor based on success
     * @param success
     * @param message
     */
    public FillResult(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
