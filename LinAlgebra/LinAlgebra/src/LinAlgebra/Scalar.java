package LinAlgebra;

/**
 * Klasa reprezentująca skalar
 */
public class Scalar extends LinClass {
    private double value; // Wartość skalara

    // Konstruktor
    public Scalar(double value) {
        this.value = value;
    }

    @Override
    public int wymiar() {
        return 0;
    }

    @Override
    public int liczba_elementów() {
        return 1;
    }

    @Override
    public int[] kształt() {
        return new int[] {};
    }

    @Override
    public LinClass kopia() {
        return new Scalar(value);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public void zaneguj() {
        this.value = -this.value;
    }

    // Funkcja pomocnicza, przeciążenie (dodajemy skalar)
    public void dodaj(Scalar other) {
        this.value += other.daj();
    }

    @Override
    public void dodaj(LinClass other) {
        if (other instanceof Scalar) {
            dodaj((Scalar) other);
        } else {
            System.err.println("Błąd logiczny: Można dodać (in-place) tylko Scalar do Scalar.");
            System.exit(22);
        }
    }

    // Funkcja pomocnicza, przeciążenie (mnożymy przez skalar)
    public void przemnóż(Scalar other) {
        this.value *= other.daj();
    }

    @Override
    public void przemnóż(LinClass other) {
        if (other instanceof Scalar) {
            przemnóż((Scalar) other);
        } else {
            System.err.println("Błąd logiczny: Można mnożyć (in-place) tylko Scalar przez Scalar.");
            System.exit(23);
        }
    }

    @Override
    public LinClass negacja() {
        return new Scalar(-this.value);
    }

    // Funkcja pomocnicza, przeciążenie (dodajemy skalar)
    public Scalar suma(Scalar other) {
        return new Scalar(this.value + other.daj());
    }

    @Override
    public LinClass suma(LinClass other) throws DimensionException{
        if (other instanceof Scalar) {
            return suma((Scalar) other);
        } else if (other instanceof Vector) {
            return (other.kopia()).suma(this);
        } else if (other instanceof Matrix) {
            return (other.kopia()).suma(this);
        }
        throw new DimensionException("Podano nieokreślony LinClass");
    }

    // Funkcja pomocnicza, przeciążenie (mnożymy przez skalar)
    public Scalar iloczyn(Scalar other) {
        return new Scalar(this.value * other.daj());
    }

    @Override
    public LinClass iloczyn(LinClass other) throws DimensionException{
        if (other instanceof Scalar) {
            return iloczyn((Scalar) other);
        } else if (other instanceof Vector) {
            return (other.kopia()).iloczyn(this);
        } else if (other instanceof Matrix) {
            return (other.kopia()).iloczyn(this);
        }
        throw new DimensionException("Podano nieokreślony LinClass");
    }

    @Override
    public void transponuj() {}

    @Override
    public void ustaw(double value, int...indices) {
        this.value = value;
    }

    @Override
    public double daj(int...indices) {
        return value;
    }

    // Funkcja pomocnicza, przeciążenie (przypisujemy skalar)
    public void przypisz(Scalar other) {
        this.value = other.daj();
    }

    @Override
    public void przypisz(LinClass other) {
        if (other instanceof Scalar) {
            przypisz((Scalar) other);
        } else {
            System.err.println("Błąd logiczny: Można przypisać tylko Scalar.");
            System.exit(24);
        }
    }

    @Override
    public LinClass wycinek(int[] rowRange, int[] colRange) {
        return kopia();
    }
}