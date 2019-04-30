package pl.b2b.Nbp.exception;

public class InvalidCodeException extends BaseException {
    public InvalidCodeException() {
        super("Wrong currency code");
    }
}
