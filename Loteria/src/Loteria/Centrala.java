package Loteria;

import java.util.*;

public class Centrala {
    private long środki; 
    private long pobraneSubwencje; 
    private final List<Losowanie> losowania = new ArrayList<>();
    private long kumulacjaIStopien = 0; 
    private final List<Kolektura> kolektury = new ArrayList<>();
    private final BudżetPaństwa budżet;

    public Centrala(long środki, BudżetPaństwa budżet) {
        this.środki = środki;
        this.budżet = budżet;
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

    private List<Kupon> znajdźKupony(Losowanie losowanie){
        int numerLosowania = losowanie.getNumerLosowania();
        List<Kupon> kuponyBioraceUdzial = new ArrayList<>();
        for (Kolektura kolektura : kolektury) {
            for (Kupon kupon : kolektura.getSprzedaneKupony()) {
                if (kupon.getNumeryLosowan().contains(numerLosowania) && !kupon.isZrealizowany()) {
                    kuponyBioraceUdzial.add(kupon);
                }
            }
        }
        return kuponyBioraceUdzial;
    }

    private void obliczWygranePoLosowaniu(Losowanie losowanie) {
        List<Kupon> kuponyBioraceUdzial = znajdźKupony(losowanie);
        long sumaWpłat = 0;
        int liczbaZakładów = 0;
        Map<Integer, Integer> trafienia = new HashMap<>(); 
        for (int i = 6; i > 2; i--) trafienia.put(i, 0);

        for (Kupon k : kuponyBioraceUdzial) {
            for (Set<Integer> zaklad : k.getZaklady()) {
                liczbaZakładów++;
                int ileTrafionych = losowanie.ileTrafien(zaklad);
                if (ileTrafionych >= 3 && ileTrafionych <= 6) {
                    trafienia.put(ileTrafionych, trafienia.get(ileTrafionych) + 1);
                }
            }
        }
        sumaWpłat = liczbaZakładów * Kupon.CENA_ZAKŁADU; 

        long naNagrody = (sumaWpłat * 51) / 100;
        long pulaI = (naNagrody * 44) / 100;
        long pulaII = (naNagrody * 8) / 100;
        long pulaIV = trafienia.get(3) * 2400; 
        long pulaIII = naNagrody - pulaI - pulaII - pulaIV;

        pulaI += kumulacjaIStopien;
        pulaI = Math.max(pulaI, 2_000_000_00L);

        long wygranaI = (trafienia.get(6) > 0) ? pulaI / trafienia.get(6) : 0;
        long wygranaII = (trafienia.get(5) > 0) ? pulaII / trafienia.get(5) : 0;
        long wygranaIII = (trafienia.get(4) > 0) ? Math.max(pulaIII / trafienia.get(4), 3600) : 0; 
        long wygranaIV = 2400; 

        if (trafienia.get(6) == 0) {
            kumulacjaIStopien = pulaI;
        } else {
            kumulacjaIStopien = 0;
        }

        long sumaWypłat = wygranaI * trafienia.get(6) + wygranaII * trafienia.get(5)
        + wygranaIII * trafienia.get(4) + wygranaIV * trafienia.get(3);

        losowanie.ustawNagrody(wygranaI, wygranaII, wygranaIII, wygranaIV,
                               trafienia.get(6), trafienia.get(5), trafienia.get(4), trafienia.get(3),
                               pulaI, pulaII, pulaIII, pulaIV, sumaWypłat);
    }

    public void przeprowadzLosowanie() {
        int numer = losowania.size() + 1;
        Losowanie losowanie = new Losowanie(numer);
        obliczWygranePoLosowaniu(losowanie);
        losowania.add(losowanie);
    }

    public void wypiszWynikiWszystkichLosowan() {
        for (Losowanie l : losowania) {
            l.wypiszWyniki();
            l.wypiszNagrody();
        }
    }

    public void wypiszStanSrodkow() {
        System.out.printf("Stan środków centrali: %d zł %02d gr\n", środki / 100, środki % 100);
    }

    public boolean odbierzWygrana(Kupon kupon, Kolektura kolektura, BudżetPaństwa budzet) {
        return false; 
    }
}