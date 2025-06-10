package Gracze;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Loteria.Kolektura;
import Loteria.Kupon;
import Loteria.Losowanie;
import Loteria.BudżetPaństwa;
import Loteria.Centrala;

/**
 * Klasa abstrakcyjna reprezentjąca pojedyńczego Gracza (jeszcze bez podtypu)
 */
public abstract class Gracz {
    protected String imię;
    protected String nazwisko;
    protected String pesel;
    protected long środki; // Środki w groszach
    protected List<Kupon> kupony; // Lista kuponów kupionych przez gracza

    // Konstruktor
    public Gracz(String imię, String nazwisko, String pesel, long środki) {
        this.imię = imię;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.środki = środki;
        this.kupony = new ArrayList<>();
    }

    public long getŚrodki(){
        return środki;
    }

    public void pobierzŚrodki(long kwota){
        środki -= kwota;
    }

    public void dodajŚrodki(long kwota){
        środki +=  kwota;
    }

    public void dodajKupon(Kupon kupon) {
        kupony.add(kupon);
    }

    public List<Kupon> getKupony(){
        return kupony;
    }

    // Wypisuje dane gracza -> Nazwisko, imię, PESEL, Środki oraz wszystkie kody kupionych kuponów
    public void wypiszInformacje() {
        System.out.println("Nazwisko: " + nazwisko);
        System.out.println("imię: " + imię);
        System.out.println("PESEL: " + pesel);
        System.out.printf("środki: %d zl %02d gr\n", środki / 100, środki % 100);

        if (kupony.isEmpty()) {
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

    // Umożliwia graczowi na próbę odebrania wygranej w zamian za posiadany kupon
    public void odbierzWygrana(Kolektura kolektura, Kupon kupon){
        if(!kupony.contains(kupon) || !kolektura.czyKuponNalezyDoKolektury(kupon) || kupon.isZrealizowany()) return;
        else {
            long sumaWygranej = 0L;

            Centrala centrala = kolektura.getCentrala();
            BudżetPaństwa budżet = centrala.getBudżetPaństwa();
            for (int numerLosowania : kupon.getNumeryLosowan()) {
                Losowanie losowanie = centrala.getLosowanie(numerLosowania); 
                if (losowanie == null) continue;

                for (Set<Integer> zakład : kupon.getZakłady()) {
                    long sumazakładu = 0;

                    int trafien = losowanie.ileTrafien(zakład);
                    if (trafien == 6) sumazakładu += losowanie.getWygranaI();
                    else if (trafien == 5) sumazakładu += losowanie.getWygranaII();
                    else if (trafien == 4) sumazakładu += losowanie.getWygranaIII();
                    else if (trafien == 3) sumazakładu += losowanie.getWygranaIV();

                    if(sumazakładu >= 2280_00L){
                        long podatek = sumazakładu / 100;
                        sumazakładu -= podatek;
                        budżet.pobierzPodatek(podatek);
                    }

                    sumaWygranej += sumazakładu;
                }
            }
            centrala.wypłaćPieniądze(sumaWygranej);
            środki += sumaWygranej;
            kupon.oznaczJakoZrealizowany();
        }
    }

    private List<Kupon> getKuponyZUzytymiLosowaniami(Centrala centrala) {
        List<Kupon> zuzyteKupony = new ArrayList<>();
        for (Kupon kupon : kupony) {
            boolean wszystkieLosowaniaZUzyte = true;
            for (int numerLosowania : kupon.getNumeryLosowan()) {
                if (centrala.getNastepnyNumerLosowania() <= numerLosowania) {
                    wszystkieLosowaniaZUzyte = false;
                    break;
                }
            }
            if (wszystkieLosowaniaZUzyte) {
                zuzyteKupony.add(kupon);
            }
        }
        return zuzyteKupony;
    }

        // Funkcja pokazowa - Gracz próbuje odebrać nagrody za wszystkie zakupione kupony po kolei
    public void odbierzWygrane(Centrala centrala){
        List<Kupon> kuponyUzyte =  getKuponyZUzytymiLosowaniami(centrala);
        //System.out.println(kuponyUzyte);
        for (Kupon kupon : kuponyUzyte){
            odbierzWygrana(centrala.getKolektury().get(kupon.getNumerKolektury()-1), kupon);
        }
    }

    // Funkcja abstrakcyjna - implementacja w podtypach graczy
    public abstract void kupKupon();
}