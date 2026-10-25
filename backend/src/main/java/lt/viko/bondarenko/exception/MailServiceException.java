package lt.viko.bondarenko.exception;

public class MailServiceException extends RuntimeException{
    public MailServiceException(String message) {
        super(message);
    }
}
