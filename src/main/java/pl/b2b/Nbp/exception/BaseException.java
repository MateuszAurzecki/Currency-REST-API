package pl.b2b.Nbp.exception;

public class BaseException extends RuntimeException {
    private String message;

    public BaseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
