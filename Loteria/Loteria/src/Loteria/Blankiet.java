package Loteria;

import java.util.*;

/**
 * Klasa implementująca blankiet na którym można zakreślać zakłady na podstawie których generujemy kupony
 */
public class Blankiet {

    public static final int LICZBA_POL = 8;
    public static final int LICZBA_KRAT = 49;
    public static final int LICZBY_NA_Zakład = 6;
    public static final int MAX_LOSOWAN = 10;

    private final PoleZakładu[] pola;
    private final Set<Integer> zaznaczoneLiczby;

    // Konstruktor pustego blankietu
    public Blankiet() {
        this.pola = new PoleZakładu[LICZBA_POL];
        for (int i = 0; i < LICZBA_POL; i++) {
            pola[i] = new PoleZakładu();
        }
        this.zaznaczoneLiczby = new HashSet<>();
    }

    // Konstruktor blankietu na podstawie zakładów
    public Blankiet(List<Set<Integer>> zaklady, int liczbaLosowan) {
        this();
        for (int i = 0; i < zaklady.size() && i < LICZBA_POL; i++) {
            Set<Integer> zaklad = zaklady.get(i);
            if (zaklad.size() != LICZBY_NA_Zakład)
                throw new IllegalArgumentException("Zakład musi mieć" + LICZBY_NA_Zakład + " liczb");
            for (int liczba : zaklad) {
                this.pola[i].skreslLiczbe(liczba);
            }
        }
        for (int n = 1; n <= Math.min(liczbaLosowan, MAX_LOSOWAN); n++) {
            this.zaznaczLiczbeLosowan(n);
        }
    }

    public PoleZakładu getPole(int numer) {
        if (numer < 1 || numer > LICZBA_POL)
            throw new IllegalArgumentException();
        return pola[numer - 1];
    }

    public void zaznaczLiczbeLosowan(int liczba) {
        if (liczba < 1 || liczba > 10)
            throw new IllegalArgumentException();
        zaznaczoneLiczby.add(liczba);
    }

    public void wyczyscLiczbyLosowan() {
        zaznaczoneLiczby.clear();
    }

    public int pobierzLiczbeLosowan() {
        if (zaznaczoneLiczby.isEmpty()) {
            return 1;
        } else {
            return Collections.max(zaznaczoneLiczby);
        }
    }

    // Pobiera wszystkie zakłady
    public List<Set<Integer>> pobierzZakłady() {
        List<Set<Integer>> Zakłady = new ArrayList<>();
        for (PoleZakładu pole : pola) {
            if (pole.isWaznyZakład()) {
                Zakłady.add(pole.getWybraneLiczby());
            }
        }
        return Zakłady;
    }

    // Klasa pomocnicza pola reprezentujacego pojedynczy zakład
    public static class PoleZakładu {
        private final Set<Integer> wybraneLiczby;
        private boolean anulowane;

        public PoleZakładu() {
            this.wybraneLiczby = new HashSet<>();
            this.anulowane = false;
        }

        public void skreslLiczbe(int liczba) {
            if (anulowane)
                throw new IllegalStateException();
            if (liczba < 1 || liczba > LICZBA_KRAT)
                throw new IllegalArgumentException();
            if (wybraneLiczby.size() >= LICZBY_NA_Zakład)
                throw new IllegalStateException();
            wybraneLiczby.add(liczba);
        }

        public void anuluj() {
            anulowane = true;
            wybraneLiczby.clear();
        }

        public boolean isAnulowane() {
            return anulowane;
        }

        public Set<Integer> getWybraneLiczby() {
            return Collections.unmodifiableSet(wybraneLiczby);
        }

        public boolean isWaznyZakład() {
            return !anulowane && wybraneLiczby.size() == LICZBY_NA_Zakład;
        }
    }
}