package exceptions;

public class NoHashingStrategyProvidedException extends RuntimeException {
    public NoHashingStrategyProvidedException(String message) {
        super(message);
    }
}
