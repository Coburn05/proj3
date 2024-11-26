/**
 * Represents a collision between a particle and another particle, or a particle and a wall.
 */
public class Event implements Comparable<Event> {
	private double _timeOfEvent;
	private double _timeEventCreated;
	private Collideable _item_a;
	private Collideable _item_b;

	/**
	 * @param timeOfEvent the time when the collision will take place
	 * @param timeEventCreated the time when the event was first instantiated and added to the queue
	 */
	public Event (double timeOfEvent, double timeEventCreated) {
		_timeOfEvent = timeOfEvent;
		_timeEventCreated = timeEventCreated;
	}
	/**
	 * @param timeOfEvent the time when the collision will take place
	 * @param timeEventCreated the time when the event was first instantiated and added to the queue
	 */
	public Event (double timeOfEvent, double timeEventCreated, Collideable item_a, Collideable item_b) {
		_timeOfEvent = timeOfEvent;
		_timeEventCreated = timeEventCreated;
		_item_a = item_a;
		_item_b = item_b;
	}

	/**
	 * method that checks if this event is a valid event
	 * @return boolean value for if the event is valid
	 */
	public boolean isValid() {
		return _item_a.lastUpdateTimeMatch(_timeEventCreated) && _item_b.lastUpdateTimeMatch(_timeEventCreated);
	}

	/**
	 * resolves the collision by updating the particles involved
	 * @return void
	 */
	public void resolveCollision() {
		_item_a.updateAfterCollision(_timeOfEvent, _item_b);
	}

	/**
	 * gets the A particle for the collision
	 * @return Collidable that is the A particle
	 */
	public Collideable getA() {
		return _item_a;
	}

	/**
	 * gets the B particle for the collision
	 * @return Collidable that is the B particle
	 */
	public Collideable getB() {
		return _item_b;
	}

	/**
	 * method to get the time of the event
	 * @return double that is the time of the event
	 */
	public double getTimeOf () {
		return _timeOfEvent;
	}
	@Override
	/**
	 * Compares two Events based on their event times. Since you are implementing a maximum heap,
	 * this method assumes that the event with the smaller event time should receive higher priority.
	 */
	public int compareTo (Event e) {
		if (_timeOfEvent < e._timeOfEvent) {
			return +1;
		} else if (_timeOfEvent == e._timeOfEvent) {
			return 0;
		} else {
			return -1;
		}
	}
}
