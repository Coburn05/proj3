public interface Collidable {
    void resolveCollision(Collidable other, double time);

    double[] getPosition();
    double[] getVelocity();
    default double getX() { return getPosition()[0]; }
    default double getY() { return getPosition()[1]; }
    default double getVX() { return getVelocity()[0]; }
    default double getVY() { return getVelocity()[1]; }
}
