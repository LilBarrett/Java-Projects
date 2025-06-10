package Loteria;

import Gracze.Gracz;
import java.util.*;

/**
 * Klasa implementująca Kolekturę Loterii - umożliwiającą zakup Kuponów
 */
public class Kolektura {
    private static int licznikKolektur = 1;
    private final int numerKolektury;
    private final List<Kupon> sprzedaneKupony = new ArrayList<>(); // Sprzedane kupony
    private static final List<Kolektura> kolektury = new ArrayList<>(); // Statycznie trzyma inne kolektury
    private final Centrala centrala;

    // Konstruktor
    public Kolektura(Centrala centrala) {
        this.numerKolektury = licznikKolektur++;
        this.centrala = centrala;
        kolektury.add(this);
        centrala.dodajKolekture(this);
    }

    public int getNumerKolektury() {
        return numerKolektury;
    }
    
    public static int getLicznikKolektur() {
        return kolektury.size();
    }

    public static Kolektura getKolektura(int idx) {
        return kolektury.get(idx);
    }
    
    public List<Kupon> getSprzedaneKupony() {
        return Collections.unmodifiableList(sprzedaneKupony);
    }

    public Centrala getCentrala(){
        return centrala;
    }

    private List<Integer> kolejnenumeryLosowań(int start, int ile) {
        List<Integer> lista = new ArrayList<>();
        for (int i = 0; i < ile; i++) {
            lista.add(start + i);
        }
        return lista;
    }

    // Sprzedaje kupon na podstawie Blankietu (wypełnionego)
    public Kupon sprzedajKuponBlankiet(Blankiet blankiet, Gracz gracz) {
        // Pobiera potrzebne dane
        long środkiKlienta = gracz.getŚrodki();
        List<Set<Integer>> zakłady = blankiet.pobierzZakłady();
        int liczbaLosowan = blankiet.pobierzLiczbeLosowan();

        if (zakłady == null || zakłady.isEmpty() || liczbaLosowan < 1) {
            return null;
        }

        int liczbaZakładów = zakłady.size();
        long cena = liczbaZakładów * liczbaLosowan * Kupon.CENA_ZAKŁADU;
        long podatek = liczbaZakładów * liczbaLosowan * Kupon.PODATEK_ZAKŁADU;

        if (środkiKlienta < cena) return null;      // nie sprzedaje gdy klient nie ma wystarczająco pieniedzy

        // Wyznacz numery losowań od najbliższego
        int pierwszyNumer = centrala.getNastepnyNumerLosowania();
        List<Integer> numeryLosowań = new ArrayList<>();
        for (int i = 0; i < liczbaLosowan; i++) {
            numeryLosowań.add(pierwszyNumer + i);
        }

        // Generuje kupon i zapisuje że został on sprzedany
        Kupon kupon = new Kupon(numerKolektury, zakłady, liczbaLosowan, numeryLosowań);
        sprzedaneKupony.add(kupon);

        // Pobiera wpłatę, przekazuje zyski centrali oraz odprowadza podatek
        gracz.pobierzŚrodki(cena);
        centrala.otrzymajWpłatę(cena - podatek);
        centrala.getBudżetPaństwa().pobierzPodatek(podatek);

        // Przekazuje kupon graczowi
        return kupon;
    }

    public Kupon sprzedajKuponChybiłTrafił(int liczbaZakładow, int liczbaLosowan, Gracz gracz) {
        // Pobiera potrzebne dane
        long środkiKlienta = gracz.getŚrodki();
        int nextLos = centrala.getNastepnyNumerLosowania();
        List<Integer> numeryLosowań = kolejnenumeryLosowań(nextLos, liczbaLosowan);

        if (liczbaZakładow < 1 || liczbaZakładow > 8 || liczbaLosowan < 1 || liczbaLosowan > 10
                || numeryLosowań == null || numeryLosowań.isEmpty()) {
            return null;
        }
        long cena = liczbaZakładow * liczbaLosowan * Kupon.CENA_ZAKŁADU;
        long podatek = liczbaZakładow * liczbaLosowan * Kupon.PODATEK_ZAKŁADU;

        if (środkiKlienta < cena) return null; // nie sprzedaje gdy klient nie ma wystarczająco pieniedzy

        // Generuje losowy kupon i zapisuje że jest on sprzedany
        List<Set<Integer>> Zakłady = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < liczbaZakładow; i++) {
            Set<Integer> Zakład = new TreeSet<>();
            while (Zakład.size() < 6) {
                Zakład.add(1 + rand.nextInt(49));
            }
            Zakłady.add(Zakład);
        }
        Kupon kupon = new Kupon(numerKolektury, Zakłady, liczbaLosowan, numeryLosowań);
        sprzedaneKupony.add(kupon);

        // Pobiera zapłatę, przesyła środki do centrali, odprowadza podatek
        gracz.pobierzŚrodki(cena);
        centrala.otrzymajWpłatę(cena - podatek);
        centrala.getBudżetPaństwa().pobierzPodatek(podatek);

        return kupon;
    }

    public int getNumer() {
        return numerKolektury;
    }

    // Sprawdza czy dany kupon należy do tej kolektury
    public boolean czyKuponNalezyDoKolektury(Kupon kupon) {
        return sprzedaneKupony.contains(kupon);
    }
}