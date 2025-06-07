package Gracze;
import Loteria.Kolektura;
import Loteria.Kupon;

public class Minimalista extends Gracz{
    private Kolektura ulubionaKolektura;
    
    public Minimalista(String imie, String nazwisko, String pesel, long srodki, Kolektura ulubionaKolektura) {
        super(imie, nazwisko, pesel, srodki);
        this.ulubionaKolektura = ulubionaKolektura;
    }

    @Override
    public Kolektura wybierzKolekture(){
        return ulubionaKolektura;
    }

    @Override
    public void kupKupon() {
        Kolektura kolektura = wybierzKolekture();
        Kupon kupon = kolektura.sprzedajKuponChybiłTrafił(1, 1, this);
        if(kupon !=  null) kupony.add(kupon);
    }
}
