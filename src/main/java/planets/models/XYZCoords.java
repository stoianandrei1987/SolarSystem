package planets.models;

public class XYZCoords {
    private double x;
    private double y;
    private double z;

    public XYZCoords(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "XYZCoords{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public XYZCoords minus(XYZCoords c2) {
        return new XYZCoords(this.x - c2.getX(), this.y-c2.getY(), this.z-c2.getZ());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
