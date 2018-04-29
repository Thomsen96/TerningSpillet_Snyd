/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klient;

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
        KlientFunk klient;
        klient = new KlientFunk(8998,"James");
        
        
        
        while(klient.Forbundet()){
            klient.modtagKommando();
            if(klient.getState() == "Tur"){
                klient.sendKommando("ctr:Guess(4,3)");
            }
        }
        
    }
    
}
