import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Reservation {
    private Date date_resa;
    private Date date_debut;
    private Date date_fin;
    private int nb_nuit_prevues;
    private int no_resa;
    private String etat_resa;
    private Client client;
    private static int nb_resa = 0;
    private Facture facture;
    private HashMap<Chambre, Nb_pers_par_chambre> lesChambres;
    private static ArrayList<Reservation> allReservations = new ArrayList<>();

    public Reservation(Date date_resa, Date date_debut, Date date_fin, String etat_resa, Client c,
                       Chambre ch, Nb_pers_par_chambre nb) {
        Reservation.nb_resa++;
        this.date_resa = date_resa;

        // Vérification de la validité des dates
        if (date_debut.after(date_fin)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }

        this.date_debut = date_debut;
        this.date_fin = date_fin;

        long diff = date_fin.getTime() - date_debut.getTime(); // Calcul de la différence en millisecondes
        this.nb_nuit_prevues = (int) (diff / (1000 * 60 * 60 * 24)); // Conversion en jours

        this.no_resa = Reservation.nb_resa;
        this.etat_resa = etat_resa;
        this.client = c;
        this.lesChambres = new HashMap<Chambre, Nb_pers_par_chambre>();
        this.lesChambres.put(ch, nb);

        allReservations.add(this); // Add the reservation to the array of all reservations
    }
	
	public HashMap<Chambre, Nb_pers_par_chambre> getLesChambres() {
		return this.lesChambres;
	}

	@Override
	public String toString() {
		return "Reservation [date_resa=" + date_resa + ", date_debut=" + date_debut + ", date_fin=" + date_fin
				+ ", nb_nuit_prevues=" + nb_nuit_prevues + ", no_resa=" + no_resa + ", etat_resa=" + etat_resa + "]";
	}
	
	public void affiche()
	{
		System.out.println(this.toString());
	}
	
	public void ajouterChambre(Chambre chambre, Nb_pers_par_chambre nbPersonnes) {
        if (lesChambres.containsKey(chambre)) {
            System.out.println("La chambre est déjà présente dans la réservation.");
        } else {
            lesChambres.put(chambre, nbPersonnes);
            System.out.println("La chambre a été ajoutée à la réservation.");
        }
    }

    public void supprimerChambre(Chambre chambre) {
        if (lesChambres.size() > 1) {
            Nb_pers_par_chambre nbPersonnes = lesChambres.remove(chambre);
            if (nbPersonnes != null) {
                System.out.println("La chambre a été supprimée de la réservation.");
            } else {
                System.out.println("La chambre n'a pas été trouvée dans la réservation.");
            }
        } else {
            System.out.println("Impossible de supprimer la première chambre de la réservation.");
        }
    }

    public void listerChambres() {
    	for (Chambre chambre : lesChambres.keySet()) {
            chambre.affiche();
        }
    }
	
	public Facture getFacture() {
		return facture;
	}
	
	public static ArrayList<Reservation> getAllReservations() {
        return allReservations;
    }

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public void modfiClient(Client c)
	{
		if(c!=null) this.client = c;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getDate_resa() {
		return date_resa;
	}

	public void setDate_resa(Date date_resa) {
		this.date_resa = date_resa;
	}

	public Date getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}

	public Date getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}

	public int getNb_nuit_prevues() {
		return nb_nuit_prevues;
	}

	public void setNb_nuit_prevues(int nb_nuit_prevues) {
		this.nb_nuit_prevues = nb_nuit_prevues;
	}

	public String getEtat_resa() {
		return etat_resa;
	}

	public void setEtat_resa(String etat_resa) {
		this.etat_resa = etat_resa;
	}

	public int getNo_resa() {
		return no_resa;
	}

	public static int getNb_resa() {
		return nb_resa;
	}
}