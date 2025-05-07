package LinAlgebra;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrzypisaniaTest{

    @Test
    void testAssignScalarToScalar() {
        Scalar s = new Scalar(1.0);
        s.przypisz(new Scalar(0.5));
        assertEquals(0.5, s.daj(), 1e-10);
    }

    @Test
    void testAssignScalarToVector() {
        Vector v = new Vector(new double[]{1.0, 2.0, 3.0}, false);
        v.przypisz(new Scalar(0.5));
        assertArrayEquals(new double[]{0.5, 0.5, 0.5}, v.getValues(), 1e-10);
    }

    @Test
    void testAssignScalarToMatrix() {
        Matrix m = new Matrix(new double[][]{
            {1.0, 2.0},
            {-3.0, -4.0},
            {5.0, -6.0}
        });
        m.przypisz(new Scalar(0.5));
        assert2dArrayEquals(new double[][]{
            {0.5, 0.5},
            {0.5, 0.5},
            {0.5, 0.5}
        }, m.getValues(), 1e-10);
    }

    @Test
    void testAssignVectorToVector() {
        Vector v1 = new Vector(new double[]{-1.0, 0.0, 1.0}, true);
        Vector v2 = new Vector(new double[]{-1.0, 0.0, 1.0}, false);
        Vector v3 = new Vector(new double[]{1.5, 2.5, 3.5}, true);
        v1.przypisz(v3);
        v2.przypisz(v3);
        assertArrayEquals(new double[]{1.5, 2.5, 3.5}, v1.getValues(), 1e-10);
        assertArrayEquals(new double[]{1.5, 2.5, 3.5}, v1.getValues(), 1e-10);
        assertEquals(true, v1.getOrientation());
    }

    @Test
    void testAssignVectorToMatrix() {
        Matrix m = new Matrix(new double[][]{
            {1.0, 2.0, -2.0},
            {-3.0, -4.0, 4.0},
            {5.0, -6.0, 6.0}
        });
        Vector v = new Vector(new double[]{1.5, 2.5, 3.5}, true);
        m.przypisz(v);
        assert2dArrayEquals(new double[][]{
            {1.5, 1.5, 1.5},
            {2.5, 2.5, 2.5},
            {3.5, 3.5, 3.5}
        }, m.getValues(), 1e-10);
    }

    @Test
    void testAssignMatrixToMatrix() {
        Matrix m1 = new Matrix(new double[][]{
            {1.0, 2.0, 3.0},
            {1.0, 3.0, 2.0}
        });
        Matrix m2 = new Matrix(new double[][]{
            {10.5, 20.5, 30.5},
            {-1.5, 0.0, 1.5}
        });
        m1.przypisz(m2);
        assert2dArrayEquals(new double[][]{
            {10.5, 20.5, 30.5},
            {-1.5, 0.0, 1.5}
        }, m1.getValues(), 1e-10);
    }

    // Utility for 2D array comparison
    private static void assert2dArrayEquals(double[][] expected, double[][] actual, double delta) {
        assertEquals(expected.length, actual.length, "Row lengths differ");
        for (int i = 0; i < expected.length; ++i) {
            assertArrayEquals(expected[i], actual[i], delta, "Row " + i + " differs");
        }
    }
}