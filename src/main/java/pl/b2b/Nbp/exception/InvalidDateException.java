package pl.b2b.Nbp.exception;

public class InvalidDateException extends BaseException {
    public InvalidDateException() {
        super("Wrong date in Url");
    }
}
