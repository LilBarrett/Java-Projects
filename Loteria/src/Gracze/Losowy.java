package Gracze;

import java.util.Random;
import Loteria.Kolektura;
import Loteria.Kupon;

public class Losowy extends Gracz {
    private static final long MAX_SRODKI = 100_000_000L;
    private static final double LAMBDA = 1.0 / 50_000;

    public Losowy(String imie, String nazwisko, String pesel) {
        super(imie, nazwisko, pesel, losujSrodki());
    }

    // Zwraca środki jako long (pełne złote, bez groszy)
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

    @Override
    public Kolektura wybierzKolekture() {
        int liczbaKolektur = Kolektura.getLicznikKolektur();
        if (liczbaKolektur == 0) return null;
        Random rand = new Random();
        int idx = rand.nextInt(liczbaKolektur);
        return Kolektura.getKolektura(idx);
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

            Kupon kupon = kolektura.sprzedajKuponChybiłTrafił(liczbaZakładów, liczbaLosowań, this);

            if (kupon != null) {
                kupony.add(kupon);
            } else {
                break;
            }
        }
    }
}