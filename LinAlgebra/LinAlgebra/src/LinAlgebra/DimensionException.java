package LinAlgebra;

/**
 * Wyjątek zgłaszany, gdy wymiary tablic nie są odpowiednie do wykonania danej operacji
 */
public class DimensionException extends Exception {
    public DimensionException(String message) {
        super(message);
    }
}