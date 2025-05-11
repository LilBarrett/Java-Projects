package LinAlgebra;

/**
 * Abstrakcyjna klasa, łacząca skalary, wektory i macierze
 */
public abstract class LinClass {
    // Podaje wymiar obiektu (0 - skalar, 1 - wektor, 2 - macierz)
    public abstract int wymiar();

    // Podaje liczbę elementów obiekty
    public abstract int liczba_elementów();

    // Podaje kształt obiektu
    public abstract int[] kształt();

    // Tworzy kopię obiektu
    public abstract LinClass kopia();

    // Wypisuje obiekt jako napis
    public abstract String toString();

    // Neguje obiekt (in-place)
    public abstract void zaneguj();

    // Liczy sumę obiektu z innym (in-place)
    public abstract void dodaj(LinClass other) throws DimensionException;

    // Liczy iloczyn obiektu z innym  (in-place)
    public abstract void przemnóż(LinClass other) throws DimensionException;

    // Neguje obiekt (nowy obiekt)
    public abstract LinClass negacja();

    // Liczy sumę obiektu z innym (nowy obiekt)
    public abstract LinClass suma(LinClass other) throws DimensionException;

    // Liczy iloczyn obiektu z innym (nowy obiekt)
    public abstract LinClass iloczyn(LinClass other) throws DimensionException;

    // Transponuje obiekt (in-place)
    public abstract void transponuj();

    // Ustawia daną wartość obiektu
    public abstract void ustaw(double value, int... indices);

    // Zwraca daną wartość obiektu
    public abstract double daj(int... indices);

    // Przypisuje jednemu obiektowi wartości innego (w zadany sposób)
    public abstract void przypisz(LinClass other) throws DimensionException;

    // Zwraca wycinek obiektu 
    public abstract LinClass wycinek(int[] rowRange, int[] colRange);
}