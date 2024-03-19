package fr.ofghanirre.mineproj;

public class NotImplementedException extends RuntimeException {
    public NotImplementedException(String behaviourNotHandled) {
        super(behaviourNotHandled);
    }
}
