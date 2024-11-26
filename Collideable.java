public abstract class Collideable {
  public abstract void update(double lastTime);
  public abstract double getVX();
  public abstract double getVY();
  public abstract double getX();
  public abstract double getY();

  /**
   * checks if the time is valid
   * @param lastTime, time of the simulation
   * @return boolean value for if the passed time is valid
   */
  public abstract boolean lastUpdateTimeMatch(double lastTime);

  /**
   * updates particles involved in the collision
   * @param now, double for the current time
   * @param other, other Collideable involved in the event
   * @throws InvalidCollideableException
   * @throws WallCollisionException
   */
  public void updateAfterCollision(double now, Collideable other) throws InvalidCollideableException, WallCollisionException {
    if (other instanceof Particle) {
      updateAfterCollision(now, (Particle) other);
    } else if (other instanceof Wall) {
      updateAfterCollision(now, (Wall) other);
    } else throw new InvalidCollideableException(this, other);
  }

  /**
   *
   * @param now
   * @param other
   */
  protected abstract void updateAfterCollision(double now, Particle other);

  /**
   *
   * @param now
   * @param other
   */
  protected abstract void updateAfterCollision(double now, Wall other);

  /**
   *
   * @param other
   * @return
   * @throws InvalidCollideableException
   */
  public double getCollisionTime (Collideable other) throws InvalidCollideableException {
    if (other instanceof Particle) {
      return getCollisionTime((Particle) other);
    } else if (other instanceof Wall) {
      return getCollisionTime((Wall) other);
    } else throw new InvalidCollideableException(this, other);
  }

  /**
   *
   * @param other
   * @return
   */
  protected abstract double getCollisionTime (Particle other);

  /**
   *
   * @param other
   * @return
   */
  protected abstract double getCollisionTime (Wall other);
}

class InvalidCollideableException extends RuntimeException {

  /**
   *
   * @param c
   * @param other
   */
  public InvalidCollideableException(Collideable c, Collideable other) {
      super("The collision between " + c.getClass().getName() + " and " + other.getClass().getName() + " undefined");
  }
}

class WallCollisionException extends RuntimeException {

  /**
   * error caused when two walls collide, this kind of event should never happen
   */
  public WallCollisionException () {
      super("Two walls somehow collided");
  }
}
