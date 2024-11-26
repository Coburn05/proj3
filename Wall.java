public class Wall extends Collideable {
    private boolean _isVertical;
    private double _x, _y;

    /**
     * @param isVertical, will this wall be a verical wall
     * @param x, location on the x axis
     * @param y, location on the y axis
     * @return void
     */
    public Wall(boolean isVertical, double x, double y) {
        _isVertical = isVertical;
        _x = x;
        _y = y;
    }

    /**
     * does nothing, walls do not move
     * @param delta, double ammount of time elapsed
     * @return void
     */
    public void update (double delta) {
        //walls don't move
    }

    /**
     * update particle that collided with this wall
     * @param now, double current time
     * @param other, Collidable for the other object that collided with the wall
     * @throws WallCollisionException, walls cant collide
     * @throws InvalidCollideableException, invalid collisions cant not be handeled
     * @return void
     */
    public void updateAfterCollision(double now, Collideable other) throws WallCollisionException, InvalidCollideableException {
        if (other instanceof Particle) {
            updateAfterCollision(now, (Particle) other);
        } else if (other instanceof Wall) {
            updateAfterCollision(now, (Wall) other);
        } else throw new WallCollisionException();
    }

    @Override
    /**
     * this will throw an error
     * @param now, double for current time
     * @param other, Wall that was collided with
     * @throws WallCollisionException, walls cant collide
     * @return void
     */
    protected void updateAfterCollision(double now, Wall other) throws WallCollisionException {
        throw new WallCollisionException();
    }

    @Override
    /**
     * update particle that collided with wall
     * @param now, double for current time
     * @param other, Particle that hit the wall
     * @return void
     */
    protected void updateAfterCollision(double now, Particle other) {
        other.updateAfterCollision(now, this);
    }

    @Override
    /**
     * gets collision time with another wall
     * @param other, Wall to get time for
     * @return Double.POSITIVE_INFINITY, walls can never collide
     */
    protected double getCollisionTime(Wall other) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    /**
     * gets collision time with another particle
     * @param other, Particle to get the collision time for
     * @return the time untill this collision will happen
     */
    public double getCollisionTime(Particle other) {
        return other.getCollisionTime(this);
    }

    /**
     * get velocity in the x direction
     * @return velocity in the x direction
     */
    public double getVX() {
		return 0;
	}

    /**
     * get velocity in the y direction
     * @return velocity in the y direction
     */
	public double getVY() {
		return 0;
	}

    /**
     * get position in the x direction
     * @return position in the x direction
     * @throws WrongOrientationException if the wall is horizontal and exists at every x value
     */
	public double getX() throws WrongOrientationException {
        if (!_isVertical) throw new WrongOrientationException(_isVertical);
		return _x;
	}

    /**
     * get position in the x direction
     * @return position in the x direction
     * @throws WrongOrientationException if the wall is vertical and exists at every y value
     */
	public double getY() throws WrongOrientationException {
        if (_isVertical) throw new WrongOrientationException(_isVertical);
		return _y;
	}

    /**
     * is the time of the collision still valid
     * @param lastTime, dummy variable
     * @return true, all collisions with walls are valid
     */
	public boolean lastUpdateTimeMatch(double lastTime) {
        return true; //if a collision with a wall is up, then it must be valid from the wall's perspective
    }

    /**
     * is this wall vertical
     * @return boolean, is this wall a vertical wall
     */
    public boolean isVertical() {
        return _isVertical;
    }
}

/**
 * Error that occurs when an invalid attribute of a wall is accessed
 */
class WrongOrientationException extends RuntimeException {
    public WrongOrientationException(boolean isVertical) {
        super("Cannot access " + (isVertical ? "y" : "x") + " attribute of a " + (isVertical ? "vertical" : "horizontal") + " wall");
    }
 }