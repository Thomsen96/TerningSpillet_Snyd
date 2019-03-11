/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server_old.Spil;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author chris
 */
public class SpilTest {
    
    public SpilTest() {
    }


    @Test
    public void testStart_rounde() {
        System.out.println("start_rounde");
        Spil instance = new Spil(5, 6);
        instance.start_rounde();
        instance.printTerninger(0);
        instance.antal_terninger_ialt();

        instance.gæt(2, 1);
        instance.gæt(2, 2);
        instance.gæt(2, 3);
        instance.gæt(2, 4);
        instance.gæt(2, 5);
        instance.løgner();

        for (int i = 0; i < 7; i++) {
            instance.start_rounde();
            instance.gæt(2, 40);
            instance.løgner();
        }

        assertEquals(instance.antal_terninger_ialt(), instance.liste_af_raflebaeger.get(0).antalTerninger());

    }
    
}
