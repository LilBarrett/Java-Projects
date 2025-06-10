package Gracze;
import Loteria.Kolektura;
import Loteria.Blankiet;

/**
 * Klasa abstrakcyjna reprezentjąca Gracza Stałoblankietowego
 */
public class Stałoblankietowy extends Stały {
    private Blankiet blankiet;
    private int coIle; // co ile losowań będzie grać

    // Konstruktor
    public Stałoblankietowy(String imię, String nazwisko, String pesel, long srodki, Blankiet blankiet, Kolektura[] ulubioneKolektury, int coIle) {
        super(imię, nazwisko, pesel, srodki, ulubioneKolektury);
        this.blankiet = blankiet; // podajemy blankiet przy tworzeniu
        this.coIle = coIle;
    }

    @Override
    public void kupKupon() {// zapisujemy w którym losowaniu gracz uczestniczył ostatnio
            odświeżNastępną(); // następnym razem kolejna kolektura z listy ulubionych
        Kolektura kolektura = wybierzKolekture();
        if(sprawdźCzyKupować(kolektura)){
            kolektura.sprzedajKuponBlankiet(blankiet, this);
            setOstatnieLosowanie(kolektura.getCentrala().getNastepnyNumerLosowania()); // zapisujemy w którym losowaniu gracz uczestniczył ostatnio
            odświeżNastępną(); // następnym razem kolejna kolektura z listy ulubionych
        }
    }

    // Sprawdzamy czy minęła dobra ilość losowań od ostatniej gry (lub czy jest pierwsze losowanie gracza)
    private boolean sprawdźCzyKupować(Kolektura kolektura) {
        int ostatnieLosowanie = getOstatnieLosowanie();
        if (ostatnieLosowanie == 0) {
            return true;
        }
        int numerNastepnegoLosowania = kolektura.getCentrala().getNastepnyNumerLosowania();
        return numerNastepnegoLosowania == ostatnieLosowanie + coIle;
    }
}
