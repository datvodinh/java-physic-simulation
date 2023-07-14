package model.object;

public class Cube extends MainObject {
    public static final double MAX_SIZE = 1.0; // Maximum size of the cube
    public static final double MIN_SIZE = 0.2; // Minimum size of the cube

    private double size = MASS_DEFAULT; // Size of the cube

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

    // Get the size of the cube
    public double getSize() {
        return size;
    }

    // Set the size of the cube, ensuring it stays within the valid range
    public void setSize(double size) {
        if (size > MAX_SIZE) {
            this.size = MAX_SIZE;
        } else if (size < MIN_SIZE) {
            this.size = MIN_SIZE;
        } else {
            this.size = size;
        }
    }
}
