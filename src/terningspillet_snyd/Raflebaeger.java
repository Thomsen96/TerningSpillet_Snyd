package terningspillet_snyd;
import java.util.ArrayList;

public class Raflebaeger
{
	/** listen af terninger, der er i raflebægeret */
    public ArrayList<Terning> terninger;
    public boolean tom = false;

	public Raflebaeger(int antalTerninger)
	{
		terninger = new ArrayList<Terning>();
		for (int i=0; i<antalTerninger; i++)
		{
			Terning t;
			t = new Terning();
			terninger.add(t);
		}
	}

	/** lægger en terning i bægeret */
	public void tilføjTerning(Terning t)
	{
		terninger.add(t);
	}
        
        /** fjerner en terning fra bægeret */
        public void fjernTerning()
	{
            try {
              terninger.remove(terninger.size()-1);
              if(antalTerninger() == 0){
                  tom = true;
              }
              
            } catch (Exception e) {
                System.out.println("Kunne ikke fjerne terningen!");
            }
	}

	/** ryster bægeret, så alle terningerne bliver 'kastet' og får en ny værdi */
	public void ryst()
	{
		for (Terning t : terninger) 
		{
			t.kast();
		}
	}


	/** finder antallet af terninger, der viser en bestemt værdi */
	public int antalDerViser(int værdi)
	{
		int resultat=0;
		for (Terning t : terninger) 
		{
			int terningensVærdi = t.getVærdi();
                        
                        // Hvis terningen er 'værdi'
			if (terningensVærdi==værdi) 
			{
				resultat = resultat + 1;
			}
		}
		return resultat;
	}

	/** beskriver bægerets indhold som en streng */
	public String toString()
	{// (listens toString() kalder toString() på hver terning)
		return terninger.toString();
	}
        
        public int antalTerninger(){
            return terninger.size();
        }
}