import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Facture {
	private static Double TVA = 10.0;
	private static Double PERS_EN_PLUS = 0.10;
	
	
	private int no_facture;
	static int nb_factures = 0;
	private Double montant_ht;
    private BooleanProperty paimentValide;
	private Date date_paiement;
	private Reservation resa;
	private Double montant_tva;
	private Double total;
	private int nb_chambres;
	private int nb_pres_total;
	private int pers_supp;
	private Double totalTva;
	
	public Facture(Date date_paiement,Reservation r) {
		Facture.nb_factures++;
		this.no_facture = Facture.nb_factures;
        this.paimentValide = new SimpleBooleanProperty(false);
		this.date_paiement = date_paiement;
		this.resa = r;
		this.montant_ht = 0.0;
		this.nb_pres_total = 0;
		this.pers_supp = 0;
		this.totalTva = 0.0;
		for(Chambre chambre : this.resa.getLesChambres().keySet())
        {	
			this.nb_pres_total = this.nb_pres_total + this.resa.getLesChambres().get(chambre).getNb_personnes_reel();
        	if(this.resa.getLesChambres().get(chambre).getNb_personnes_reel() > 2)
        	{
        		pers_supp = this.resa.getLesChambres().get(chambre).getNb_personnes_reel() - 2;
        		this.totalTva = this.totalTva + ((this.resa.getNb_nuit_prevues()) * chambre.getCategorie().getTarif_de_base() * Facture.getPERS_EN_PLUS() / Facture.getTVA());
        	}
    		this.montant_ht = this.montant_ht+ (chambre.getCategorie().getTarif_de_base() * this.resa.getNb_nuit_prevues());
        }
		this.montant_tva = this.montant_ht/TVA; 
		this.total = this.montant_ht + this.montant_tva;
		this.totalTva = this.totalTva + this.getMontant_tva();

	}

	/*public static void main(String[] args) {
		Categorie categorie = new Categorie(3,60.0,"Standard");

        // Création d'une chambre
        Chambre chambre = new Chambre("Chambre Economique", 60.0, "Une chambre", categorie, null, null);

        // Création d'un client
        Client client = new Client("M.", "Dupont", "Jean", "1 rue de Paris", "", "75000", "Paris", "0123456789", "jdupont@example.com");

        // Création d'une réservation pour le client
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateResa = null, dateDebut= null, dateFin= null;
        try {
	        dateResa = sdf.parse("27/06/2023");
	        dateDebut = sdf.parse("30/07/2023");
	        dateFin = sdf.parse("34/07/2023");
        }catch (ParseException e) {
            e.printStackTrace();
        }

        
        Nb_pers_par_chambre nb = new Nb_pers_par_chambre(2,chambre);
        nb.setNb_personnes_reel(3);
        
        Reservation reservation = new Reservation(dateResa, dateDebut, dateFin, "En cours", client, chambre,nb);

        // creation d'une facture 
        Facture facture = new Facture(dateResa, reservation);
        
        reservation.setFacture(facture);
        
        // Ajout de la réservation au client
        client.ajoutResa(reservation);
        
        facture.affiche();
        System.out.println("nb pers tot : " + facture.getNb_pres_total());
        System.out.println("nb pers reel : "+ nb.getNb_personnes_reel());
	}
	*/
	
	public void recalculerMontant() {
	    this.montant_ht = 0.0;
	    this.nb_pres_total = 0;
	    this.pers_supp = 0;
	    this.totalTva = 0.0;

	    for (Chambre chambre : this.resa.getLesChambres().keySet()) {
	        Nb_pers_par_chambre nbPersChambre = this.resa.getLesChambres().get(chambre);
	        this.nb_pres_total += nbPersChambre.getNb_personnes_reel();
	        this.montant_ht += chambre.getCategorie().getTarif_de_base() * this.resa.getNb_nuit_prevues();

	        if (nbPersChambre.getNb_personnes_reel() > 2) {
	            this.pers_supp += nbPersChambre.getNb_personnes_reel() - 2;
	            this.totalTva += (this.resa.getNb_nuit_prevues() * chambre.getCategorie().getTarif_de_base() * Facture.getPERS_EN_PLUS()) / Facture.getTVA();
	        }
	    }

	    this.montant_tva = this.montant_ht / Facture.getTVA();
	    this.total = this.montant_ht + this.montant_tva;
	    this.totalTva += this.getMontant_tva();
	}


	@Override
	public String toString() {
		return "Facture [no_facture=" + no_facture + ", montant_ht=" + montant_ht + ", paiment_valide="
				+ ", date_paiement=" + date_paiement + ", resa=" + resa + ", montant_tva=" + montant_tva + ", total="
				+ total + ", nb_chambres=" + nb_chambres + "]";
	}
	
	public void affiche()
	{
		System.out.println(this.toString());
	}
	
	
	
	
	public void setTotalTva(Double totalTva) {
		this.totalTva = totalTva;
	}

	public Double getTotalTva() {
		return totalTva;
	}

	public int getPers_supp() {
		return pers_supp;
	}

	public int getNb_pres_total() {
		return nb_pres_total;
	}

	public static Double getTVA() {
		return TVA;
	}

	public static void setTVA(Double tVA) {
		TVA = tVA;
	}

	public static Double getPERS_EN_PLUS() {
		return PERS_EN_PLUS;
	}

	public static void setPERS_EN_PLUS(Double pERS_EN_PLUS) {
		PERS_EN_PLUS = pERS_EN_PLUS;
	}

	public int getNo_facture() {
		return no_facture;
	}

	public void setNo_facture(int no_facture) {
		this.no_facture = no_facture;
	}

	public static int getNb_factures() {
		return nb_factures;
	}
	
	

	public void setNb_pres_total(int nb_pres_total) {
		this.nb_pres_total = nb_pres_total;
	}

	public static void setNb_factures(int nb_factures) {
		Facture.nb_factures = nb_factures;
	}

	public Double getMontant_ht() {
		return montant_ht;
	}

	public void setMontant_ht(Double montant_ht) {
		this.montant_ht = montant_ht;
	}

	public boolean isPaimentValide() {
        return paimentValide.get();
    }
	
	public void setPaimentValide(boolean valide) {
        this.paimentValide.set(valide);
    }
	
	public BooleanProperty paimentValideProperty() {
        return paimentValide;
    }

	public Date getDate_paiement() {
		return date_paiement;
	}

	public void setDate_paiement(Date date_paiement) {
		this.date_paiement = date_paiement;
	}

	public Reservation getResa() {
		return resa;
	}

	public void setResa(Reservation resa) {
		this.resa = resa;
	}

	public Double getMontant_tva() {
		return montant_tva;
	}

	public void setMontant_tva(Double montant_tva) {
		this.montant_tva = montant_tva;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public int getNb_chambres() {
		return nb_chambres;
	}

	public void setNb_chambres(int nb_chambres) {
		this.nb_chambres = nb_chambres;
	}
}
