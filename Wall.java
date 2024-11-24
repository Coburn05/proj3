public class Wall implements Collidable {
    private boolean isVertical;
    private double position; // Position along the perpendicular axis

    public Wall(boolean orientation, double position) {
        this.isVertical = orientation;
        this.position = position;
    }

    public boolean getIsVertical() {
        return isVertical;
    }

    @Override
    public void resolveCollision(Collidable other, double time) {
        if (other instanceof Particle) {
            System.out.println("Wall was hit by a particle");
            ((Particle) other).resolveCollision(this, time);
        } else {
            // Handle collision with other types (e.g., other walls)
            System.out.println("Wall collision with a non-particle.");
        }
    }


    @Override
    public double[] getPosition() {
        return isVertical ? new double[]{position, 0} : new double[]{0, position};
    }

    @Override
    public double[] getVelocity() {
        // Walls are static
        return new double[]{0, 0};
    }
}
