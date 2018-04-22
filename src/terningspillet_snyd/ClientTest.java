/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terningspillet_snyd;

/**
 *
 * @author john
 */
public class ClientTest {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SpillerNetværk spiller = new SpillerNetværk(8998, "James");
        System.out.println(spiller.modtag());
        spiller.send("Hello server");
//        System.out.println(spiller.modtag());
        System.out.println(spiller.modtagTerninger().toString());
        System.out.println(spiller.modtagAntalTerninger());
    }
    
}
