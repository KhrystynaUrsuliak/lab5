package labs.lab5.exception;

public class UniqnessException extends RuntimeException {

    public UniqnessException() {
        super();
    }

    public UniqnessException(String message) {
        super(message);
    }

    public UniqnessException(Throwable throwable) {
        super(throwable);
    }

    public UniqnessException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
