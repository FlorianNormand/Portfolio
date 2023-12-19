import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainTest {
    public static void main(String[] args) {
        // Création d'une catégorie de chambre
        Categorie categorie = new Categorie(3,50.0,"Standard");

        // Création d'une chambre
        Chambre chambre = new Chambre("Simple", 100.0, "Chambre simple", categorie, null, null);

        // Création d'une prestation
        Prestation prestation1 = new Prestation("Bieres", 7.0,"Picon biere");
        Prestation prestation2 = new Prestation("Boisson acolisee", 20.0,"JagerBonb");

        // Ajout des prestations à la chambre
        chambre.ajoutPresta(prestation1);
        chambre.ajoutPresta(prestation2);

        // Affichage des prestations de la chambre
        chambre.listerPresta();

        // Création d'un client
        Client client = new Client("M.", "Dupont", "Jean", "1 rue de Paris", "", "75000", "Paris", "0123456789", "jdupont@example.com");

        // Création d'une réservation pour le client
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateResa = null, dateDebut= null, dateFin= null;
        try {
	        dateResa = sdf.parse("27/06/2023");
	        dateDebut = sdf.parse("30/07/2023");
	        dateFin = sdf.parse("02/08/2023");
        }catch (ParseException e) {
            e.printStackTrace();
        }

        
        Nb_pers_par_chambre nb = new Nb_pers_par_chambre(12);
        
        Reservation reservation = new Reservation(dateResa, dateDebut, dateFin, "En cours", client, chambre,nb);

        // creation d'une facture 
        Facture facture = new Facture(150.0, true, dateResa, reservation);
        
        reservation.setFacture(facture);
        
        // Ajout de la réservation au client
        client.ajoutResa(reservation);

        // Affichage des réservations du client
        client.listerResa();

        // Création d'une facture pour la réservation
        Facture facture2 = new Facture(200.0, false, null, reservation);
        
     // Création d'une réservation pour le client
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        Date dateResa2 = null, dateDebut2= null, dateFin2= null;
        try {
        	dateResa2 = sdf2.parse("25/05/2022");
            dateDebut2 = sdf2.parse("26/05/2022");
            dateFin2 = sdf2.parse("31/05/2022");
        }catch (ParseException e) {
            e.printStackTrace();
        }

        

        // Modification de la réservation dans la facture
        Reservation reservation2 = new Reservation(dateResa2, dateDebut2, dateFin2, "Validee", client, chambre,nb);
        facture.modifResa(reservation2);

        // Affichage des informations de la facture
        facture.affiche();
    }
}
