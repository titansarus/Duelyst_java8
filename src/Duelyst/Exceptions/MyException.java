package Duelyst.Exceptions;

public class MyException extends RuntimeException {
    private String title;
    public MyException(String message,String title) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
