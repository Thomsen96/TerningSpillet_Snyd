package terningspillet_snyd;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class TegnbareObjekter extends JPanel
{
	ArrayList<Tegnbar> tegnbareObjekter = new ArrayList<Tegnbar>();

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		for (Tegnbar t : tegnbareObjekter){
                    t.tegn(g);
                }
	}
}