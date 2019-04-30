package pl.b2b.Nbp.exception;

public class InvalidDateRangeException extends BaseException {
    public InvalidDateRangeException() {
        super("Date range should be less than 93 days");


    }
}