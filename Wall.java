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
        System.out.println("a wall was hit");
        // other should be a particle
        // 2 walls cant collide, or well they shouldnt
        ((Particle) other).resolveCollision(this, time);
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
