package LinAlgebra;

import java.util.Arrays;

/**
 * Klasa reprezentująca wektor (poziomy lub pionowy) liczb rzeczywistych.
 */
public class Vector extends LinClass {
    private double[] values; // Wartości wektora
    private boolean vertical; // Pionowy (true) czy poziomy (false)

    // Konstruktor wektora na podstawie tablicy danych i określenia orientacji
    public Vector(double[] values, boolean vertical) {
        if (values == null || values.length == 0) {
            System.err.println("Błąd logiczny: Próba utworzenia pustego wektora");
            System.exit(10);
        }
        this.values = Arrays.copyOf(values, values.length);
        this.vertical = vertical;
    }

    // Zwraca tablicę wartości wektora
    public double[] getValues() {
        return values;
    }

    // Zwraca orientacje wektora (pozioma, pionowa)
    public boolean getOrientation() {
        return vertical;
    }

    @Override
    public int wymiar() {
        return 1;
    }

    @Override
    public int liczba_elementów() {
        return values.length;
    }

    @Override
    public int[] kształt() {
        return new int[]{values.length};
    }

    @Override
    public LinClass kopia() {
        return new Vector(this.values, this.vertical);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(vertical ? "Wektor pionowy: [ " : "Wektor poziomy:\n[");
        if (vertical) {
            for (int i = 0; i < values.length; i++) {
                sb.append(values[i]);
                if (i < values.length - 1) sb.append(", ");
            }
            sb.append(" ]");
        } else {
            for (double v : values) {
                sb.append("  ").append(v).append("\n");
            }
            sb.append("]");
        }
        return sb.toString();
    }

    @Override
    public void zaneguj() {
        for (int i = 0; i < values.length; ++i) {
            values[i] = -values[i];
        }
    }

    // Funkcja pomocnicza, przeciążenie (dodajemy skalar)
    public void dodaj(Scalar other) {
        for (int i = 0; i < values.length; ++i)
            this.values[i] += other.daj();
    }

    // Funkcja pomocnicza, przeciążenie (dodajemy wektor)
    public void dodaj(Vector other) throws DimensionException {
        if (other == null || this.values.length != other.values.length || this.vertical != other.vertical) {
            throw new DimensionException("Niekompatybilne wymiary lub orientacje wektorów przy dodawaniu!");
        }
        for (int i = 0; i < values.length; ++i)
            this.values[i] += other.daj(i);
    }

    @Override
    public void dodaj(LinClass other) {
        try {
            if (other instanceof Scalar) {
                dodaj((Scalar) other);
            } else if (other instanceof Vector) {
                dodaj((Vector) other);
            } else {
                throw new DimensionException("Do wektora można dodać (inplace) tylko Scalar lub Vector");
            }
        } catch (DimensionException e) {
            System.err.println("Błąd logiczny w dodaj: " + e.getMessage());
            System.exit(11);
        }
    }

    // Funkcja pomocnicza, przeciążenie (mnożymy przez skalar)
    public void przemnóż(Scalar other) {
        for (int i = 0; i < values.length; ++i)
            this.values[i] *= other.daj();
    }

    @Override
    public void przemnóż(LinClass other) {
        try {
            if (other instanceof Scalar) {
                przemnóż((Scalar) other);
            } else {
                throw new DimensionException("Można mnożyć (inplace) wektor tylko przez Scalar");
            }
        } catch (DimensionException e) {
            System.err.println("Błąd logiczny w przemnóż: " + e.getMessage());
            System.exit(12);
        }
    }

    @Override
    public LinClass negacja() {
        double[] negValues = new double[values.length];
        for (int i = 0; i < values.length; ++i) {
            negValues[i] = -values[i];
        }
        return new Vector(negValues, vertical);
    }

    // Funkcja pomocnicza, przeciążenie (dodajemy skalar)
    public Vector suma(Scalar other) {
        double[] newValues = new double[values.length];
        for (int i = 0; i < values.length; ++i)
            newValues[i] = values[i] + other.daj();
        return new Vector(newValues, vertical);
    }

    // Funkcja pomocnicza, przeciążenie (dodajemy wektor)
    public Vector suma(Vector other) throws DimensionException {
        if (other == null || this.values.length != other.values.length || this.vertical != other.vertical) {
            throw new DimensionException("Niekompatybilne wymiary lub orientacje wektorów!");
        }
        double[] newValues = new double[values.length];
        for (int i = 0; i < values.length; ++i)
            newValues[i] = values[i] + other.daj(i);
        return new Vector(newValues, vertical);
    }

    @Override
    public LinClass suma(LinClass other) {
        try {
            if (other instanceof Scalar) {
                return suma((Scalar) other);
            } else if (other instanceof Vector) {
                return suma((Vector) other);
            } else {
                throw new DimensionException("Można sumować tylko z wektorem lub skalarem");
            }
        } catch (DimensionException e) {
            System.err.println("Błąd logiczny w suma: " + e.getMessage());
            System.exit(13);
            return null;
        }
    }

