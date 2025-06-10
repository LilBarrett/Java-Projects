package Test.LoteriaTest;

import Loteria.Blankiet;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class BlankietTest {

    @Test
    public void testBlankietKonstruktor() {
        Blankiet b = new Blankiet();

        for (int i = 1; i <= Blankiet.LICZBA_POL; i++) {
            assertNotNull(b.getPole(i));
        }

        assertEquals(1, b.pobierzLiczbeLosowan());
        assertTrue(b.pobierzZakłady().isEmpty());
    }

    @Test
    public void testBlankietKonstruktorLista() {
        Set<Integer> zaklad1 = new HashSet<>(Arrays.asList(1,2,3,4,5,6));
        Set<Integer> zaklad2 = new HashSet<>(Arrays.asList(7,8,9,10,11,12));
        List<Set<Integer>> zaklady = Arrays.asList(zaklad1, zaklad2);

        Blankiet b = new Blankiet(zaklady, 3);

        List<Set<Integer>> pobrane = b.pobierzZakłady();
        assertEquals(2, pobrane.size());
        assertTrue(pobrane.contains(zaklad1));
        assertTrue(pobrane.contains(zaklad2));

        assertEquals(3, b.pobierzLiczbeLosowan());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankietListConstructorInvalidZaklad() {
        
        Set<Integer> bad = new HashSet<>(Arrays.asList(1,2,3,4,5));
        List<Set<Integer>> zaklady = Arrays.asList(bad);
        new Blankiet(zaklady, 1);
    }

    @Test
    public void testGetPoleValidAndInvalid() {
        Blankiet b = new Blankiet();
        assertNotNull(b.getPole(1));
        assertNotNull(b.getPole(Blankiet.LICZBA_POL));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPoleTooLow() {
        Blankiet b = new Blankiet();
        b.getPole(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPoleTooHigh() {
        Blankiet b = new Blankiet();
        b.getPole(Blankiet.LICZBA_POL+1);
    }

    @Test
    public void testZaznaczLiczbeLosowanAndWyczysc() {
        Blankiet b = new Blankiet();
        b.zaznaczLiczbeLosowan(3);
        assertEquals(3, b.pobierzLiczbeLosowan());
        b.wyczyscLiczbyLosowan();
        assertEquals(1, b.pobierzLiczbeLosowan());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZaznaczLiczbeLosowanTooLow() {
        Blankiet b = new Blankiet();
        b.zaznaczLiczbeLosowan(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZaznaczLiczbeLosowanTooHigh() {
        Blankiet b = new Blankiet();
        b.zaznaczLiczbeLosowan(Blankiet.MAX_LOSOWAN + 1);
    }

    @Test
    public void testPoleZakladuSkreslLiczbe() {
        Blankiet.PoleZakładu p = new Blankiet.PoleZakładu();
        for (int i = 1; i <= Blankiet.LICZBY_NA_Zakład; i++) {
            p.skreslLiczbe(i);
        }
        assertEquals(Blankiet.LICZBY_NA_Zakład, p.getWybraneLiczby().size());
        assertTrue(p.isWaznyZakład());
    }

    @Test(expected = IllegalStateException.class)
    public void testPoleZakladuSkreslLiczbeTooMany() {
        Blankiet.PoleZakładu p = new Blankiet.PoleZakładu();
        for (int i = 1; i <= Blankiet.LICZBY_NA_Zakład; i++) {
            p.skreslLiczbe(i);
        }
        // One more should fail
        p.skreslLiczbe(49);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPoleZakladuSkreslLiczbeTooLow() {
        Blankiet.PoleZakładu p = new Blankiet.PoleZakładu();
        p.skreslLiczbe(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPoleZakladuSkreslLiczbeTooHigh() {
        Blankiet.PoleZakładu p = new Blankiet.PoleZakładu();
        p.skreslLiczbe(Blankiet.LICZBA_KRAT + 1);
    }

    @Test(expected = IllegalStateException.class)
    public void testPoleZakladuSkreslLiczbeAnulowane() {
        Blankiet.PoleZakładu p = new Blankiet.PoleZakładu();
        p.anuluj();
        p.skreslLiczbe(1);
    }

    @Test
    public void testPoleZakladuAnuluj() {
        Blankiet.PoleZakładu p = new Blankiet.PoleZakładu();
        for (int i = 1; i <= 3; i++) {
            p.skreslLiczbe(i);
        }
        p.anuluj();
        assertTrue(p.isAnulowane());
        assertTrue(p.getWybraneLiczby().isEmpty());
        assertFalse(p.isWaznyZakład());
    }
}