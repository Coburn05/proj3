public class WallCollisionException extends RuntimeException {
    public WallCollisionException () {
        super("Two walls somehow collided");
    }
}
