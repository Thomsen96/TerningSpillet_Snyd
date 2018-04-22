package Klient;
import java.awt.*;
import javax.swing.*;

public class Grafikdemo extends JPanel
{
    public static int WIDTH = 60;
	public void paintComponent(Graphics g)
	{
            
            int hjørne = 0;
            
            // 6'er
            g.drawRect(WIDTH, WIDTH, WIDTH, WIDTH);
            // Venstre prikker
            g.fillArc(WIDTH+10, WIDTH+5+5, WIDTH/5, WIDTH/5, 0, 360);
            g.fillArc(WIDTH+10, WIDTH+20+5, WIDTH/5, WIDTH/5, 0, 360);
            g.fillArc(WIDTH+10, WIDTH+35+5, WIDTH/5, WIDTH/5, 0, 360);
            
            // Højre prikker
            g.fillArc(WIDTH+37, WIDTH+5+5, WIDTH/5, WIDTH/5, 0, 360);
            g.fillArc(WIDTH+37, WIDTH+20+5, WIDTH/5, WIDTH/5, 0, 360);
            g.fillArc(WIDTH+37, WIDTH+35+5, WIDTH/5, WIDTH/5, 0, 360);
            hjørne = hjørne+WIDTH+10;
            
            // 5'er
            g.drawRect(WIDTH+hjørne, WIDTH, WIDTH, WIDTH);
            // Venstre prikker
            g.fillArc(WIDTH+10+hjørne, WIDTH+5+5, WIDTH/5, WIDTH/5, 0, 360);
            g.fillArc(WIDTH+10+hjørne, WIDTH+35+5, WIDTH/5, WIDTH/5, 0, 360);
            
            // Midterste prik
            g.fillArc(WIDTH+24+hjørne, WIDTH+20+5, WIDTH/5, WIDTH/5, 0, 360);
            
            // Højre prikker
            g.fillArc(WIDTH+38+hjørne, WIDTH+5+5, WIDTH/5, WIDTH/5, 0, 360);
            g.fillArc(WIDTH+38+hjørne, WIDTH+35+5, WIDTH/5, WIDTH/5, 0, 360);  
            hjørne = hjørne+WIDTH+10;
            
            
            // 4'er
            g.drawRect(WIDTH+hjørne, WIDTH, WIDTH, WIDTH);
            // Venstre prikker
            g.fillArc(WIDTH+10+hjørne, WIDTH+5+5, WIDTH/5, WIDTH/5, 0, 360);
            g.fillArc(WIDTH+10+hjørne, WIDTH+35+5, WIDTH/5, WIDTH/5, 0, 360);
                       
            // Højre prikker
            g.fillArc(WIDTH+38+hjørne, WIDTH+5+5, WIDTH/5, WIDTH/5, 0, 360);
            g.fillArc(WIDTH+38+hjørne, WIDTH+35+5, WIDTH/5, WIDTH/5, 0, 360);  
            hjørne = hjørne+WIDTH+10;
            
            
            // 3'er
            g.drawRect(WIDTH+hjørne, WIDTH, WIDTH, WIDTH);
            // Venstre prikker
            g.fillArc(WIDTH+10+hjørne, WIDTH+5+5, WIDTH/5, WIDTH/5, 0, 360);
            
            // Midterste prik
            g.fillArc(WIDTH+24+hjørne, WIDTH+20+5, WIDTH/5, WIDTH/5, 0, 360);        
            
            // Højre prikker
            g.fillArc(WIDTH+38+hjørne, WIDTH+35+5, WIDTH/5, WIDTH/5, 0, 360);  
            hjørne = hjørne+WIDTH+10;
            
            // 2'er
            g.drawRect(WIDTH+hjørne, WIDTH, WIDTH, WIDTH);
            // Venstre prikker
            g.fillArc(WIDTH+10+hjørne, WIDTH+5+5, WIDTH/5, WIDTH/5, 0, 360);
            // Højre prikker
            g.fillArc(WIDTH+38+hjørne, WIDTH+35+5, WIDTH/5, WIDTH/5, 0, 360);     
            hjørne = hjørne+WIDTH+10;
            
            
            // 1'er
            g.drawRect(WIDTH+hjørne, WIDTH, WIDTH, WIDTH);          
            // Midterste prik
            g.fillArc(WIDTH+24+hjørne, WIDTH+20+5, WIDTH/5, WIDTH/5, 0, 360);              
      
              
            

            g.drawString("Antal terninger: ", WIDTH*15, WIDTH);
            g.drawString("Antal terninger i alt: ", (WIDTH*15)-21, WIDTH+30);
	}


}