package LinAlgebra;

import java.util.Arrays;

/**
 * Klasa reprezentująca macierz liczb rzeczywistych.
 */
public class Matrix extends LinClass {
    private double[][] values; // Wartości macierzy
    private int rows; // Liczba wierszy
    private int cols; // Liczba kolumn

    // Konstruktor tworzący nową macierz na podstawie dwuwymiarowej tablicy
    public Matrix(double[][] values) {
        if (values == null || values.length == 0 || values[0].length == 0) {
            System.err.println("Błąd logiczny: Próba utworzenia pustej macierzy");
            System.exit(30);
        }
        this.rows = values.length;
        this.cols = values[0].length;
        this.values = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            this.values[i] = Arrays.copyOf(values[i], cols);
    }

    // Zwraca wartości macierzy
    public double[][] getValues() {
        return values;
    }

    // Zwraca liczbę wierszy
    public int getRows() {
        return rows;
    }

    // Zwraca liczbę kolumn
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
        return new int[] {rows, cols};
    }

    @Override
    public LinClass kopia() {
        return new Matrix(values);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[\n");
        for (int i = 0; i < values.length; i++) {
            result.append("  ");
            for (int j = 0; j < values[i].length; j++) {
                result.append(values[i][j]);
                if (j < values[i].length - 1)
                    result.append(", ");
            }
            result.append("\n");
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public void zaneguj() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                values[i][j] = -values[i][j];
            }
        }
    }

    // Funkcja pomocnicza, przeciążenie (dodajemy skalar)
    public void dodaj(Scalar other) {
        double val = other.daj();
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j)
                values[i][j] += val;
    }

    // Funkcja pomocnicza, przeciążenie (dodajemy macierz)
    public void dodaj(Matrix other) throws DimensionException {
        if (other == null || other.rows != rows || other.cols != cols) {
            throw new DimensionException("Niekomaptybilne rozmiary macierzy)");
        }
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j)
                values[i][j] += other.values[i][j];
    }

    @Override
    public void dodaj(LinClass other) throws DimensionException {
        if (other instanceof Matrix) {
            dodaj((Matrix) other);
        } else if (other instanceof Scalar) {
            dodaj((Scalar) other);
        } else {
            System.err.println("Błąd logiczny: Można dodać tylko Matrix o tym samym kształcie lub Scalar");
            System.exit(31);
        }
    }

    // Funkcja pomocnicza, przeciążenie (mnożymy przez sklar)
    public void przemnóż(Scalar other) {
        double val = other.daj();
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j)
                values[i][j] *= val;
    }

    // Funkcja pomocnicza, przeciążenie (mnożymy przez macierz)
    public void przemnóż(Matrix other) throws DimensionException {
        if (other == null || cols != other.rows) {
            throw new DimensionException("Niekompatybilne wymiary");
        }
        double[][] result = new double[rows][other.cols];
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < other.cols; ++j)
                for (int k = 0; k < cols; ++k)
                    result[i][j] += values[i][k] * other.values[k][j];
        this.values = result;
        this.cols = other.cols;
    }

    @Override
    public void przemnóż(LinClass other) throws DimensionException {
        if (other instanceof Scalar) {
            przemnóż((Scalar) other);
        } else if (other instanceof Matrix) {
            przemnóż((Matrix) other);
        } else {
            System.err.println("Błąd logiczny: Można mnożyć tylko przez Scalar lub Matrix o odpowiednich wymiarach");
            System.exit(32);
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

    // Funkcje pomocnicza, przeciążenie (dodajemy macierz i skalar)
    public Matrix suma(Scalar other) {
        Matrix copy = (Matrix) this.kopia();
        copy.dodaj(other);
        return copy;
    }

    // Funkcje pomocnicza, przeciążenie (dodajemy dwie macierze)
    public Matrix suma(Matrix other) throws DimensionException {
        Matrix copy = (Matrix) this.kopia();
        copy.dodaj(other);
        return copy;
    }

    @Override
    public LinClass suma(LinClass other) throws DimensionException {
        if (other instanceof Matrix) {
            return suma((Matrix) other);
        } else if (other instanceof Scalar) {
            return suma((Scalar) other);
        }
        throw new DimensionException("Suma: Można dodać tylko Matrix o tym samym kształcie lub Scalar");
    }

    // Funkcje pomocnicza, przeciążenie (mnożymy macierz i wektor)
    public Matrix iloczyn(Scalar other) {
        Matrix copy = (Matrix) this.kopia();
        copy.przemnóż(other);
        return copy;
    }

    // Funkcje pomocnicza, przeciążenie (mnożymy dwie macierze)
    public Matrix iloczyn(Matrix other) throws DimensionException {
        Matrix copy = (Matrix) this.kopia();
        copy.przemnóż(other);
        return copy;
    }

    // Funkcje pomocnicza, przeciążenie (mnożymy macierz przez wektor)
    public Vector iloczyn(Vector other) throws DimensionException {
        if (cols != other.kształt()[0]) {
            throw new DimensionException("Niekompatybilne wymiary przy mnożeniu");
        }
        double[] result = new double[rows];
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j)
                result[i] += values[i][j] * other.getValues()[j];
        return new Vector(result, true);
    }

    @Override
    public LinClass iloczyn(LinClass other) throws DimensionException {
        if (other instanceof Matrix) {
            return iloczyn((Matrix) other);
        } else if (other instanceof Scalar) {
            return iloczyn((Scalar) other);
        } else if (other instanceof Vector) {
            return iloczyn((Vector) other);
        }
        throw new DimensionException("Można pomnożyć tylko przez Scalar, Matrix lub Vector o odpowiednich wymiarach");
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
    public void ustaw(double value, int... indices) {
        if (indices.length != 2) {
            System.err.println("Błąd logiczny: Macierz wymaga dwóch indeksów");
            System.exit(33);
        }
        if (indices[0] < 0 || indices[0] >= rows || indices[1] < 0 || indices[1] >= cols) {
            System.err.println("Błąd logiczny: Indeksy poza zakresem w Matrix (ustaw)");
            System.exit(34);
        }
        values[indices[0]][indices[1]] = value;
    }

    @Override
    public double daj(int... indices) {
        if (indices.length != 2) {
            System.err.println("Błąd logiczny: Macierz wymaga dwóch indeksów (daj)");
            System.exit(35);
        }
        if (indices[0] < 0 || indices[0] >= rows || indices[1] < 0 || indices[1] >= cols) {
            System.err.println("Błąd logiczny: Indeksy poza zakresem w Matrix (daj)");
            System.exit(36);
        }
        return values[indices[0]][indices[1]];
    }

    // Funkcja pomocnicza, przeciążenie (przypisujemy skalar)
    public void przypisz(Scalar other) {
        double val = other.daj();
        for (int i = 0; i < rows; ++i)
            Arrays.fill(values[i], val);
    }

    // Funkcja pomocniczna, przeciążenie (przypisujemy macierz)
    public void przypisz(Matrix other) throws DimensionException {
        if (other == null || other.rows != rows || other.cols != cols) {
            throw new DimensionException("Niekompatybilne rozmiary przy przypisywaniu");
        }
        double[][] mVals = other.getValues();
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j)
                values[i][j] = mVals[i][j];
    }

    // Funkcja pomocnicza, przeciążenie (przypisujemy wektor)
    public void przypisz(Vector v) throws DimensionException {
        double[] vVals = v.getValues();
        if (v.getOrientation()) { // kolumnowy
            if (vVals.length != rows) {
                throw new DimensionException("Długość wektora kolumnowego musi odpowiadać liczbie wierszy macierzy");
            }
            for (int i = 0; i < rows; ++i)
                for (int j = 0; j < cols; ++j)
                    values[i][j] = vVals[i];
        } else { // wierszowy
            if (vVals.length != cols) {
                throw new DimensionException("Długość wektora wierszowego musi odpowiadać liczbie kolumn macierzy");
            }
            for (int i = 0; i < rows; ++i)
                for (int j = 0; j < cols; ++j)
                    values[i][j] = vVals[j];
        }
    }

    @Override
    public void przypisz(LinClass other) throws DimensionException {
        if (other instanceof Matrix) {
            przypisz((Matrix) other);
        } else if (other instanceof Scalar) {
            przypisz((Scalar) other);
        } else if (other instanceof Vector) {
            przypisz((Vector) other);
        } else {
            System.err.println("Błąd logiczny: Można przypisać tylko Matrix, Scalar lub Vector do Macierzy");
            System.exit(37);
        }
    }

    @Override
    public LinClass wycinek(int[] rowRange, int[] colRange) {
        if (rowRange == null || rowRange.length != 2 || colRange == null || colRange.length != 2) {
            System.err.println("Błąd logiczny: wycinek Macierz wymaga dwóch zakresów (wiersze, kolumny)");
            System.exit(38);
        }
        int r1 = rowRange[0], r2 = rowRange[1], c1 = colRange[0], c2 = colRange[1];
        if (r1 < 0 || r2 >= rows || r1 > r2 || c1 < 0 || c2 >= cols || c1 > c2) {
            System.err.println("Błąd logiczny: Nieprawidłowy zakres wycinka macierzy");
            System.exit(39);
        }
        double[][] res = new double[r2 - r1 + 1][c2 - c1 + 1];
        for (int i = r1; i <= r2; i++)
            for (int j = c1; j <= c2; j++)
                res[i - r1][j - c1] = values[i][j];
        return new Matrix(res);
    }
}