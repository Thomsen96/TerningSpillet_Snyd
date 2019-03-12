package terningspillet_snyd;
import java.util.Comparator;
public class TerningComparator implements Comparator<Terning>
{ 
    // Fil oprindelig af Jacob Nordfalk

    /**
     * Beskriver hvordan Comparatoren skal sortere terninger
     */
	public int compare(Terning t1, Terning t2) // kræves af Comparator
	{
            if (t1.getVærdi()  > t2.getVærdi()) return 1; // t2 skal før t1
            if (t1.getVærdi() == t2.getVærdi()) return 0; // samme plads i listen
            else return -1;                               // t2 skal efter t1
	}
}