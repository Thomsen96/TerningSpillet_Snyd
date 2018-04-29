/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terningspillet_snyd;

import Server.ServerNetværk;

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
        server.modtagForbindelse();
        //server.modtagForbindelse();
        //Thread.sleep(5000);
        server.sendTilSpiller("msg: Dav med dig",0);
        server.sendTilSpiller("ctr:accepteret",0);
        //server.sendTilSpiller("ctr:This is junk.png",0);
        server.sendTilSpiller("ctr:initier spil",0);
        server.sendTilSpiller("ctr:initier runde",0);
        server.sendTilSpiller("ctr:start runde",0);
        server.sendTilSpiller("ctr:tur",0);
        server.sendTilSpiller("ctr:gaetaccepteret",0);
        server.sendTilSpiller("ctr:runde slut",0);
        server.sendTilSpiller("ctr:spil slut",0);
        server.sendTilSpiller("ctr:tilslut spil",0);
        //server.modtagFraSpiller(0);
        
        //server.kickSpiller(0);
        
    }

}
