package Loteria;

import Gracze.Gracz;
import java.util.*;

public class Kolektura {
    private static int licznikKolektur = 1;
    private final int numerKolektury;
    private final List<Kupon> sprzedaneKupony = new ArrayList<>();
    private static final List<Kolektura> kolektury = new ArrayList<>();
    private final Centrala centrala;

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

    public Kupon sprzedajKuponBlankiet(Blankiet blankiet, Gracz gracz) {
        long środkiKlienta = gracz.getŚrodki();
        List<Set<Integer>> zakłady = blankiet.pobierzZakłady();
        int liczbaLosowan = blankiet.pobierzLiczbeLosowan();

        if (zakłady == null || zakłady.isEmpty() || liczbaLosowan < 1) {
            return null;
        }

        int liczbaZakładów = zakłady.size();
        long cena = liczbaZakładów * liczbaLosowan * Kupon.CENA_ZAKŁADU;
        long podatek = liczbaZakładów * liczbaLosowan * Kupon.PODATEK_ZAKŁADU;

        if (środkiKlienta < cena) return null;

        // Wyznacz numery losowań od najbliższego
        int pierwszyNumer = centrala.getNastepnyNumerLosowania();
        List<Integer> numeryLosowań = new ArrayList<>();
        for (int i = 0; i < liczbaLosowan; i++) {
            numeryLosowań.add(pierwszyNumer + i);
        }

        Kupon kupon = new Kupon(numerKolektury, zakłady, liczbaLosowan, numeryLosowań);
        sprzedaneKupony.add(kupon);

        gracz.pobierzŚrodki(cena);
        centrala.otrzymajWpłatę(cena - podatek);
        BudżetPaństwa budżet = centrala.getBudżetPaństwa();
        budżet.pobierzPodatek(podatek);

        return kupon;
    }

    public Kupon sprzedajKuponChybiłTrafił(int liczbaZakładow, int liczbaLosowan, Gracz gracz) {
        long środkiKlienta = gracz.getŚrodki();
        int nextLos = centrala.getNastepnyNumerLosowania();
        List<Integer> numeryLosowań = kolejnenumeryLosowań(nextLos, liczbaLosowan);

        if (liczbaZakładow < 1 || liczbaZakładow > 8 || liczbaLosowan < 1 || liczbaLosowan > 10
                || numeryLosowań == null || numeryLosowań.isEmpty()) {
            return null;
        }
        long cena = liczbaZakładow * liczbaLosowan * Kupon.CENA_ZAKŁADU;
        long podatek = liczbaZakładow * liczbaLosowan * Kupon.PODATEK_ZAKŁADU;

        if (środkiKlienta < cena) return null; 

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

        gracz.pobierzŚrodki(cena);
        centrala.otrzymajWpłatę(cena - podatek);
        BudżetPaństwa budżet = centrala.getBudżetPaństwa();
        budżet.pobierzPodatek(podatek);

        return kupon;
    }

    public int getNumer() {
        return numerKolektury;
    }

    public boolean czyKuponNalezyDoKolektury(Kupon kupon) {
        return sprzedaneKupony.contains(kupon);
    }
}