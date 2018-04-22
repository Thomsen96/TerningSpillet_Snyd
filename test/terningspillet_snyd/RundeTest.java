/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terningspillet_snyd;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author chris
 */
public class RundeTest {
    
    public RundeTest() {
    }
    /*
    @Test
    public void testGetAntalØjne() {
        System.out.println("getAntal\u00d8jne");
        int værdi = 0;
        Runde instance = null;
        int expResult = 0;
        int result = instance.getAntalØjne(værdi);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetKombinationer() {
        System.out.println("getKombinationer");
        int værdi = 0;
        Runde instance = null;
        int expResult = 0;
        int result = instance.getKombinationer(værdi);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGæt() {
        System.out.println("g\u00e6t");
        int værdi = 0;
        int antal = 0;
        Runde instance = null;
        instance.gæt(værdi, antal);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testLøgner() {
        System.out.println("l\u00f8gner");
        Runde instance = null;
        instance.løgner();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testPrintTerninger() {
        System.out.println("printTerninger");
        Runde instance = null;
        instance.printTerninger();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    */
    @Test
    public void testStart_rounde() {
        System.out.println("start_rounde");
        Spil instance = new Spil(5, 6);
        instance.start_rounde();
        instance.printTerninger(0);
        instance.antal_terninger_ialt();
        
        instance.gæt(2, 1, instance.getHvis_tur() );
        instance.gæt(2, 2, instance.getHvis_tur());
        instance.gæt(2, 3, instance.getHvis_tur());
        instance.gæt(2, 4, instance.getHvis_tur());
        instance.gæt(2, 5, instance.getHvis_tur());
        instance.løgner(instance.getHvis_tur());
        
        for (int i = 0; i < 7; i++) {
            instance.start_rounde();
            instance.printTerninger(0);
            instance.gæt(2, 100, instance.getHvis_tur());
            instance.løgner(instance.getHvis_tur());            
        }
        
        assertEquals(instance.antal_terninger_ialt(), instance.liste_af_raflebaeger.get(0).antalTerninger());

    }
    
}
