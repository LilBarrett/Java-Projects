package Gracze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Loteria.Blankiet;
import Loteria.Kolektura;

/**
 * Klasa abstrakcyjna reprezentjąca Gracza Stałoliczbowego
 */
public class Stałoliczbowy extends Stały {
    private int[] ulubioneLiczby;
    
    // Konstruktor
    public Stałoliczbowy(String imię, String nazwisko, String pesel, long srodki, Kolektura[] ulubioneKolektury, int[] ulubioneLiczby) {
        super(imię, nazwisko, pesel, srodki, ulubioneKolektury);
        for (int liczba : ulubioneLiczby) {
            if (liczba < 1 || liczba > 49) {
                throw new IllegalArgumentException("Ulubione liczby muszą być z zakresu 1-49: " + liczba);
            }
        }
        this.ulubioneLiczby = ulubioneLiczby;
    }

    @Override
    public void kupKupon() {
        Blankiet blankiet = generujBlankiet();
        Kolektura kolektura = wybierzKolekture();
        if(sprawdźCzyKupować(kolektura)){
            kolektura.sprzedajKuponBlankiet(blankiet, this);
            setOstatnieLosowanie(kolektura.getCentrala().getNastepnyNumerLosowania()); // zapisujemy w którym losowaniu gracz uczestniczył ostatnio
            odświeżNastępną(); // następnym razem kolejna kolektura z listy ulubionych
        }
    }

    // Generujemy Blankiet na podstawie ulubionych liczb
    private Blankiet generujBlankiet() {
        List<Set<Integer>> zakłady = new ArrayList<>();
        Set<Integer> zakład = new HashSet<>();
        for (int j = 0; j < 6 ; j++) {
            zakład.add(ulubioneLiczby[j]);
        }
        int liczbaLosowań = 10;
        return new Blankiet(zakłady, liczbaLosowań);
    }

    // Sprawdzamy czy minęło 10 losowań od ostatniego zakupu
    private boolean sprawdźCzyKupować(Kolektura kolektura) {
        int ostatnieLosowanie = getOstatnieLosowanie();
        if (ostatnieLosowanie == 0) {
            return true;
        }
        int numerNastepnegoLosowania = kolektura.getCentrala().getNastepnyNumerLosowania();
        return numerNastepnegoLosowania == ostatnieLosowanie + 10;
    }
}