    // Funkcja pomocnicza, przeciążenie (mnożymy przez skalar)
    public LinClass iloczyn(Scalar other) {
        double[] newValues = new double[values.length];
        for (int i = 0; i < values.length; ++i)
            newValues[i] = values[i] * other.daj();
        return new Vector(newValues, vertical);
    }

    public LinClass iloczyn(Vector v) throws DimensionException {
        if (v == null || values.length != v.values.length)
            throw new DimensionException("Niekompatybilne wymiary wektorów!");
        boolean thisCol = this.getOrientation();
        boolean otherCol = v.getOrientation();
        if (thisCol == otherCol) {
            // Iloczyn skalarny
            double result = 0;
            for (int i = 0; i < values.length; ++i)
                result += this.values[i] * v.values[i];
            return new Scalar(result);
        } else {
            // Iloczyn zewnętrzny
            double[][] data = new double[thisCol ? values.length : 1][otherCol ? 1 : v.values.length];
            if (thisCol) {
                for (int i = 0; i < values.length; ++i)
                    for (int j = 0; j < v.values.length; ++j)
                        data[i][j] = this.values[i] * v.values[j];
            } else {
                double sum = 0;
                for (int i = 0; i < values.length; ++i)
                    sum += this.values[i] * v.values[i];
                data[0][0] = sum;
            }
            return new Matrix(data);
        }
    }

    // Funkcja pomocnicza przeciążenie (mnożymy przez macierz)
    public LinClass iloczyn(Matrix m) throws DimensionException {
        double[][] matrixData = m.getValues();
        if (!vertical) {
            if (values.length != matrixData.length) 
                throw new DimensionException("Niekompatybilne wymiary wektora wierszowego i macierzy!");
            double[] result = new double[matrixData[0].length];
            for (int i = 0; i < matrixData[0].length; i++) {
                for (int j = 0; j < values.length; j++) {
                    result[i] += this.values[j] * matrixData[j][i];
                }
            }
            return new Vector(result, false);
        } else {
            throw new DimensionException("Nie zaimplementowano iloczynu: wektor kolumnowy razy macierz");
        }
    }
    
    @Override
    public LinClass iloczyn(LinClass other) {
        try {
            if (other instanceof Scalar) {
                return iloczyn((Scalar) other);
            } else if (other instanceof Vector) {
                return iloczyn((Vector) other);
            } else if (other instanceof Matrix) {
                return iloczyn((Matrix) other);
            } else {
                throw new DimensionException("Można pomnożyć tylko przez wektor, skalar lub macierz");
            }
        } catch (DimensionException e) {
            System.err.println("Błąd logiczny w iloczyn: " + e.getMessage());
            System.exit(14);
            return null;
        }
    }

    @Override
    public void transponuj() {
        vertical = !vertical;
    }

    @Override
    public void ustaw(double value, int... indices) {
        if (indices.length != 1) {
            System.err.println("Błąd logiczny: Wektor wymaga podania jednego indeksu!");
            System.exit(15);
        }
        if (indices[0] < 0 || indices[0] >= values.length) {
            System.err.println("Błąd logiczny: Indeks poza zakresem!");
            System.exit(16);
        }
        values[indices[0]] = value;
    }

    @Override
    public double daj(int... indices) {
        if (indices.length != 1) {
            System.err.println("Błąd logiczny: Wektor wymaga podania jednego indeksu!");
            System.exit(17);
        }
        if (indices[0] < 0 || indices[0] >= values.length) {
            System.err.println("Błąd logiczny: Indeks poza zakresem! (indices[0]=" + indices[0] + ")");
            System.exit(18);
        }
        return values[indices[0]];
    }

    // Funkcja pomocnicza, przeciążenie
    public void przypisz(Scalar other) {
        for (int i = 0; i < values.length; ++i)
            values[i] = other.daj();
    }

    public void przypisz(Vector other) throws DimensionException {
        if (other == null || values.length != other.values.length) {
            throw new DimensionException("Niekompatybilne wymiary wektorów przy przypisaniu!");
        }
        for (int i = 0; i < values.length; ++i)
            values[i] = other.getValues()[i];
        this.vertical = other.vertical;
    }

    @Override
    public void przypisz(LinClass other) {
        try {
            if (other instanceof Scalar) {
                przypisz((Scalar) other);
            } else if (other instanceof Vector) {
                przypisz((Vector) other);
            } else {
                throw new DimensionException("Można przypisać tylko wektor lub skalar.");
            }
        } catch (DimensionException e) {
            System.err.println("Błąd logiczny w przypisz: " + e.getMessage());
            System.exit(19);
        }
    }

    @Override
    public LinClass wycinek(int[] rowRange, int[] colRange) {
        if (rowRange == null || rowRange.length != 2) {
            System.err.println("Błąd logiczny: Wycinek wektora wymaga dwóch argumentów!");
            System.exit(20);
        }
        int start = rowRange[0];
        int end = rowRange[1];
        if (start < 0 || end >= values.length || start > end) {
            System.err.println("Błąd logiczny: Nieprawidłowy zakres wycinka)");
            System.exit(21);
        }
        double[] sub = Arrays.copyOfRange(values, start, end + 1);
        return new Vector(sub, vertical);
    }
}