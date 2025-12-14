package lt.viko.bondarenko.exception;

public class MaxParticipantsReachedException extends RuntimeException {

    public MaxParticipantsReachedException() {
        super("Cannot add more participants. Maximum limit reached.");
    }
}
