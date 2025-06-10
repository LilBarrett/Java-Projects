package Gracze;
import Loteria.Kolektura;
import Loteria.Kupon;

/**
 * Klasa abstrakcyjna reprezentjąca Gracza Minimalistę
 */
public class Minimalista extends Gracz{
    private Kolektura ulubionaKolektura;
    
    // Konstruktor
    public Minimalista(String imię, String nazwisko, String pesel, long srodki, Kolektura ulubionaKolektura) {
        super(imię, nazwisko, pesel, srodki);
        this.ulubionaKolektura = ulubionaKolektura; // Trzeba podać ulubioną kolekturę
    }

    @Override
    public void kupKupon() {
        Kupon kupon = ulubionaKolektura.sprzedajKuponChybiłTrafił(1, 1, this); // Kupuje jeden kupon Chybił-Trafił z jednym zakładem
        if(kupon !=  null) kupony.add(kupon);
    }
}
