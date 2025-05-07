package LinAlgebra;

public class Scalar extends LinClass {
    private double value;

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

    @Override
    public void dodaj(LinClass other) {
        if (other instanceof Scalar)
            this.value += ((Scalar) other).value;
        else
            throw new IllegalArgumentException("Można dodać tylko Scalar do Scalara.");
    }

    @Override
    public void przemnóż(LinClass other) {
        if (other instanceof Scalar)
            this.value *= ((Scalar) other).value;
        else
            throw new IllegalArgumentException("Można mnożyć tylko Scalar przez Scalar.");
    }

    @Override
    public LinClass negacja() {
        return new Scalar(-this.value);
    }

    @Override
    public LinClass suma(LinClass other) {
        if (other instanceof Scalar) {
            return new Scalar(this.value + ((Scalar) other).value);
        } else if (other instanceof Vector) {
            return (other.kopia()).suma(this);
        } else if (other instanceof Matrix) {
            return (other.kopia()).suma(this);
        }
        throw new IllegalArgumentException("Suma: Można dodawać tylko Scalary.");
    }

    @Override
    public LinClass iloczyn(LinClass other) {
        if (other instanceof Scalar) {
            return new Scalar(this.value * ((Scalar) other).value);
        } else if (other instanceof Vector) {
            return (other.kopia()).iloczyn(this);
        } else if (other instanceof Matrix) {
            return (other.kopia()).iloczyn(this);
        }
        throw new IllegalArgumentException("Iloczyn: Można mnożyć tylko Scalary.");
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

    @Override
    public void przypisz(LinClass other) {
        if (!(other instanceof Scalar)) throw new IllegalArgumentException("Można przypisać tylko Scalar");
        this.value = ((Scalar) other).value;
    }

    @Override
    public LinClass wycinek(int[] rowRange, int[] colRange) {
        return kopia();
    }
}