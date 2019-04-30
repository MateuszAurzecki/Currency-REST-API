package pl.b2b.Nbp.dto;

public class ErrorDto {

    private String event;
    private String message;

    public ErrorDto() {
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
