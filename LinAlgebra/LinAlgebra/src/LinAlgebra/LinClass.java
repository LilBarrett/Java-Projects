package LinAlgebra;
public abstract class LinClass{
    public abstract int wymiar();
    public abstract int liczba_elementów();
    public abstract int[] kształt();
    public abstract LinClass kopia();
    public abstract String toString();
    public abstract void zaneguj();
    public abstract void dodaj(LinClass other);
    public abstract void przemnóż(LinClass other);
    public abstract LinClass negacja();
    public abstract LinClass suma(LinClass other);
    public abstract LinClass iloczyn(LinClass other);
    public abstract void transponuj();
    public abstract void ustaw(double value, int... indices);
    public abstract double daj(int... indices);
    public abstract void przypisz(LinClass other);
    public abstract LinClass wycinek(int[] rowRange, int[] colRange);
}