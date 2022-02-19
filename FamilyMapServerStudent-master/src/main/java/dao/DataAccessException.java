package dao;

/**
 * Data Access Exception class
 */
public class DataAccessException extends Exception {
    /**
     * Constructor for message param, calls super
     * @param message
     */
    DataAccessException(String message)
    {
        super(message);
    }

    /**
     * empty constructor
     */
    DataAccessException()
    {
        super();
    }
}
