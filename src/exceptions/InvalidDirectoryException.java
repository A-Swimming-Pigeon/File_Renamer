package exceptions;

public class InvalidDirectoryException extends Exception{
    public InvalidDirectoryException() {
        super();
    }

    public InvalidDirectoryException(String s) {
        super(s);
    }
}
