
public class Nb_pers_par_chambre {
	private int nb_personnes_prevues;
	private int nb_personnes_reel;
	private Chambre chambre;

	public Nb_pers_par_chambre(int nb_per_prev, Chambre c) {
		this.nb_personnes_prevues = nb_per_prev;
		this.chambre = c;
	}

	@Override
	public String toString() {
		return "Nb_pers_par_chambre [nb_personnes_prevues=" + nb_personnes_prevues + ", nb_personnes_reel="
				+ nb_personnes_reel + "]";
	}
	
	public void affiche()
	{
		System.out.println(this.toString());
	}

	public int getNb_personnes_prevues() {
		return nb_personnes_prevues;
	}

	public void setNb_personnes_prevues(int nb_pers_prev) {
		this.nb_personnes_prevues = nb_pers_prev;
	}

	public int getNb_personnes_reel() {
		return nb_personnes_reel;
	}

	public void setNb_personnes_reel(int nb_pers_reel) {
		if(nb_pers_reel <= this.chambre.getCategorie().getCapacite())
		{
			this.nb_personnes_reel = nb_pers_reel;
		}else {
			System.out.println("Erreur : Le nombre de personne réel ne peut pas être suppérieur a la capacité max de la chambre");
		}	
	}
}