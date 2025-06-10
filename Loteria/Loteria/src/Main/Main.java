package Main;

import Loteria.*;
import Gracze.*;
import java.util.*;

public class Main {
    public static void main(String[] args){
        System.out.println("Symulacja przeprowadzenia loterii zaczyna się!");

        // Tworzymy Centralę + 10 kolektur
        BudżetPaństwa budżet = new BudżetPaństwa();
        Centrala centrala = new Centrala(-1_000_000_00L, budżet);
        int liczbaKolektur = 10;
        List<Kolektura> kolektury = new ArrayList<>();
        for (int i = 0; i < liczbaKolektur; ++i) {
            kolektury.add(new Kolektura(centrala));
        }

        // Tworzymy po 200 graczy każdego rodzaju, rozdzielonych po kolekturach 
        List<Gracz> gracze = new ArrayList<>();
        Random rand = new Random();

        // Minimalista
        for (int i = 0; i < 200; ++i) {
            Kolektura ulubiona = kolektury.get(i % kolektury.size());
            Gracz g = new Minimalista("Min" + i, "Gracz", "10000" + i, 100_000_00L, ulubiona);
            gracze.add(g);
        }
        // Stałoliczbowy
        for (int i = 0; i < 200; ++i) {
            Kolektura[] ulubioneKolektury = { kolektury.get(i % kolektury.size()) };
            int[] liczby = rand.ints(6, 1, 50).toArray();
            Gracz g = new Stałoliczbowy("Stałol" + i, "Gracz", "20000" + i, 100_000_00L, ulubioneKolektury, liczby);
            gracze.add(g);
        }
        // Stałoblankietowy
        for (int i = 0; i < 200; ++i) {
            Kolektura[] ulubioneKolektury = { kolektury.get(i % kolektury.size()) };
            // Tworzymy blankiet: 1 zakład z losowymi liczbami
            Set<Integer> zaklad = new HashSet<>();
            while (zaklad.size() < 6) zaklad.add(rand.nextInt(49)+1);
            List<Set<Integer>> zaklady = new ArrayList<>();
            zaklady.add(zaklad);
            Blankiet blankiet = new Blankiet(zaklady, 10);
            int coIle = 5 + rand.nextInt(6); // co 5-10 losowań
            Gracz g = new Stałoblankietowy("Stałob" + i, "Gracz", "30000" + i, 100_000_00L, blankiet, ulubioneKolektury, coIle);
            gracze.add(g);
        }
        // Losowy
        for (int i = 0; i < 200; ++i) {
            Gracz g = new Losowy("Los" + i, "Gracz", "40000" + i);
            gracze.add(g);
        }

        // Organizujemy 20 losowań, przed każdym kupowanie kuponów przez graczy
        for (int los = 0; los < 20; ++los) {
            for (Gracz gracz : gracze) {
                gracz.kupKupon();
            }
            centrala.przeprowadzLosowanie();
            // Po losowaniu każdy gracz odbiera wygraną ze wszystkich kuponów, które już można rozliczyć
            for (Gracz gracz : gracze) {
                gracz.odbierzWygrane(centrala);
            }
        }

        // Wypisujemy raporty końcowe
        centrala.wypiszWynikiWszystkichLosowan();
        budżet.wypiszStanBudżetu();
        centrala.wypiszStanSrodkow();
    }
}