public interface Collideable {
    public double getCollisionTime(Collideable other);
    public void updateAfterCollision (double now, Collideable other);
    public void update(double delta);
    public double getVX();
    public double getVY();
    public double getX();
    public double getY();
    public boolean lastUpdateTimeMatch(double lastTime);
}
