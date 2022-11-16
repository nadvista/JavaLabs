import java.awt.geom.Rectangle2D.Double;

public class Mandelbrot extends FractalGenerator {

    private static final double INITIAL_SIZE = 3;
    private static final double INITIAL_X = -2;
    private static final double INITIAL_Y = -1.5d;
    private static final int MAX_ITERATIONS = 2000;

    @Override
    public void getInitialRange(Double range) {
        range.x = INITIAL_X;
        range.y = INITIAL_Y;
        range.width = range.height = INITIAL_SIZE;
    }

    @Override
    public int numIterations(double x, double y) {
        var c = new Complex(x, y);
        var z0 = new Complex(0, 0);

        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {

            if (z0.ModulePow2() >= 4)
                return iteration;
            z0.InSquare();
            z0.Add(c);
        }

        return -1;
    }
}
