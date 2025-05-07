package LinAlgebra;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArytmetykaTest {
    @Test
    void testScalarAddition() {
        Scalar a = new Scalar(3.5);
        Scalar b = new Scalar(11.5);
        Scalar result = (Scalar) a.suma(b);
        assertEquals(15.0, result.daj(), 1e-10);
    }

    @Test
    void testScalarMultiplication() {
        Scalar a = new Scalar(3.0);
        Scalar b = new Scalar(12.0);
        Scalar result = (Scalar) a.iloczyn(b);
        assertEquals(36.0, result.daj(), 1e-10);
    }

    @Test
    void testScalarVectorAddition() {
        Scalar a = new Scalar(3.0);
        Vector v = new Vector(new double[]{1.0, 2.5}, false);
        Vector result = (Vector) a.suma(v);
        assertArrayEquals(new double[]{4.0, 5.5}, result.getValues(), 1e-10);
    }

    @Test
    void testScalarVectorMultiplication() {
        Scalar a = new Scalar(4.0);
        Vector v = new Vector(new double[]{1.5, 2.25}, false);
        Vector result = (Vector) a.iloczyn(v);
        assertArrayEquals(new double[]{6.0, 9.0}, result.getValues(), 1e-10);
    }

    @Test
    void testVectorScalarAddition() {
        Vector v = new Vector(new double[]{1.0, 2.5}, true);
        Scalar a = new Scalar(3.0);
        Vector result = (Vector) v.suma(a);
        assertArrayEquals(new double[]{4.0, 5.5}, result.getValues(), 1e-10);
    }

    @Test
    void testVectorScalarMultiplication() {
        Vector v = new Vector(new double[]{1.5, 2.25}, true);
        Scalar a = new Scalar(4.0);
        Vector result = (Vector) v.iloczyn(a);
        assertArrayEquals(new double[]{6.0, 9.0}, result.getValues(), 1e-10);
    }

    @Test
    void testScalarMatrixAddition() {
        Scalar a = new Scalar(3.0);
        Matrix m = new Matrix(new double[][]{
            {1.25, 3.0, -12.0},
            {-51.0, 8.0, 3.5}
        });
        Matrix result = (Matrix) a.suma(m);
        assertArrayEquals(new double[][]{
            {4.25, 6.0, -9.0},
            {-48.0, 11.0, 6.5}
        }, result.getValues());
    }

    @Test
    void testScalarMatrixMultiplication() {
        Scalar a = new Scalar(-3.0);
        Matrix m = new Matrix(new double[][]{
            {1.25, 3.0, -12.0},
            {-51.0, 8.0, 3.5}
        });
        Matrix result = (Matrix) a.iloczyn(m);
        assertArrayEquals(new double[][]{
            {-3.75, -9.0, 36.0},
            {153.0, -24.0, -10.5}
        }, result.getValues());
    }

    @Test
    void testVectorAddition1() {
        Vector v1 = new Vector(new double[]{1.0, 2.0, 3.0}, false);
        Vector v2 = new Vector(new double[]{1.0, 1.0, -2.0}, false);
        Vector sum = (Vector) v1.suma(v2);
        assertArrayEquals(new double[]{2.0, 3.0, 1.0}, sum.getValues(), 1e-10);
        assertFalse(sum.getOrientation());
    }

    @Test
    void testVectorAddition2() {
        Vector v1 = new Vector(new double[]{-2.0, 5.0}, true);
        Vector v2 = new Vector(new double[]{-5.0, 2.0}, true);
        Vector sum = (Vector) v1.suma(v2);
        assertArrayEquals(new double[]{-7.0, 7.0}, sum.getValues(), 1e-10);
        assertTrue(sum.getOrientation());
    }

    @Test
    void testVectorDotProduct1() {
        Vector v1 = new Vector(new double[]{3.0, 2.0, -1.0}, true);
        Vector v2 = new Vector(new double[]{-2.0, 2.0, 1.0}, true);
        Scalar result = (Scalar) v1.iloczyn(v2);
        assertEquals(-3.0, result.daj(), 1e-10);
    }

    @Test
    void testVectorDotProduct2() {
        Vector v1 = new Vector(new double[]{-2.0, -5.0, 1.0, 3.0}, false);
        Vector v2 = new Vector(new double[]{-5.0, 1.0, 2.0, -3.0}, false);
        Scalar result = (Scalar) v1.iloczyn(v2);
        assertEquals(-2.0, result.daj(), 1e-10);
    }

    @Test
    void testRowTimesColumnToMatrix() {
        Vector v1 = new Vector(new double[]{1.0, 2.0, 3.0}, false); // row
        Vector v2 = new Vector(new double[]{1.0, 1.0, -2.0}, true); // column
        Matrix result = (Matrix) v1.iloczyn(v2);
        double[][] expected = {
            {-3.0}
        };
        assert2dArrayEquals(expected, result.getValues(), 1e-10);
    }

    @Test
    void testColumnTimesRowFullMatrix() {
        Vector v1 = new Vector(new double[]{1.0, 2.0, 3.0}, true); // column
        Vector v2 = new Vector(new double[]{1.0, 1.0, -2.0}, false); // row
        Matrix result = (Matrix) v1.iloczyn(v2);
        double[][] expected = {
            {1.0, 1.0, -2.0},
            {2.0, 2.0, -4.0},
            {3.0, 3.0, -6.0}
        };
        assert2dArrayEquals(expected, result.getValues(), 1e-10);
    }

    @Test
    void testMatrixVectorMultiplication() {
        Matrix m = new Matrix(new double[][] {
            {1.0, 2.0},
            {3.0, -2.0},
            {2.0, 1.0}
        });
        Vector v = new Vector(new double[]{-1.0, 3.0}, true);

        Vector expected = new Vector(new double[]{5.0, -9.0, 1.0}, true);
        Vector result = (Vector) m.iloczyn(v);

        assertArrayEquals(expected.getValues(), result.getValues(), 1e-10);
        assertEquals(expected.getOrientation(), result.getOrientation());
    }

    @Test
    void testVectorMatrixMultiplication() {
        Vector v = new Vector(new double[]{1.0, -1.0, 2.0}, false);
        Matrix m = new Matrix(new double[][] {
            {1.0, 2.0},
            {3.0, -2.0},
            {2.0, 1.0}
        });

        Vector expected = new Vector(new double[]{2.0, 6.0}, false);
        Vector result = (Vector) v.iloczyn(m);

        assertArrayEquals(expected.getValues(), result.getValues(), 1e-10);
    }

    @Test
    void testMatrixMatrixAddition() {
        Matrix m1 = new Matrix(new double[][]{
            {1.0, -2.0, 3.0},
            {2.0, 1.0, -1.0}
        });
        Matrix m2 = new Matrix(new double[][]{
            {3.0, -1.0, 2.0},
            {1.0, 1.0, -2.0}
        });
        Matrix expected = new Matrix(new double[][]{
            {4.0, -3.0, 5.0},
            {3.0, 2.0, -3.0}
        });
        Matrix result = (Matrix) m1.suma(m2);

        assert2dArrayEquals(expected.getValues(), result.getValues(), 1e-10);
    }

    @Test
    void testMatrixMatrixMultiplication() {
        Matrix m1 = new Matrix(new double[][]{
            {2.0, 0.5},
            {1.0, -2.0},
            {-1.0, 3.0}
        });
        Matrix m2 = new Matrix(new double[][]{
            {2.0, -1.0, 5.0},
            {-3.0, 2.0, -1.0}
        });
        Matrix expected = new Matrix(new double[][]{
            {2.5, -1.0, 9.5},
            {8.0, -5.0, 7.0},
            {-11.0, 7.0, -8.0}
        });
        Matrix result = (Matrix) m1.iloczyn(m2);

        assert2dArrayEquals(expected.getValues(), result.getValues(), 1e-10);
    }

    @Test
    void testNegateScalar() {
        Scalar s = new Scalar(17.0);
        Scalar expected = new Scalar(-17.0);
        Scalar result = (Scalar) s.negacja();

        assertEquals(expected.daj(), result.daj(), 1e-10);
    }

    @Test
    void testNegateVector() {
        Vector v = new Vector(new double[]{10.0, -45.0, 0.0, 29.0, -3.0}, true);
        Vector expected = new Vector(new double[]{-10.0, 45.0, -0.0, -29.0, 3.0}, true);
        Vector result = (Vector) v.negacja();

        assertArrayEquals(expected.getValues(), result.getValues(), 1e-10);
    }

    @Test
    void testNegateMatrix() {
        Matrix m = new Matrix(new double[][]{
            {0.0, 0.5, -1.25},
            {11.0, -71.0, -33.5},
            {-2, -1.75, -99.0}
        });
        Matrix expected = new Matrix(new double[][]{
            {0.0, -0.5, 1.25},
            {-11.0, 71.0, 33.5},
            {2, 1.75, 99.0}
        });
        Matrix result = (Matrix) m.negacja();

        assert2dArrayEquals(expected.getValues(), result.getValues(), 1e-10);
    }

    // Utility function for 2D array comparison with tolerance
    private static void assert2dArrayEquals(double[][] expected, double[][] actual, double delta) {
        assertEquals(expected.length, actual.length, "Row counts differ");
        for (int i = 0; i < expected.length; ++i) {
            assertArrayEquals(expected[i], actual[i], delta, "Row " + i + " differs");
        }
    }
}