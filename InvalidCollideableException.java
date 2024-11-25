public class InvalidCollideableException extends RuntimeException {
    public InvalidCollideableException() {
        super("Collideable must either be a Wall or a Particle");
    }
}