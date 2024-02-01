package exceptions;

public class InvalidNameSizeException extends RuntimeException {
    public InvalidNameSizeException(String s) {
        super(s);
    }
}
