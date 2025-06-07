package Main;

import Loteria.*;
import Gracze.*;

public class Main {
    public static void main(String[] args){
        System.out.println("Symulacja przeprowadzenia loterii zaczyna się!");
        BudżetPaństwa budżet = new BudżetPaństwa();
        Centrala centrala = new Centrala(0, budżet);
        Kolektura kolektura = new Kolektura(centrala);
        Gracz gracz = new Losowy("a", "b", "0");
        gracz.dodajŚrodki(10_000_000_000L);
        gracz.kupKupon();
        centrala.przeprowadzLosowanie();
        centrala.wypiszStanSrodkow();
        for(int i = 0; i < 3000; ++i){
            gracz.kupKupon();
            centrala.przeprowadzLosowanie();
        }
        centrala.wypiszStanSrodkow();
        gracz.odbierzWygranaWszystkie(kolektura);
        //centrala.wypiszWynikiWszystkichLosowan();
        centrala.wypiszStanSrodkow();
        budżet.wypiszStanBudżetu();
        gracz.wypiszInformacje(true);
    }
}
