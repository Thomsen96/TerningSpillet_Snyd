package Test_klasser;

import Server.ServerNetværk;

/**
 *
 * @author john
 */
public class ServerTest {

    /**
     * Til test af ServerNetværk
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
        server.sendTilSpiller("ctr:tern[1, 2, 3, 4]",0);
        server.sendTilSpiller("ctr:antaltern 7",0);
        server.sendTilSpiller("ctr:start runde",0);
        server.sendTilSpiller("ctr:tur",0);
        
        server.sendTilSpiller("ctr:gaet accepteret",0);
        
        /*
        server.sendTilSpiller("ctr:runde slut",0);
        server.sendTilSpiller("ctr:spil slut",0);
        server.sendTilSpiller("ctr:tilslut spil",0);
        //server.modtagFraSpiller(0);
*/
        
        server.kickSpiller(0);
        
        
    }

}
