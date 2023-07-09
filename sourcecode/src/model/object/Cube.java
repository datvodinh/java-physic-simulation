package model.Object;
public class Cube extends MainObject {
    public static final double MAX_SIZE = 1.0;
    public static final double MIN_SIZE = 0.2;

    private double size = MASS_DEFAULT;

    public Cube() throws Exception {
        super();
    }

    public Cube(double mass) throws Exception {
        super(mass);
    }

    public Cube(double mass, double size) throws Exception {
        super(mass);
        setSize(size);
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        if (size > MAX_SIZE) {
            this.size = MAX_SIZE;
        } else if (size < MIN_SIZE) {
            this.size = MIN_SIZE;
        }

    }

}
