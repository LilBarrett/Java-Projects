package LinAlgebra;

import java.util.Arrays;

public class Vector extends LinClass {
    private double[] values;
    private boolean vertical;

    public Vector(double[] values, boolean vertical) {
        this.values = Arrays.copyOf(values, values.length);
        this.vertical = vertical;
    }

    public double[] getValues() {
        return values;
    }

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
        return new int[] {
            values.length
        };
    }

    @Override
    public LinClass kopia() {
        return new Vector(this.values, this.vertical);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (vertical) {
            sb.append("[ ");
            for (int i = 0; i < values.length; i++) {
                sb.append(values[i]);
                if (i < values.length - 1) sb.append(", ");
            }
            sb.append(" ]");
        } else {
            sb.append("[\n");
            for (double v: values) {
                sb.append("  ").append(v).append("\n");
            }
            sb.append("]");
        }
        return sb.toString();
    }

    public void zaneguj() {
        for (int i = 0; i < values.length; ++i) {
            values[i] = -values[i];
        }
    }

    public void dodaj(LinClass other) {
        if (other instanceof Scalar) {
            for (int i = 0; i < values.length; ++i)
                this.values[i] += ((Scalar) other).daj();
        } else if (other instanceof Vector) {
            if (kształt() == ((Vector) other).kształt() && vertical == ((Vector) other).getOrientation()) {
                for (int i = 0; i < values.length; ++i)
                    this.values[i] += other.daj(i);
            } else {
                throw new IllegalArgumentException("Złe wymiary.");
            }
        } else
            throw new IllegalArgumentException("Można dodać tylko wektor lub Scalar.");
    }

    public void przemnóż(LinClass other) {
        if (other instanceof Scalar) {
            for (int i = 0; i < values.length; ++i)
                this.values[i] *= ((Scalar) other).daj();
        } else
            throw new IllegalArgumentException("Można tylko wektor lub Scalar.");
    }

    public LinClass negacja() {
        double[] negValues = new double[values.length];
        for (int i = 0; i < values.length; ++i) {
            negValues[i] = -values[i];
        }
        return new Vector(negValues, vertical);
    }

    public LinClass suma(LinClass other) {
        double[] newValues = new double[values.length];
        if (other instanceof Scalar) {
            for (int i = 0; i < values.length; ++i)
                newValues[i] = values[i] + ((Scalar) other).daj();
            return new Vector(newValues, vertical);
        } else if (other instanceof Vector) {
            if (kształt()[0] == ((Vector) other).kształt()[0] && vertical == ((Vector) other).getOrientation()) {
                for (int i = 0; i < values.length; ++i)
                    newValues[i] = values[i] + other.daj(i);
                return new Vector(newValues, vertical);
            } else {
                throw new IllegalArgumentException("Złe wymiary.");
            }
        } else //TODO Matrices
            throw new IllegalArgumentException("Można dodać tylko wektor lub Scalar.");
    }

    public LinClass iloczyn(LinClass other) {
        if (other instanceof Scalar) {
            double[] newValues = new double[values.length];
            for (int i = 0; i < values.length; ++i)
                newValues[i] = values[i] * ((Scalar) other).daj();
            return new Vector(newValues, vertical);
        } else if (other instanceof Vector) {
            Vector v = (Vector) other;
            if (values.length != v.values.length)
                throw new IllegalArgumentException("Złe wymiary.");

            boolean thisCol = this.getOrientation();
            boolean otherCol = v.getOrientation();

            if (thisCol == otherCol) {
                // Both same orientation: dot product (scalar)
                double result = 0;
                for (int i = 0; i < values.length; ++i)
                    result += this.values[i] * v.values[i];
                return new Scalar(result);
            } else {
                // Different orientations: always outer product (matrix)
                double[][] data = new double[thisCol ? values.length : 1][otherCol ? 1 : v.values.length];
                if (thisCol) {
                    // this: column, other: row -> matrix [n x n]
                    for (int i = 0; i < values.length; ++i)
                        for (int j = 0; j < v.values.length; ++j)
                            data[i][j] = this.values[i] * v.values[j];
                } else {
                    // this: row, other: column -> matrix [1 x 1]
                    double sum = 0;
                    for (int i = 0; i < values.length; ++i)
                        sum += this.values[i] * v.values[i];
                    data[0][0] = sum;
                }
                return new Matrix(data);
            }
        } else if (other instanceof Matrix) {
            if (values.length != ((Matrix) other).getRows())
                throw new IllegalArgumentException("Złe wymiary.");

            Vector transVector = new Vector(values, !vertical);
            Matrix copy = (Matrix) other.kopia();
            copy.transponuj();
            Vector result = (Vector) copy.iloczyn(transVector);
            result.transponuj();
            return result;
        } else {
            throw new IllegalArgumentException("Można pomnożyć tylko przez wektor lub skalar.");
        }
    }

    public void transponuj() {
        vertical = !vertical;
    }

    public void ustaw(double value, int...indices) {
        values[indices[0]] = value;
    }

    public double daj(int...indices) {
        return values[indices[0]];
    }

    public void przypisz(LinClass other) {
        if (other instanceof Vector) {
            Vector v = (Vector) other;
            if (values.length != v.values.length)
                throw new IllegalArgumentException("Złe wymiary/przekrój przypisywanego wektora.");
            for (int i = 0; i < values.length; ++i)
                values[i] = v.getValues()[i];
        } else if (other instanceof Scalar) {
            double val = ((Scalar) other).daj();
            for (int i = 0; i < values.length; ++i)
                values[i] = val;
        } else {
            throw new IllegalArgumentException("Można przypisać tylko wektor, Scalar lub macierz.");
        }
    }

    public LinClass wycinek(int[] rowRange, int[] colRange) {
        int start = rowRange[0];
        int end = rowRange[1];
        if (start < 0 || end >= values.length || start >= end)
            throw new IllegalArgumentException("Nieprawidłowy zakres wycinka.");
        double[] sub = Arrays.copyOfRange(values, start, end + 1);
        return new Vector(sub, vertical);
    }
}