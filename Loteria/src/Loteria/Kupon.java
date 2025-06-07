package Loteria;

import java.util.*;

public class Kupon {
    public static final long CENA_ZAKŁADU = 300;    // 3 zł = 300 gr
    public static final long PODATEK_ZAKŁADU = 60;  // 0,60 zł = 60 gr

    private static int licznikKuponow = 1;

    private final int numerKuponu;
    private final int numerKolektury;
    private final String znacznikLosowy;
    private final String identyfikator;
    private final List<Set<Integer>> zaklady;
    private final int liczbaLosowan;
    private final List<Integer> numeryLosowan;
    private final long cena;      // w groszach
    private final long podatek;   // w groszach

    // Obsługa stanu - czy zrealizowany (wypłacony)
    private boolean zrealizowany = false;

    public Kupon(int numerKolektury, List<Set<Integer>> zaklady, int liczbaLosowan, List<Integer> numeryLosowan) {
        this.numerKuponu = licznikKuponow++;
        this.numerKolektury = numerKolektury;
        this.znacznikLosowy = wygenerujZnacznikLosowy();
        this.identyfikator = wygenerujIdentyfikator();
        this.zaklady = new ArrayList<>();
        for (Set<Integer> zaklad : zaklady) {
            TreeSet<Integer> sorted = new TreeSet<>(zaklad);
            this.zaklady.add(sorted);
        }
        this.liczbaLosowan = liczbaLosowan;
        this.numeryLosowan = new ArrayList<>(numeryLosowan);

        int liczbaZakladow = zaklady.size();
        this.cena = liczbaZakladow * liczbaLosowan * CENA_ZAKŁADU;
        this.podatek = liczbaZakladow * liczbaLosowan * PODATEK_ZAKŁADU;
    }

    // Generuje 9 losowych cyfr jako String
    private String wygenerujZnacznikLosowy() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    // Oblicza sumę kontrolną: suma cyfr z numeru kuponu, numeru kolektury i znaczniku losowym, mod 100, z wiodącym zerem jeśli potrzeba
    private String wygenerujSumeKontrolna() {
        int suma = 0;
        suma += sumaCyfr(numerKuponu);
        suma += sumaCyfr(numerKolektury);
        suma += sumaCyfr(znacznikLosowy);
        int sumaKontrolna = suma % 100;
        return String.format("%02d", sumaKontrolna);
    }

    private int sumaCyfr(int liczba) {
        int suma = 0;
        while (liczba > 0) {
            suma += liczba % 10;
            liczba /= 10;
        }
        return suma;
    }

    private int sumaCyfr(String s) {
        int suma = 0;
        for (char c : s.toCharArray()) {
            suma += c - '0';
        }
        return suma;
    }

    private String wygenerujIdentyfikator() {
        return numerKuponu + "-" + numerKolektury + "-" + znacznikLosowy + "-" + wygenerujSumeKontrolna();
    }

    // GETTERY
    public String getIdentyfikator() {
        return identyfikator;
    }

    public long getCena() {
        return cena;
    }

    public long getPodatek() {
        return podatek;
    }

    public int getNumerKolektury() {
        return numerKolektury;
    }

    public int getNumerKuponu() {
        return numerKuponu;
    }

    public List<Set<Integer>> getZaklady() {
        return Collections.unmodifiableList(zaklady);
    }

    public int getLiczbaLosowan() {
        return liczbaLosowan;
    }

    public List<Integer> getNumeryLosowan() {
        return Collections.unmodifiableList(numeryLosowan);
    }

    public boolean isZrealizowany() {
        return zrealizowany;
    }

    public void oznaczJakoZrealizowany() {
        zrealizowany = true;
    }

    // Wydruk kuponu
    public void wydrukujKupon() {
        System.out.println("KUPON NR " + getIdentyfikator());
        int i = 1;
        for (Set<Integer> zaklad : zaklady) {
            System.out.print(i + ": ");
            for (int liczba : zaklad) {
                System.out.printf("%2d ", liczba);
            }
            System.out.println();
            i++;
        }
        System.out.println("LICZBA LOSOWAŃ: " + liczbaLosowan);
        System.out.print("NUMERY LOSOWAŃ:\n ");
        for (int numer : numeryLosowan) {
            System.out.print(numer + " ");
        }
        System.out.println();
        System.out.printf("CENA: %d zł %02d gr\n", cena / 100, cena % 100);
    }
}