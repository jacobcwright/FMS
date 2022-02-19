package result;

/**
 * Load Result class
 */
public class LoadResult extends Response{
    /**
     * Constructor based on success
     * @param s
     * @param m
     */
    public LoadResult(Boolean s, String m) {
        success = s;
        message = m;
    }
}
