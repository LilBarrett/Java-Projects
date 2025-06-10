package Gracze;

import java.util.Random;
import Loteria.Kolektura;
import Loteria.Kupon;

/**
 * Klasa abstrakcyjna reprezentjąca Gracza Losowego
 */
public class Losowy extends Gracz {
    private static final long MAX_SRODKI = 100_000_000L;
    private static final double LAMBDA = 1.0 / 50_000;

    // Konstruktor
    public Losowy(String imię, String nazwisko, String pesel) {
        super(imię, nazwisko, pesel, losujSrodki());
    }

    // Losuje z rozkładu wykładniczgo (for fun)
    private static long losujSrodki() {
        Random rand = new Random();
        double u = rand.nextDouble();
        double srodkiZl = -Math.log(1 - u) / LAMBDA;
        if (srodkiZl > MAX_SRODKI / 100.0) {
            srodkiZl = MAX_SRODKI / 100.0;
        }
        long srodkiGrosze = Math.round(srodkiZl * 100.0); // zamiana na grosze
        return srodkiGrosze;
    }

    private Kolektura wybierzKolekture() {
        int liczbaKolektur = Kolektura.getLicznikKolektur(); // Sprawdza ile jest kolektur - o ile są
        if (liczbaKolektur == 0) return null;
        Random rand = new Random();
        int numerKolektury = rand.nextInt(liczbaKolektur); // Wybiera losową kolekturę
        return Kolektura.getKolektura(numerKolektury);
    }

    @Override
    public void kupKupon() {
        Kolektura kolektura = wybierzKolekture();
        if(kolektura == null) return;
        Random rand = new Random();
        int liczbaKuponów = 1 + rand.nextInt(100);

        for (int i = 0; i < liczbaKuponów; i++) {
            int liczbaZakładów = 1 + rand.nextInt(8);   // 1 do 8 zakładów na kupon
            int liczbaLosowań = 1 + rand.nextInt(10);   // 1 do 10 losowań na kupon

            Kupon kupon = kolektura.sprzedajKuponChybiłTrafił(liczbaZakładów, liczbaLosowań, this); // Kupuje kupon lub kupony ChybiłTrafił

            if (kupon != null) {
                kupony.add(kupon);
            } else {
                break; // Gdy go nie stać to przestaje próbować
            }
        }
    }
}