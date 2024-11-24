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
		collidables.add(new Wall(true, width));
		collidables.add(new Wall(true, 0));
		collidables.add(new Wall(false, width));
		collidables.add(new Wall(false, 0));

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
			if (c instanceof Particle) {
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

	/**
	 * Executes the actual simulation.
	 */
	private void simulate (boolean show) {
		double lastTime = 0;

		// Create initial events, i.e., all the possible
		// collisions between all the particles and each other,
		// and all the particles and the walls.
		for (Collidable c : collidables) {
			// Add new events.
			if (c instanceof Particle) {
				Collidable other = findClosestCollision((Particle) c);
				if(other != null) {
					double timeOfEvent = ((Particle) c).collisionTime(other);
					events.add(new Event(timeOfEvent, lastTime, c, other));
				} else {
					System.out.println("Particle is null");
				}
			}
		}


		events.add(new TerminationEvent(duration));
		while (events.size() > 0) {
			System.out.println("Event list is " + events.size() + " long");

			Event event = events.removeFirst();
			double delta = event.getTimeOfEvent() - lastTime;

			if (event instanceof TerminationEvent) {
				updateAllParticles(delta);
				break;
			}

			// Check if event still valid; if not, then skip this event
			if(!event.isValid(lastTime)) {
				System.out.println("event not valid");
				continue;
			}

			// Since the event is valid, then pause the simulation for the right
			// amount of time, and then update the screen.
			Collidable[] currentCollidables = new Collidable[2];
			if (show) {
				currentCollidables = event.getCollidables();
				currentCollidables[0].resolveCollision(currentCollidables[1], lastTime);
				try {
					System.out.println(delta);
					Thread.sleep((long) delta * 10);
				} catch (InterruptedException ie) {}
			}

			// Update positions of all particles
			updateAllParticles(delta);

			// Update the velocity of the particle(s) involved in the collision
			// (either for a particle-wall collision or a particle-particle collision).
			// You should call the Particle.updateAfterCollision method at some point.

			// Enqueue new events for the particle(s) that were involved in this event.
			if(currentCollidables != null) {
				enqueueNewEvents(currentCollidables[0], lastTime);
				enqueueNewEvents(currentCollidables[1], lastTime);
			}

			// Update the time of our simulation
			lastTime = event.getTimeOfEvent();

			// Redraw the screen
			if (show) {
				System.out.println("paint");
				repaint();
			}
		}

		// Print out the final state of the simulation
		System.out.println(width);
		System.out.println(duration);
		for (Collidable c : collidables) {
			if(c instanceof Particle) {
				System.out.println((Particle) c);
			}
		}
	}

	private void enqueueNewEvents(Collidable particle, double currentTime) {
		if (particle instanceof Particle) {
			Collidable closest = findClosestCollision((Particle) particle);
			if (closest != null) {
				double collisionTime = ((Particle) particle).collisionTime(closest);
				if (collisionTime > currentTime) {
					events.add(new Event(collisionTime, currentTime, particle, closest));
				}
			}
		}
	}


	/**
	 * Finds the closest Collidable that will collide with the given particle.
	 * @param particle The particle to check.
	 * @return The closest Collidable that will collide with the given particle.
	 */
	private Collidable findClosestCollision(Particle particle) {
		Collidable closestCollidable = null;
		double minTime = Double.POSITIVE_INFINITY;

		for (Collidable other : collidables) {
			if (other != particle) { // Avoid checking self-collisions
				double thisCollisionTime = particle.collisionTime(other);
				if (thisCollisionTime < minTime && thisCollisionTime > 0) {
					minTime = thisCollisionTime;
					closestCollidable = other;
				}
			}
		}
		return closestCollidable;
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
