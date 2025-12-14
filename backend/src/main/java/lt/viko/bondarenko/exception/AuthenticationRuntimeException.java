package lt.viko.bondarenko.exception;

public class AuthenticationRuntimeException extends RuntimeException {

    public AuthenticationRuntimeException() {
        super("User not authenticated");
    }

}
