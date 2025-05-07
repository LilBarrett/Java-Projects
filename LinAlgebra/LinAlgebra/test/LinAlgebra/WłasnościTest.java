package LinAlgebra;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WłasnościTest {
    @Test
    void testScalarProperties() {
        Scalar a = new Scalar(1.0);
        // Powinno być wymiar() == 0, kształt() == [], liczba_elementów() == 1
        assertEquals(0, a.wymiar(), 1e-10);
        assertArrayEquals(new int[]{}, a.kształt());
        assertEquals(1, a.liczba_elementów(), 1e-10);
    }

    @Test
    void testVectorProperties() {
        Vector v1 = new Vector(new double[]{1.0, 2.0, 1.0}, true);
        Vector v2 = new Vector(new double[]{2.0, 2.0, 3.0}, false);

        // Dla obu powinno być wymiar() == 1, kształt() == [3], liczba_elementów() == 3
        assertEquals(1, v1.wymiar());
        assertArrayEquals(new int[]{3}, v1.kształt());
        assertEquals(3, v1.liczba_elementów());

        assertEquals(1, v2.wymiar());
        assertArrayEquals(new int[]{3}, v2.kształt());
        assertEquals(3, v2.liczba_elementów());
    }

    @Test
    void testMatrixProperties() {
        Matrix m = new Matrix(new double[][]{
            {1.0, 0.0, 2.0},
            {2.0, 1.0, 3.0},
            {1.0, 1.0, 1.0},
            {2.0, 3.0, 1.0}
        });

        // Powinno być wymiar() == 2, kształt() == [4, 3], liczba_elementów() == 12
        assertEquals(2, m.wymiar());
        assertArrayEquals(new int[]{4, 3}, m.kształt());
        assertEquals(12, m.liczba_elementów());
    }
}