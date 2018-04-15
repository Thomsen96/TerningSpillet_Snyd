package terningspillet_snyd;
/** En klasse der beskriver 6-sidede terninger */
public class Terning
{
	/** antallet af øjne på den side på terningen, der vender opad lige nu */
	private int værdi;

	/** konstruktør der opretter en terning */
	public Terning()
	{
		// vælg en tilfældig side til at starte med
		værdi = (int) (Math.random() * 6 + 1);
	}

	/** kaster terningen, så den får en anden værdi */
	public void kast()
	{
		// vælg en tilfældig side
		double tilfældigtTal = Math.random();
		værdi = (int) (tilfældigtTal * 6 + 1);
	}

	/** Aflæser terningens værdi */
	public int getVærdi()
	{
		return værdi;
	}


	/** giver en beskrivelse af terningen som en streng */
	public String toString()
	{
		String svar = ""+værdi;  // værdi som streng, f.eks. "4"
		return svar;
	}
}