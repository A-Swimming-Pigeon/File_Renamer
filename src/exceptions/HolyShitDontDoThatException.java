package exceptions;

public class HolyShitDontDoThatException extends RuntimeException{
    public HolyShitDontDoThatException() {
        super();
    }
    public HolyShitDontDoThatException(String s) {
        super(s);
    }
}
