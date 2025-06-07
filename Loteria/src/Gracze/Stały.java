package Gracze;

import Loteria.Kolektura;

public abstract class Stały extends Gracz {
    private Kolektura[] ulubioneKolektury;
    private int następna;
    private int ostatnieLosowanie;

    public Stały(String imie, String nazwisko, String pesel, long srodki, Kolektura[] ulubioneKolektury) {
        super(imie, nazwisko, pesel, srodki);
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

    @Override
    public Kolektura wybierzKolekture(){
        return ulubioneKolektury[następna];
    }

    public void odświeżNastępną(){
        następna++;
    }
}
