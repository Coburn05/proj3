public abstract class WallOrParticle implements Collideable {
    public void updateAfterCollision(double now, Collideable other) throws InvalidCollideableException, WallCollisionException {
		if (other instanceof Particle) {
			updateAfterCollision(now, (Particle) other);
		} else if (other instanceof Wall) {
			updateAfterCollision(now, (Wall) other);
		} else throw new InvalidCollideableException();
	}
    protected void updateAfterCollision(double now, Particle other) {
        throw new RuntimeException("update method for particle not defined");
    }
    protected void updateAfterCollision(double now, Wall other) {
        throw new RuntimeException("update method for wall not defined");
    }
    public double getCollisionTime (Collideable other) throws InvalidCollideableException {
		if (other instanceof Particle) {
			return getCollisionTime((Particle) other);
		} else if (other instanceof Wall) {
			return getCollisionTime((Wall) other);
		} else throw new InvalidCollideableException();
	}
    protected double getCollisionTime (Particle other) {
        throw new RuntimeException("collision time method with a particle not defined");
    }
    protected double getCollisionTime (Wall other) {
        throw new RuntimeException("collision time method with a wall not defined");
    }
}
