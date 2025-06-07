package Loteria;

import java.util.*;

public class Losowanie {
    private final int numerLosowania;
    private final int[] wylosowaneLiczby;

    // Wyniki nagród i statystyka (trzymane dla Centrali)
    private long wygranaI, wygranaII, wygranaIII, wygranaIV; // w groszach
    private int liczbaI, liczbaII, liczbaIII, liczbaIV;
    private long pulaI, pulaII, pulaIII, pulaIV;
    private long łącznaPula;

    public Losowanie(int numerLosowania) {
        this.numerLosowania = numerLosowania;
        this.wylosowaneLiczby = wygenerujLiczby();
        Arrays.sort(this.wylosowaneLiczby);
    }

    private int[] wygenerujLiczby() {
        List<Integer> liczby = new ArrayList<>();
        for (int i = 1; i <= 49; i++) {
            liczby.add(i);
        }
        Collections.shuffle(liczby);
        int[] wynik = new int[6];
        for (int i = 0; i < 6; i++) {
            wynik[i] = liczby.get(i);
        }
        Arrays.sort(wynik);
        return wynik;
    }

    public void wypiszWyniki() {
        System.out.println("Losowanie nr " + numerLosowania);
        System.out.print("Wyniki: ");
        for (int liczba : wylosowaneLiczby) {
            System.out.printf("%2d ", liczba);
        }
        System.out.println();
    }

    // Wywołuje Centrala po obliczeniu wygranych i ustala wyniki
    public void ustawNagrody(
        long wygranaI, long wygranaII, long wygranaIII, long wygranaIV,
        int liczbaI, int liczbaII, int liczbaIII, int liczbaIV,
        long pulaI, long pulaII, long pulaIII, long pulaIV, long łącznaPula
    ) {
        this.wygranaI = wygranaI;
        this.wygranaII = wygranaII;
        this.wygranaIII = wygranaIII;
        this.wygranaIV = wygranaIV;
        this.liczbaI = liczbaI;
        this.liczbaII = liczbaII;
        this.liczbaIII = liczbaIII;
        this.liczbaIV = liczbaIV;
        this.pulaI = pulaI;
        this.pulaII = pulaII;
        this.pulaIII = pulaIII;
        this.pulaIV = pulaIV;
        this.łącznaPula = łącznaPula;
    }

    public void wypiszNagrody() {
        System.out.println("Nagrody w losowaniu:");
        if (liczbaI > 0)
            System.out.printf("I stopień (6): %d x %d zł %02d gr, pula: %d zł %02d gr\n",
                liczbaI, wygranaI / 100, wygranaI % 100, pulaI / 100, pulaI % 100);
        else
            System.out.printf("I stopień (6): brak zwycięzców, pula przechodzi dalej (%d zł %02d gr)\n", pulaI / 100, pulaI % 100);

        if (liczbaII > 0)
            System.out.printf("II stopień (5): %d x %d zł %02d gr, pula: %d zł %02d gr\n",
                liczbaII, wygranaII / 100, wygranaII % 100, pulaII / 100, pulaII % 100);
        if (liczbaIII > 0)
            System.out.printf("III stopień (4): %d x %d zł %02d gr, pula: %d zł %02d gr\n",
                liczbaIII, wygranaIII / 100, wygranaIII % 100, pulaIII / 100, pulaIII % 100);
        if (liczbaIV > 0)
            System.out.printf("IV stopień (3): %d x %d zł %02d gr, pula: %d zł %02d gr\n",
                liczbaIV, wygranaIV / 100, wygranaIV % 100, pulaIV / 100, pulaIV % 100);
        System.out.printf("Łączna pula nagród: %d\n", łącznaPula);
    }

    // Zwraca ile trafionych liczb w danym zakładzie (pomocnicze)
    public int ileTrafien(Set<Integer> zaklad) {
        int trafione = 0;
        for (int liczba : wylosowaneLiczby) {
            if (zaklad.contains(liczba)) trafione++;
        }
        return trafione;
    }

    public int getNumerLosowania() {
        return numerLosowania;
    }

    public int[] getWylosowaneLiczby() {
        return Arrays.copyOf(wylosowaneLiczby, 6);
    }

    // Dostęp do statystyk dla Centrali
    public long getWygranaI() { return wygranaI; }
    public long getWygranaII() { return wygranaII; }
    public long getWygranaIII() { return wygranaIII; }
    public long getWygranaIV() { return wygranaIV; }
    public int getLiczbaI() { return liczbaI; }
    public int getLiczbaII() { return liczbaII; }
    public int getLiczbaIII() { return liczbaIII; }
    public int getLiczbaIV() { return liczbaIV; }
    public long getPulaI() { return pulaI; }
    public long getPulaII() { return pulaII; }
    public long getPulaIII() { return pulaIII; }
    public long getPulaIV() { return pulaIV; }
}