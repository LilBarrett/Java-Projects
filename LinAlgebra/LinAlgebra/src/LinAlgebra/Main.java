package LinAlgebra;

public class Main {
    public static void main(String[] args) {
        // Example usage for Vector
        Vector v1 = new Vector(new double[]{1, 2, 3}, false);
        Vector v2 = new Vector(new double[]{4, 5, 6}, false);
        System.out.println(v1.toString()+" "+v2.toString());

        v1.dodaj(new Scalar(10));
        System.out.println("v1 after adding scalar 10: " + v1);

        v2.zaneguj();
        System.out.println("v2 after negation: " + v2);

        // Example usage for Matrix
        double[][] arr = { {1, 2}, {3, 4} };
        Matrix m1 = new Matrix(arr);
        System.out.println("m1:\n" + m1);

        m1.zaneguj();
        System.out.println("m1 after negation:\n" + m1);

        // Example matrix addition
        Matrix m2 = new Matrix(new double[][]{{5, 6}, {7, 8}});
        m1.dodaj(m2);
        System.out.println("m1 after adding m2:\n" + m1);

        // Example matrix * scalar
        m1.przemnóż(new Scalar(2));
        System.out.println("m1 after multiplying by 2:\n" + m1);
    }
}