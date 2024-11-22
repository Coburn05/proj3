/**
 * Represents a collision between a particle and another particle, or a particle and a wall.
 */
public class Event implements Comparable<Event> {
	private double timeOfEvent;
	private double timeEventCreated;
	private Collidable collidableA; // Collidable involved in event
	private Collidable collidableB; // Collidable involved in event

	/**
	 * @param timeOfEvent the time when the collision will take place
	 * @param timeEventCreated the time when the event was first instantiated and added to the queue
	 */
	public Event (double timeOfEvent, double timeEventCreated, Collidable collidableA, Collidable collidableB) {
		this.timeOfEvent = timeOfEvent;
		this.timeEventCreated = timeEventCreated;
		this.collidableA = collidableA;
		this.collidableB = collidableB;
	}

	public Event (double timeOfEvent, double timeEventCreated) {
		this.timeOfEvent = timeOfEvent;
		this.timeEventCreated = timeEventCreated;
		this.collidableA = null;
		this.collidableB = null;
	}

	public boolean isValid(double time) {
		boolean status = time <= timeOfEvent;
		// should check update time of particle(s) involved agaisn current time (passed in)
		return status;
	}

	@Override
	/**
	 * Compares two Events based on their event times. Since you are implementing a maximum heap,
	 * this method assumes that the event with the smaller event time should receive higher priority.
	 */
	public int compareTo (Event e) {
		if (timeOfEvent < e.timeOfEvent) {
			return +1;
		} else if (timeOfEvent == e.timeOfEvent) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * gets both particles involved in colision
	 * @return [particleA, particleB]
	 */
	public Collidable[] getCollidables() {
		return new Collidable[]{collidableA, collidableB};
	}

	/**
	 * gets time of event
	 * @return time of event (Double)
	 */
	public double getTimeOfEvent() {
		return this.timeOfEvent;
	}

	/**
	 * gets time of event
	 * @return time event created (Double)
	 */
	public double getTimeCreated() {
		return this.timeEventCreated;
	}

	public Collidable getCollidableA() { return this.collidableA; }
	public Collidable getCollidableB() { return this.collidableB; }
}
