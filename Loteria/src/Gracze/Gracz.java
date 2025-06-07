package Gracze;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Loteria.Kolektura;
import Loteria.Kupon;
import Loteria.Losowanie;
import Loteria.BudżetPaństwa;
import Loteria.Centrala;

public abstract class Gracz {
    protected String imie;
    protected String nazwisko;
    protected String pesel;
    protected long środki; // grosze
    protected List<Kupon> kupony;

    public Gracz(String imie, String nazwisko, String pesel, long środki) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.środki = środki;
        this.kupony = new ArrayList<>();
    }

    public long getŚrodki(){
        return środki;
    }

    public void dodajKupon(Kupon kupon) {
        kupony.add(kupon);
    }

    public void pobierzŚrodki(long kwota){
        środki -= kwota;
    }

    public List<Kupon> getKupony(){
        return kupony;
    }

    public void dodajŚrodki(long kwota){
        środki +=  kwota;
    }

    public void wypiszInformacje(boolean czy) {
        System.out.println("Nazwisko: " + nazwisko);
        System.out.println("Imię: " + imie);
        System.out.println("PESEL: " + pesel);
        System.out.printf("Środki: %d zł %02d gr\n", środki / 100, środki % 100);

        if (kupony.isEmpty() || czy) {
            System.out.println("Brak kuponów.");
        } else {
            System.out.print("Identyfikatory posiadanych kuponów: ");
            for (int i = 0; i < kupony.size(); i++) {
                System.out.print(kupony.get(i).getIdentyfikator());
                if (i < kupony.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    public void odbierzWygrana(Kolektura kolektura, Kupon kupon){
        if(!kupony.contains(kupon) || !kolektura.czyKuponNalezyDoKolektury(kupon) || kupon.isZrealizowany()) return; //TODO
        else {
            long sumaWygranej = 0L;

            Centrala centrala = kolektura.getCentrala();
            BudżetPaństwa budżet = centrala.getBudżetPaństwa();
            for (int numerLosowania : kupon.getNumeryLosowan()) {
                Losowanie losowanie = centrala.getLosowanie(numerLosowania); 
                if (losowanie == null) continue;

                for (Set<Integer> zaklad : kupon.getZaklady()) {
                    long sumaZakładu = 0;

                    int trafien = losowanie.ileTrafien(zaklad);
                    if (trafien == 6) {
                        System.out.println("Padła 6!!!, wartość:"+losowanie.getWygranaI());
                        sumaZakładu += losowanie.getWygranaI();
                    }
                    else if (trafien == 5){
                        sumaZakładu += losowanie.getWygranaII();
                    }
                    else if (trafien == 4) sumaZakładu += losowanie.getWygranaIII();
                    else if (trafien == 3) sumaZakładu += losowanie.getWygranaIV();

                    if(sumaZakładu >= 2280_00L){
                        long podatek = sumaZakładu / 100;
                        sumaZakładu -= podatek;
                        budżet.pobierzPodatek(podatek);
                    }

                    sumaWygranej += sumaZakładu;
                }
            }
            centrala.wypłaćPieniądze(sumaWygranej);
            środki += sumaWygranej;
            kupon.oznaczJakoZrealizowany();
        }
    }

    public void odbierzWygranaWszystkie(Kolektura kolektura){
        for(Kupon kupon : kupony){
            odbierzWygrana(kolektura, kupon);
        }
    }

    public abstract void kupKupon();
    public abstract Kolektura wybierzKolekture();
}