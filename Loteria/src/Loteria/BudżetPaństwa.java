package Loteria;

public class BudżetPaństwa {
    private long pobranePodatki;      // w groszach
    private long przekazaneSubwencje; // w groszach

    public BudżetPaństwa() {
        pobranePodatki = 0;
        przekazaneSubwencje = 0;
    }

    public void pobierzPodatek(long kwota) {
        pobranePodatki += kwota;
    }

    public void przekazSubwencje(long kwota) {
        przekazaneSubwencje += kwota;
    }

    public long getPobranePodatki() {
        return pobranePodatki;
    }

    public long getPrzekazaneSubwencje() {
        return przekazaneSubwencje;
    }

    public void wypiszStanBudżetu() {
        System.out.printf("Budżet państwa:\n  Pobrane podatki: %d zł %02d gr\n  Przekazane subwencje: %d zł %02d gr\n",
            pobranePodatki / 100, pobranePodatki % 100,
            przekazaneSubwencje / 100, przekazaneSubwencje % 100);
    }
}