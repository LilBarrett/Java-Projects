package Test.LoteriaTest;

import Loteria.BudżetPaństwa;
import Loteria.Centrala;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BudżetPaństwaTest {

    @Test
    public void testPobierzPodatek() {
        BudżetPaństwa budżet = new BudżetPaństwa();
        assertEquals(0L, budżet.getPobranePodatki());

        budżet.pobierzPodatek(1500L);
        assertEquals(1500L, budżet.getPobranePodatki());

        budżet.pobierzPodatek(2500L);
        assertEquals(4000L, budżet.getPobranePodatki());
    }

    @Test
    public void testPrzekazSubwencje() {
        BudżetPaństwa budżet = new BudżetPaństwa();
        Centrala centrala = new Centrala(0, budżet);

        assertEquals(0L, budżet.getPrzekazaneSubwencje());

        centrala.pobierzSubwencje(3000L);
        assertEquals(3000L, budżet.getPrzekazaneSubwencje());

        centrala.pobierzSubwencje(7000L);
        assertEquals(10000L, budżet.getPrzekazaneSubwencje());
    }

    @Test
    public void testWypiszStanBudżetu() {
        BudżetPaństwa budżet = new BudżetPaństwa();
        Centrala centrala = new Centrala(0, budżet);

        centrala.pobierzSubwencje(3000_01L); 
        budżet.pobierzPodatek(10_000_00L); 

        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            budżet.wypiszStanBudżetu();
        } finally {
            // Restore System.out
            System.setOut(originalOut);
        }

        String expected = String.format(
            "Budżet państwa:\n  Pobrane podatki: %d zł %02d gr\n  Przekazane subwencje: %d zł %02d gr\n",
            10000, 0, 3000, 1
        );

        assertEquals(expected, outContent.toString());
    }
}