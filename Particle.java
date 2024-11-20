import java.util.*;
import java.util.function.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.sound.sampled.*;

public class ParticleSimulator extends JPanel {
	private Heap<Event> events;
	private java.util.List<Collidable> collidables;
	private double duration;
	private int width;

	/**
	 * @param filename the name of the file to parse containing the particles
	 */
	public ParticleSimulator (String filename) throws IOException {
		events = new HeapImpl<Event>();

		// Parse the specified file and load all the particles.
		Scanner s = new Scanner(new File(filename));
		width = s.nextInt();
		duration = s.nextDouble();
		s.nextLine();
		collidables = new ArrayList<>();
		while (s.hasNext()) {
			String line = s.nextLine();
			Particle particle = Particle.build(line);
			collidables.add(particle);
		}

		setPreferredSize(new Dimension(width, width));
	}

	@Override
	/**
	 * Draws all the particles on the screen at their current locations
	 * DO NOT MODIFY THIS METHOD
	 */
        public void paintComponent (Graphics g) {
		g.clearRect(0, 0, width, width);
		for (Collidable c : collidables) {
			if(c instanceof Particle) {
				((Particle) c).draw(g);
			}
		}
	}

	// Helper class to signify the final event of the simulation.
	private class TerminationEvent extends Event {
		TerminationEvent (double timeOfEvent) {
			super(timeOfEvent, 0);
		}
	}

	/**
	 * Helper method to update the positions of all the particles based on their current velocities.
	 */
	private void updateAllParticles(double delta) {
		for (Collidable c : collidables) {
			if (c instanceof Particle) {
				((Particle) c).update(delta);
			}
		}
	}

	/*
	private void addCollisions(Particle p, double currentTime) {
		for (Collidable other : collidables) {
			if (other != p) {
				double timeToCollision = calculateCollisionTime(p, other);
				if (timeToCollision > 0) {
					events.add(new Event(currentTime + timeToCollision, currentTime, p, other));
				}
			}
		}
	}
	*/

	/**
	 * Executes the actual simulation.
	 */
	private void simulate (boolean show) {
		double lastTime = 0;

		// Create initial events, i.e., all the possible
		// collisions between all the particles and each other,
		// and all the particles and the walls.

		// TODO add events here

		/*
		for (Particle p : particles) {
			// Add new events.
			// TODO: correct this its prob wrong
			Particle next = findClosestCollision(p);
			if(next != null) {
				//System.out.println("i found a hit...");
				events.add(new Event(p.getCollisionTime(next), lastTime, p, next));
			}
			//  add an event for the nearest colision of the currently inspected particle
		}
		*/
		
		events.add(new TerminationEvent(duration));
		System.out.println(events.size() + " long");
		while (events.size() > 0) {
			System.out.println(events.size() + " long");
			Event event = events.removeFirst();
			double delta = event.getTimeOfEvent() - lastTime;

			if (event instanceof TerminationEvent) {
				System.out.println("good bye!");
				updateAllParticles(delta);
				break;
			}

			// TODO: THIS
			// Check if event still valid; if not, then skip this event
			/*
			Particle[] particles = event.getParticles();
			boolean valid = lastTime >= particles[0].getLastUpdateTime() && lastTime >= particles[1].getLastUpdateTime();
			if (!valid) {
				continue;
			}

			if (valid) {
				// Check if both particles have been updated until the event time.
				double eventTime = event.getTimeOfEvent();
				if (eventTime < lastTime) {
					continue; // Skip the event if the particles have already been updated.
				}

				// Process the event here (e.g., collision, velocity update, etc.)
				// Update particle states after collision.
			}

			COULD DO SOMETHING LIKE THIS

		 	*/

			// Since the event is valid, then pause the simulation for the right
			// amount of time, and then update the screen.
			if (valid) {
				System.out.println("should show something");
				try {
					Thread.sleep((long) 10);
				} catch (InterruptedException ie) { }
			}

			// Update positions of all particles
			updateAllParticles(delta);

			// Update the velocity of the particle(s) involved in the collision
			// (either for a particle-wall collision or a particle-particle collision).
			// You should call the Particle.updateAfterCollision method at some point.
			particles[0].resolveCollision(particles[1], lastTime);
			particles[1].resolveCollision(particles[0], lastTime);
			// Enqueue new events for the particle(s) that were involved in this event.

			System.out.println("adding");
			Particle nextA = findClosestCollision(particles[0]);
			Particle nextB = findClosestCollision(particles[1]);

			if (nextA != null) {
				events.add(new Event(particles[0].getCollisionTime(nextA), lastTime, particles[0], nextA));
			}
			if (nextB != null) {
				events.add(new Event(particles[1].getCollisionTime(nextB), lastTime, particles[1], nextB));
			}

			// Update the time of our simulation
			lastTime = event.getTimeOfEvent();

			// Redraw the screen
			if (valid) {
				System.out.println("showing");
				repaint();
			}
		}

		// Print out the final state of the simulation
		System.out.println(width);
		System.out.println(duration);
		//for (Particle p : particles) {
		//	System.out.println(p);
		//}
	}

	/**
	 * Finds the closest Collidable that will collide with the given particle.
	 * @param particle The particle to check.
	 * @return The closest Collidable that will collide with the given particle.
	 */
	private Collidable findClosestCollision(Particle particle) {
		Particle closestParticle = null;
		double minTime = Double.POSITIVE_INFINITY;

		for (Collidable other : collidables) {
			/*
			if (particle != other) {
				double collisionTime = particle.getCollisionTime(other);
				if (collisionTime < minTime && collisionTime > 0) {
					minTime = collisionTime;
					closestParticle = other;
				}
			}
			*/
		}
		boolean found = (closestParticle != null);
		//System.out.print("found");
		//System.out.println(found ? " something!!!!!!!!!!!" : " nothing");
		return closestParticle;
	}

	public static void main (String[] args) throws IOException {
		if (args.length < 1) {
			System.out.println("Usage: java ParticalSimulator <filename>");
			System.exit(1);
		}

		ParticleSimulator simulator;

		simulator = new ParticleSimulator(args[0]);
		JFrame frame = new JFrame();
		frame.setTitle("Particle Simulator");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(simulator, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		simulator.simulate(true);
	}
}
