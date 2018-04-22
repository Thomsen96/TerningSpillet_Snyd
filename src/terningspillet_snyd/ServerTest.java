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
public class ServerTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        ServerNetværk server = new ServerNetværk(8998);
        Raflebaeger t = new Raflebaeger(4, true);
        t.ryst();
        server.modtagForbindelse();
        //server.modtagForbindelse();
        //Thread.sleep(5000);
        server.sendTilSpiller("hello player",0);
        server.modtagFraSpiller(0);
        server.sendRaflebaere(t, 0);
        server.sendAntalTernigerTilAlle(22);
    }

}
