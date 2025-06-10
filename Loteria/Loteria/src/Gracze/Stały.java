package Gracze;

import Loteria.Kolektura;

/**
 * Klasa abstrakcyjna dla Graczy którzy wybierają cyklicznie z list ulubionych kolektur
 */
public abstract class Stały extends Gracz {
    private Kolektura[] ulubioneKolektury;
    private int następna;
    private int ostatnieLosowanie;

    public Stały(String imię, String nazwisko, String pesel, long srodki, Kolektura[] ulubioneKolektury) {
        super(imię, nazwisko, pesel, srodki);
        this.ulubioneKolektury = ulubioneKolektury;
        następna = 0;
        ostatnieLosowanie = 0;
    }

    public int getOstatnieLosowanie() {
        return ostatnieLosowanie;
    }

    public void setOstatnieLosowanie(int nowe) {
        ostatnieLosowanie = nowe;
    }

    public Kolektura wybierzKolekture(){
        return ulubioneKolektury[następna];
    }

    public void odświeżNastępną(){
        następna = (następna + 1) % ulubioneKolektury.length;
    }
}
