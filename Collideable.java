public abstract class Collideable {
  /**
   * update position of the particle based on the time elapsed
   * @param delta time elapsed
   */
  public abstract void update(double delta);
  /**
   * @return x velocity of the collideable
   */
  public abstract double getVX();
  /**
   * @return y velocity of the collideable
   */
  public abstract double getVY();
  /**
   * @return x position of the collideable
   */
  public abstract double getX();
  /**
   * @return y position of collideable
   */
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
   * @throws InvalidCollideableException if the update behavior for the collision is undefined
   * @throws WallCollisionException if two immovable walls somehow collide
   */
  public void updateAfterCollision(double now, Collideable other) throws InvalidCollideableException, WallCollisionException {
    if (other instanceof Particle) {
      updateAfterCollision(now, (Particle) other);
    } else if (other instanceof Wall) {
      updateAfterCollision(now, (Wall) other);
    } else throw new InvalidCollideableException(this, other);
  }

  /**
   * collision behavior after the collideable collides with a particle
   * @param now the time of collision
   * @param other the particle thats being collided with
   */
  protected abstract void updateAfterCollision(double now, Particle other);

  /**
   * collision behavior after the collideable collides with a wall
   * @param now the time of collision
   * @param other the wall thats being collided with
   */
  protected abstract void updateAfterCollision(double now, Wall other);

  /**
   * general retrieval of a collision between two collideables
   * @param other the other collideable
   * @return time taken until collision
   * @throws InvalidCollideableException if the collision between the two is undefined
   */
  public double getCollisionTime (Collideable other) throws InvalidCollideableException {
    if (other instanceof Particle) {
      return getCollisionTime((Particle) other);
    } else if (other instanceof Wall) {
      return getCollisionTime((Wall) other);
    } else throw new InvalidCollideableException(this, other);
  }

  /**
   * get collision time between this collideable and a particle
   * @param other
   * @return time taken until collision
   */
  protected abstract double getCollisionTime (Particle other);

  /**
   * get collision time between this collideable and a wall
   * @param other
   * @return time taken until collision
   */
  protected abstract double getCollisionTime (Wall other);
}

/**
 * error caused when a collision between two collideables is not defined
 */
class InvalidCollideableException extends RuntimeException {

  /**
   * @param c the first collideable 
   * @param other the other collideable the has an undefined collision with c
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
