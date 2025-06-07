package Gracze;
import Loteria.Kolektura;
import Loteria.Blankiet;

public class Stałoblankietowy extends Stały {
    private Blankiet blankiet;
    private int coIle;

    public Stałoblankietowy(String imie, String nazwisko, String pesel, long srodki, Blankiet blankiet, Kolektura[] ulubioneKolektury, int coIle) {
        super(imie, nazwisko, pesel, srodki, ulubioneKolektury);
        this.blankiet = blankiet;
        this.coIle = coIle;
    }

    @Override
    public void kupKupon() {
        Kolektura kolektura = wybierzKolekture();
        if(sprawdźCzyKupować(kolektura)){
            kolektura.sprzedajKuponBlankiet(blankiet, this);
            setOstatnieLosowanie(kolektura.getCentrala().getNastepnyNumerLosowania());
            odświeżNastępną();
        }
    }

    private boolean sprawdźCzyKupować(Kolektura kolektura) {
        int ostatnieLosowanie = getOstatnieLosowanie();
        if (ostatnieLosowanie == 0) {
            return true;
        }
        int numerNastepnegoLosowania = kolektura.getCentrala().getNastepnyNumerLosowania();
        return numerNastepnegoLosowania == ostatnieLosowanie + coIle;
    }
}
