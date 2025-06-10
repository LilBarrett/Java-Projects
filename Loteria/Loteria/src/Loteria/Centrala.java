package Loteria;

import java.util.*;

/**
 * Klasa implementująca Centralę Loterii - organizującą losowania
 */
public class Centrala {
    private long środki; 
    private long pobraneSubwencje; 
    private final List<Losowanie> losowania = new ArrayList<>(); // Przeprowadzone Losowania
    private long kumulacja; // Wartość kumulacji
    private final List<Kolektura> kolektury = new ArrayList<>(); // Przynależne kolektury
    private final BudżetPaństwa budżet;

    // Konstruktor
    public Centrala(long środki, BudżetPaństwa budżet) {
        this.środki = środki;
        this.budżet = budżet;
        this.pobraneSubwencje = 0;
        this.kumulacja = 0;
    }

    public long getśrodki() {
        return środki;
    }

    public long getpobraneSubwencje() {
        return pobraneSubwencje;
    }

    public List<Losowanie> getLosowania() {
        return Collections.unmodifiableList(losowania);
    }

    // Zwraca konkretne losowanie po numerze
    public Losowanie getLosowanie(int numerLosowania) {
        if(numerLosowania < 1 || numerLosowania > losowania.size()) return null;
        return losowania.get(numerLosowania - 1);
    }

    public BudżetPaństwa getBudżetPaństwa(){
        return budżet;
    }

    public void otrzymajWpłatę(long kwota) {
        środki += kwota;
    }

    public void wypłaćPieniądze(long kwota) {
        if(środki - kwota < 0){
            pobierzSubwencje(kwota - środki);
        }
        środki -= kwota;
    }

    public void pobierzSubwencje(long kwota) {
        budżet.przekazSubwencje(kwota);
        środki += kwota;
        pobraneSubwencje += kwota;
    }

    public int getNastepnyNumerLosowania(){
        return losowania.size() + 1;
    }

    public void dodajKolekture(Kolektura kolektura) {
        kolektury.add(kolektura);
    }

    public List<Kolektura> getKolektury() {
        return Collections.unmodifiableList(kolektury);
    }

    // Szuka sprzedanych kuponów biorących udział w danym losowaniu
    private List<Kupon> znajdźKupony(Losowanie losowanie){
        int numerLosowania = losowanie.getNumerLosowania();
        List<Kupon> kuponyBioraceUdzial = new ArrayList<>();
        for (Kolektura kolektura : kolektury) {
            for (Kupon kupon : kolektura.getSprzedaneKupony()) {
                // Czy kupon jest w tym losowaniu i czy nie jest już zrealizowany w innym
                if (kupon.getNumeryLosowan().contains(numerLosowania) && !kupon.isZrealizowany()) {
                    kuponyBioraceUdzial.add(kupon);
                }
            }
        }
        return kuponyBioraceUdzial;
    }

    // Liczy wygrane po losowanie i przypisuje je do danego losowania
    private void obliczWygranePoLosowaniu(Losowanie losowanie) {
        List<Kupon> kuponyBioraceUdzial = znajdźKupony(losowanie); // Kupony pzynależne do losowania
        long sumaWpłat = 0;
        int liczbaZakładów = 0;

        Map<Integer, Integer> trafienia = new HashMap<>(); 
        for (int i = 6; i > 2; i--) trafienia.put(i, 0); // Będziemy liczyć ile jest trafień wygrywających

        // Liczymy ile jest wygranych każdego stopnia
        for (Kupon k : kuponyBioraceUdzial) {
            for (Set<Integer> zakład : k.getZakłady()) {
                liczbaZakładów++;
                int ileTrafionych = losowanie.ileTrafien(zakład);
                if (ileTrafionych >= 3 && ileTrafionych <= 6) {
                    trafienia.put(ileTrafionych, trafienia.get(ileTrafionych) + 1);
                }
            }
        }
        // Całkowita suma wpłat
        sumaWpłat = liczbaZakładów * Kupon.CENA_ZAKŁADU; 

        // Liczymy ile przeznaczamy na nagrody oraz wartości poszczególnych nagród
        long naNagrody = (sumaWpłat * 51) / 100;
        long pulaI = (naNagrody * 44) / 100;
        long pulaII = (naNagrody * 8) / 100;
        long pulaIV = trafienia.get(3) * 2400; 
        long pulaIII = naNagrody - pulaI - pulaII - pulaIV;

        // Jeśli jest kumulacja to dodajemy
        pulaI += kumulacja;

        // Jeśli pulaI jest za mała to ustawiamy 2 milliony zł
        pulaI = Math.max(pulaI, 2_000_000_00L);

        long wygranaI = (trafienia.get(6) > 0) ? pulaI / trafienia.get(6) : 0;
        long wygranaII = (trafienia.get(5) > 0) ? pulaII / trafienia.get(5) : 0;
        long wygranaIII = (trafienia.get(4) > 0) ? Math.max(pulaIII / trafienia.get(4), 3600) : 0; 
        long wygranaIV = 2400; 

        // Czy mamy kumulację po tym losowaniu?
        if (trafienia.get(6) == 0) {
            kumulacja = pulaI;
        } else {
            kumulacja = 0;
        }

        // Liczymy sumę wypłat
        long sumaWypłat = wygranaI * trafienia.get(6) + wygranaII * trafienia.get(5)
        + wygranaIII * trafienia.get(4) + wygranaIV * trafienia.get(3);

        // Przypisujemy to co dostaliśmy do obecnego losowania
        losowanie.ustawNagrody(wygranaI, wygranaII, wygranaIII, wygranaIV,
                               trafienia.get(6), trafienia.get(5), trafienia.get(4), trafienia.get(3),
                               pulaI, pulaII, pulaIII, pulaIV, sumaWypłat);
    }

    // Przeprowadza losowanie 
    public void przeprowadzLosowanie() {
        int numer = losowania.size() + 1; // następne losowanie
        Losowanie losowanie = new Losowanie(numer);
        obliczWygranePoLosowaniu(losowanie);
        losowania.add(losowanie); // dodajemy do listy losowań
    }

    // Wypisuje wynik wszystkich przeprowadzonych losowań
    public void wypiszWynikiWszystkichLosowan() {
        for (Losowanie l : losowania) {
            l.wypiszWyniki();
            l.wypiszNagrody();
        }
    }

    // Wypisuje stan środków centrali
    public void wypiszStanSrodkow() {
        System.out.printf("Stan środków centrali: %d zł %02d gr\n", środki / 100, środki % 100);
    }
}