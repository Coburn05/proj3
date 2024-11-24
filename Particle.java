import java.awt.*;
import java.util.*;

public class Particle implements Collidable {
	private String name;
	private double x, y;
	private double vx, vy;
	private double radius;
	private double lastUpdateTime;

	public double getX() { return this.x; }
	public double getY() { return this.y; }
	public double getVX() { return this.vx; }
	public double getVY() { return this.vy; }
	public double getRadius() { return this.radius; }
	public double getLastUpdateTime() { return lastUpdateTime; }
	public String getName() { return this.name; }

	public double[] getVelocity() { return new double[]{this.vx, this.vy}; }
	public double[] getPosition() { return new double[]{this.x, this.y}; }

	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public void setVX(double vx) { this.vx = vx; }
	public void setVY(double vy) { this.vy = vy; }
	public void setLastUpdateTime(double lastUpdateTime) { this.lastUpdateTime = lastUpdateTime; }
	public void setName(String name) { this.name = name; }

	@Override
	public void resolveCollision(Collidable other, double time) {
		if (other instanceof Particle) {
			// Particle-particle collision
			updateAfterCollision(time, (Particle) other);
		} else if (other instanceof Wall) {
			// Particle-wall collision
			Wall wall = (Wall) other;
			if (wall.getIsVertical()) {
				vx = -vx; // Invert horizontal velocity on vertical wall collision
			} else {
				vy = -vy; // Invert vertical velocity on horizontal wall collision
			}
		}
	}

	/**
	 * Helper method to parse a string into a Particle.
	 * DO NOT MODIFY THIS METHOD
	 * @param str the string to parse
	 * @return the parsed Particle
	 */
	public static Particle build (String str) {
		String[] tokens = str.split("\\s+");
		double[] nums = Arrays.stream(Arrays.copyOfRange(tokens, 1, tokens.length))
				      .mapToDouble(Double::parseDouble)
				      .toArray();
		return new Particle(tokens[0], nums[0], nums[1], nums[2], nums[3], nums[4]);
	}

	/**
	 * @name name of the particle (useful for debugging)
	 * @param x x-coordinate of the particle
	 * @param y y-coordinate of the particle
	 * @param vx x-velocity of the particle
	 * @param vy y-velocity of the particle
	 * @param radius radius of the particle
	 */
	Particle (String name, double x, double y, double vx, double vy, double radius) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.radius = radius;
	}

	/**
	 * Draws the particle as a filled circle.
	 * DO NOT MODIFY THIS METHOD
	 */
	void draw (Graphics g) {
		g.fillOval((int) (x - radius), (int) (y - radius), (int) (2*radius), (int) (2*radius));
	}

	/**
	 * Useful for debugging.
	 */
	public String toString () {
		return (name.equals("") ? "" : name + " ") + x + "  " + y + " " + vx + " " + vy + " " + radius;
	}

	/**
	 * Updates the position of the particle after an elapsed amount of time, delta, using
	 * the particle's current velocity.
	 * @param delta the elapsed time since the last particle update
	 */
	public void update (double delta) {
		double newX = x + delta * vx;
		double newY = y + delta * vy;
		x = newX;
		y = newY;
	}

	/**
	 * Updates both this particle's and another particle's velocities after a collision between them.
	 * DO NOT CHANGE THE MATH IN THIS METHOD
	 * @param now the current time in the simulation
	 * @param other the particle that this one collided with
	 */
	private void updateAfterCollision (double now, Particle other) {
		double vxPrime, vyPrime;
		double otherVxPrime, otherVyPrime;
		double common = ((this.vx - other.getVX()) * (this.x - other.getX()) +
				 (this.vy - other.getVY()) * (this.y - other.getY())) /
			     (Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
		vxPrime = this.vx - common * (this.x - other.getX());
		vyPrime = this.vy - common * (this.y - other.getY());
		otherVxPrime = other.getVX() - common * (other.getX() - this.x);
		otherVyPrime = other.getVY() - common * (other.getY() - this.y);

		this.vx = vxPrime;
		this.vy = vyPrime;
		other.setVX(otherVxPrime);
		other.setVY(otherVyPrime);

		lastUpdateTime = now;
		other.setLastUpdateTime(now);
	}

	public double collisionTime(Collidable other) {
		return this.getCollisionTime(other);
	}

	/**
	 * Computes and returns the time when (if ever) this particle will collide with another particle,
	 * or infinity if the two particles will never collide given their current velocities.
	 * DO NOT CHANGE THE MATH IN THIS METHOD
	 * @param other the other particle to consider
	 * @return the time with the particles will collide, or infinity if they will never collide
	 */
	private double getCollisionTime (Collidable other) {
		// See https://en.wikipedia.org/wiki/Elastic_collision#Two-dimensional_collision_with_two_moving_objects
		double a = this.vx - other.getVX();
		double b = this.x - other.getX();
		double c = this.vy - other.getVY();
		double d = this.y - other.getY();
		double r = this.radius;

		double A = a*a + c*c;
		double B = 2 * (a*b + c*d);
		double C = b*b + d*d - 4*r*r;

		// Numerically more stable solution to QE.
		// https://people.csail.mit.edu/bkph/articles/Quadratics.pdf
		double t1, t2;
		if (B >= 0) {
			t1 = (-B - Math.sqrt(B*B - 4*A*C)) / (2*A);
			t2 = 2*C / (-B - Math.sqrt(B*B - 4*A*C));
		} else {
			t1 = 2*C / (-B + Math.sqrt(B*B - 4*A*C));
			t2 = (-B + Math.sqrt(B*B - 4*A*C)) / (2*A);
		}

		// Require that the collision time be slightly larger than 0 to avoid
		// numerical issues.
		double SMALL = 1e-6;
		double t;
		if (t1 > SMALL && t2 > SMALL) {
			t = Math.min(t1, t2);
		} else if (t1 > SMALL) {
			t = t1;
		} else if (t2 > SMALL) {
			t = t2;
		} else {
			// no collision
			t = Double.POSITIVE_INFINITY;
		}

		return t;
	}
}
