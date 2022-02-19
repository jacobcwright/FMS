package result;

/**
 * Clear Result Class
 */
public class ClearResult extends Response{

    /**
     * Constructor dependent on success
     */
    public ClearResult() {
        if(success){
            message = "Clear succeeded.";
        }
        else {
            message = "Error:[Description of the error]";
        }
    }

}
