import java.util.ArrayList;
import java.util.HashMap;

public class Chambre {
	private int no_chambre;
	static int nb_chambre=0;
	private boolean etat_chambre;
	private Categorie categorie;
	private ArrayList<Prestation> lesPresta;
	private HashMap<Reservation, Nb_pers_par_chambre> lesChambres;
	
	public Chambre(boolean etat,Categorie c) {
		nb_chambre++;
		this.no_chambre = nb_chambre;
		this.etat_chambre = etat;
		this.categorie = c;
		this.lesPresta = new ArrayList<Prestation>();
		this.lesChambres = new HashMap<Reservation, Nb_pers_par_chambre>();
	}
	
	@Override
	public String toString() {
		return "Chambre [no_chambre=" + no_chambre + ", etat_chambre=" + etat_chambre + ", categorie=" + categorie
				+ ", lesPresta=" + lesPresta + ", lesChambres=" + lesChambres + "]";
	}



	public void affiche()
	{
		System.out.println(this.toString());
	}
	
	public void ajouterResa(Reservation r, Nb_pers_par_chambre nb) {
        if (lesChambres.containsKey(r)) {
            System.out.println("La réservation est déjà associée à cette chambre.");
        } else {
            lesChambres.put(r, nb);
            System.out.println("La réservation a été ajoutée à la chambre.");
        }
    }
	
	public void supprimerResa(Reservation reservation) {
        if (lesChambres.containsKey(reservation)) {
            lesChambres.remove(reservation);
            System.out.println("La réservation a été supprimée de la chambre.");
        } else {
            System.out.println("La réservation n'est pas associée à cette chambre.");
        }
    }
	
	public ArrayList<Reservation> listerReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>(lesChambres.keySet());
        return reservations;
    }
	
	public HashMap<Reservation, Nb_pers_par_chambre> getLesChambres() {
		return lesChambres;
	}

	public void setLesChambres(HashMap<Reservation, Nb_pers_par_chambre> lesChambres) {
		this.lesChambres = lesChambres;
	}

	public void listerPresta() {
        for (Prestation presta : lesPresta) {
        		presta.affiche();
        }
    }
	
	public ArrayList<Prestation> getLesPresta() {
		return lesPresta;
	}

	public void setLesPresta(ArrayList<Prestation> lesPresta) {
		this.lesPresta = lesPresta;
	}

	public void ajoutPresta(Prestation p)
	{
		if(p!=null && !lesPresta.contains(p))
		{
			lesPresta.add(p);
		}
	}
	
	public void suppPresta(Prestation p)
	{
		if(p!=null && lesPresta.contains(p))
		{
			lesPresta.remove(p);
		}
	}
	
	public void modifCategorie(Categorie c)
	{
		if(c!=null) this.categorie = c;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public int getNo_chambre() {
		return no_chambre;
	}

	public boolean isEtat_chambre() {
		return etat_chambre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (etat_chambre ? 1231 : 1237);
		result = prime * result + no_chambre;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chambre other = (Chambre) obj;
		if (etat_chambre != other.etat_chambre)
			return false;
		if (no_chambre != other.no_chambre)
			return false;
		return true;
	}
}
