package allen.lloyd.framework.di.exception;

public class DIFException extends RuntimeException {
    public DIFException() {
        super();
    }

    public DIFException(String message) {
        super(message);
    }

    public DIFException(String message, Throwable cause) {
        super(message, cause);
    }

    public DIFException(Throwable cause) {
        super(cause);
    }

    protected DIFException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
