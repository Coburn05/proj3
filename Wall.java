import javax.management.RuntimeErrorException;

public class Wall extends WallOrParticle {
    private boolean _isVertical;
    private double _x, _y;

    public Wall(boolean isVertical, double x, double y) {
        _isVertical = isVertical;
        _x = x;
        _y = y;
    }
    public void update (double delta) {
        //walls don't move
    }
    public void updateAfterCollision(double now, Collideable other) throws WallCollisionException, InvalidCollideableException {
        if (other instanceof Particle) {
            updateAfterCollision(now, (Particle) other);
        } else if (other instanceof Wall) {
            updateAfterCollision(now, (Wall) other);
        } else throw new WallCollisionException();
    } 
    @Override
    protected void updateAfterCollision(double now, Wall other) throws WallCollisionException {
        throw new WallCollisionException();
    }
    @Override
    protected void updateAfterCollision(double now, Particle other) {
        other.updateAfterCollision(now, this);
    }
    @Override
    protected double getCollisionTime(Wall other) {
        return Double.POSITIVE_INFINITY;
    }
    @Override
    public double getCollisionTime(Particle other) {
        return other.getCollisionTime(this);
    }
    public double getVX() {
		return 0;
	}
	public double getVY() {
		return 0;
	}
	public double getX() {
        if (!_isVertical) throw new RuntimeException("Cannot access x attribute of a horizontal wall");
		return _x;
	}
	public double getY() {
        if (_isVertical) throw new RuntimeException("Cannot access y attribute of a vertical wall");
		return _y;
	}
	public boolean lastUpdateTimeMatch(double lastTime) {
        return true; //if a collision with a wall is up, then it must be valid from the wall's perspective
    }  
    public boolean isVertical() {
        return _isVertical;
    }
   
}
