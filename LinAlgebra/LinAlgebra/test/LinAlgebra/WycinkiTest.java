package LinAlgebra;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WycinkiTest {

    @Test
    void testSliceScalar() throws DimensionException {
        Scalar s = new Scalar(13.125);
        Scalar slice = (Scalar) s.wycinek(null, null);
        assertEquals(13.125, slice.daj(), 1e-10);
    }

    @Test
    void testSliceVector() throws DimensionException {
        Vector v = new Vector(new double[]{1.0, 21.0, 32.0, 43.0, 54.0}, false);
        Vector slice = (Vector) v.wycinek(new int[]{2, 3}, null);
        assertArrayEquals(new double[]{32.0, 43.0}, slice.getValues(), 1e-10);
    }

    @Test
    void testSliceMatrix() throws DimensionException {
        Matrix m = new Matrix(new double[][]{
            {7.0, -21.0, 15.0, -31.0, 25.0},
            {-21.0, 15.0, -31.0, 25.0, 7.0},
            {15.0, -31.0, 25.0, -7.0, -21.0},
            {-31.0, 25.0, 7.0, -21.0, 15.0}
        });

        Matrix slice = (Matrix) m.wycinek(new int[]{1, 3}, new int[]{1, 2});
        assert2dArrayEquals(new double[][]{
            {15.0, -31.0},
            {-31.0, 25.0},
            {25.0, 7.0}
        }, slice.getValues(), 1e-10);
    }

    // Utility for 2D array comparison
    private static void assert2dArrayEquals(double[][] expected, double[][] actual, double delta) {
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; ++i) {
            assertArrayEquals(expected[i], actual[i], delta);
        }
    }
}