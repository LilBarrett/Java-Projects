package LinAlgebra;

import java.util.Arrays;

public class Matrix extends LinClass {
    private double[][] values;
    private int rows;
    private int cols;

    public Matrix(double[][] values) {
        this.rows = values.length;
        this.cols = values[0].length;
        this.values = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            this.values[i] = Arrays.copyOf(values[i], cols);
    }

    public double[][] getValues() {
        return values;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    @Override
    public int wymiar() {
        return 2;
    }

    @Override
    public int liczba_elementów() {
        return rows * cols;
    }

    @Override
    public int[] kształt() {
        return new int[] {
            rows,
            cols
        };
    }

    @Override
    public LinClass kopia() {
        return new Matrix(values);
    }

    @Override
    public String toString() {
        String result = "[\n";
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                result += values[i][j];
                if (j < values[i].length - 1)
                    result += ", ";
            }
            result += "\n";
        }
        result += "]";
        return result;
    }

    @Override
    public void zaneguj() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                values[i][j] = -values[i][j];
            }
        }
    }

    @Override
    public void dodaj(LinClass other) {
        if (other instanceof Matrix) {
            Matrix m = (Matrix) other;
            if (m.rows != rows || m.cols != cols)
                throw new IllegalArgumentException("Matrix size mismatch for addition.");
            for (int i = 0; i < rows; ++i)
                for (int j = 0; j < cols; ++j)
                    values[i][j] += m.values[i][j];
        } else if (other instanceof Scalar) {
            double val = ((Scalar) other).daj();
            for (int i = 0; i < rows; ++i)
                for (int j = 0; j < cols; ++j)
                    values[i][j] += val;
        } else {
            throw new IllegalArgumentException("Can only add matrix of same shape or scalar.");
        }
    }

    @Override
    public void przemnóż(LinClass other) {
        if (other instanceof Scalar) {
            double val = ((Scalar) other).daj();
            for (int i = 0; i < rows; ++i)
                for (int j = 0; j < cols; ++j)
                    values[i][j] *= val;
        } else if (other instanceof Matrix) {
            Matrix m = (Matrix) other;
            if (cols != m.rows)
                throw new IllegalArgumentException("Matrix size mismatch for multiplication.");
            double[][] result = new double[rows][m.cols];
            for (int i = 0; i < rows; ++i)
                for (int j = 0; j < m.cols; ++j)
                    for (int k = 0; k < cols; ++k)
                        result[i][j] += values[i][k] * m.values[k][j];
            this.values = result;
            this.cols = m.cols;
        } else {
            throw new IllegalArgumentException("Can only multiply by scalar or a matrix with compatible dimensions.");
        }
    }

    @Override
    public LinClass negacja() {
        double[][] neg = new double[rows][cols];
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j)
                neg[i][j] = -values[i][j];
        return new Matrix(neg);
    }

    @Override
    public LinClass suma(LinClass other) {
        Matrix copy = (Matrix) this.kopia();
        copy.dodaj(other);
        return copy;
    }

    @Override
    public LinClass iloczyn(LinClass other) {
        Matrix copy = (Matrix) this.kopia();
        if (other instanceof Matrix || other instanceof Scalar) {
            copy.przemnóż(other);
            return copy;
        } else if (other instanceof Vector) {
            if (cols != ((Vector) other).kształt()[0])
                throw new IllegalArgumentException("Size mismatch for multiplication.");
            double[] result = new double[rows];
            for (int i = 0; i < rows; ++i)
                for (int j = 0; j < cols; ++j)
                    result[i] += values[i][j] * ((Vector) other).getValues()[j];
            Vector resVector = new Vector(result, true);
            return resVector;
        } else {
            throw new IllegalArgumentException("Can only multiply by other LinClass elements with compatible dimensions.");
        }
    }

    @Override
    public void transponuj() {
        double[][] trans = new double[cols][rows];
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j)
                trans[j][i] = values[i][j];
        this.values = trans;
        int tmp = rows;
        this.rows = cols;
        this.cols = tmp;
    }

    @Override
    public void ustaw(double value, int...indices) {
        if (indices.length != 2)
            throw new IllegalArgumentException("Matrix requires 2 indices");
        values[indices[0]][indices[1]] = value;
    }

    @Override
    public double daj(int...indices) {
        if (indices.length != 2)
            throw new IllegalArgumentException("Matrix requires 2 indices");
        return values[indices[0]][indices[1]];
    }

    @Override
    public void przypisz(LinClass other) {
        if (other instanceof Matrix) {
            Matrix m = (Matrix) other;
            if (m.getRows() != rows || m.getCols() != cols)
                throw new IllegalArgumentException("Matrix size mismatch for assignment.");
            double[][] mVals = m.getValues();
            for (int i = 0; i < rows; ++i)
                for (int j = 0; j < cols; ++j)
                    values[i][j] = mVals[i][j];
        } else if (other instanceof Scalar) {
            double val = ((Scalar) other).daj();
            for (int i = 0; i < rows; ++i)
                Arrays.fill(values[i], val);
        } else if (other instanceof Vector) {
            Vector v = (Vector) other;
            double[] vVals = v.getValues();
            if (v.getOrientation()) { // vertical => column vector
                if (vVals.length != rows)
                    throw new IllegalArgumentException("Długość wektora (kolumnowego) musi odpowiadać liczbie wierszy macierzy.");
                for (int i = 0; i < rows; ++i)
                    for (int j = 0; j < cols; ++j)
                        values[i][j] = vVals[i];
            } else { // horizontal => row vector
                if (vVals.length != cols)
                    throw new IllegalArgumentException("Długość wektora (wierszowego) musi odpowiadać liczbie kolumn macierzy.");
                for (int i = 0; i < rows; ++i)
                    for (int j = 0; j < cols; ++j)
                        values[i][j] = vVals[j];
            }
        } else {
            throw new IllegalArgumentException("Can only assign from matrix of same shape, vector, or scalar.");
        }
    }

    @Override
    public LinClass wycinek(int[] rowRange, int[] colRange) {
        int r1 = rowRange[0], r2 = rowRange[1], c1 = colRange[0], c2 = colRange[1];
        if (r1 < 0 || r2 >= rows || r1 >= r2 || c1 < 0 || c2 >= cols || c1 >= c2)
            throw new IllegalArgumentException("Invalid slice range.");
        double[][] res = new double[r2 - r1 + 1][c2 - c1 + 1];
        for (int i = r1; i <= r2; i++)
            for (int j = c1; j <= c2; j++)
                res[i - r1][j - c1] = values[i][j];
        return new Matrix(res);
    }
}