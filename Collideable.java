public abstract class Collideable {
  public abstract void update(double lastTime);
  public abstract double getVX();
  public abstract double getVY();
  public abstract double getX();
  public abstract double getY();
  public abstract boolean lastUpdateTimeMatch(double lastTime);
  public void updateAfterCollision(double now, Collideable other) throws InvalidCollideableException, WallCollisionException {
    if (other instanceof Particle) {
      updateAfterCollision(now, (Particle) other);
    } else if (other instanceof Wall) {
      updateAfterCollision(now, (Wall) other);
    } else throw new InvalidCollideableException(this, other);
  }
  protected abstract void updateAfterCollision(double now, Particle other);
  protected abstract void updateAfterCollision(double now, Wall other);
  public double getCollisionTime (Collideable other) throws InvalidCollideableException {
    if (other instanceof Particle) {
      return getCollisionTime((Particle) other);
    } else if (other instanceof Wall) {
      return getCollisionTime((Wall) other);
    } else throw new InvalidCollideableException(this, other);
  }
  protected abstract double getCollisionTime (Particle other);
  protected abstract double getCollisionTime (Wall other);
}

class InvalidCollideableException extends RuntimeException {
  public InvalidCollideableException(Collideable c, Collideable other) {
      super("The collision between " + c.getClass().getName() + " and " + other.getClass().getName() + " undefined");
  }
}

class WallCollisionException extends RuntimeException {
  public WallCollisionException () {
      super("Two walls somehow collided");
  }
}
