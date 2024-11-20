public class Wall implements Collidable {
    public enum Orientation { VERTICAL, HORIZONTAL }
    private Orientation orientation;
    private double position; // Position along the perpendicular axis

    public Wall(Orientation orientation, double position) {
        this.orientation = orientation;
        this.position = position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public void resolveCollision(Collidable other, double time) {
        System.out.println("a wall was hit");
        // Walls don't change state during collisions
    }

    @Override
    public double[] getPosition() {
        return orientation == Orientation.VERTICAL ? new double[]{position, 0} : new double[]{0, position};
    }

    @Override
    public double[] getVelocity() {
        // Walls are static
        return new double[]{0, 0};
    }
}
