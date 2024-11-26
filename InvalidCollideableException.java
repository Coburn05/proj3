public class InvalidCollideableException extends RuntimeException {
    public InvalidCollideableException(Collideable c) {
        super("Behavior for " + c.getClass().getName() + " undefined");
    }
}