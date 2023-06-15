import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.stage.Stage;

public class Principale extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    public void start(Stage f) throws Exception {
        // Création des catégories
        Categorie categorie1 = new Categorie(3, 60.0, "Standard");
        Categorie categorie2 = new Categorie(2, 80.0, "Supérieure");

        // Création des chambres
        Chambre chambre1 = new Chambre(true, categorie1);
        Chambre chambre2 = new Chambre(true, categorie2);

        // Création des clients
        Client client1 = new Client("M.", "Dupont", "Jean", "1 rue de Paris", "", "75000", "Paris", "0123456789",
                "jdupont@example.com");
        Client client2 = new Client("Mme", "Martin", "Sophie", "2 avenue du Soleil", "", "69000", "Lyon", "0987654321",
                "smartin@example.com");

        Client client3 = new Client("M", "Cramté", "Alfred", "2 rue Apagno", "", "49000", "Angers", "01472558369",
                "smartin@example.com");
        // Création des réservations pour les clients
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateResa1 = null, dateDebut1 = null, dateFin1 = null;
        Date dateResa2 = null, dateDebut2 = null, dateFin2 = null;
        Date dateResa3 = null, dateDebut3 = null, dateFin3 = null;

        try {
            dateResa1 = sdf.parse("27/06/2023");
            dateDebut1 = sdf.parse("30/07/2023");
            dateFin1 = sdf.parse("04/08/2023");

            dateResa2 = sdf.parse("01/07/2023");
            dateDebut2 = sdf.parse("10/08/2023");
            dateFin2 = sdf.parse("15/08/2023");
            
            dateResa3 = sdf.parse("12/08/2023");
            dateDebut3 = sdf.parse("15/09/2023");
            dateFin3 = sdf.parse("117/09/2023");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Nb_pers_par_chambre nb1 = new Nb_pers_par_chambre(2, chambre1);
        Nb_pers_par_chambre nb2 = new Nb_pers_par_chambre(1, chambre2);

        Reservation reservation1 = new Reservation(dateResa1, dateDebut1, dateFin1, "Validée", client1,
                chambre1, nb1);
        Reservation reservation2 = new Reservation(dateResa2, dateDebut2, dateFin2, "Arrivée Enregistrée",
                client1, chambre2, nb2);
        Reservation reservation3 = new Reservation(dateResa3, dateDebut3, dateFin3, "Close",
                client3, chambre2, nb2);
        /*reservation1.ajouterChambre(chambre2, nb2);*/
        // Création des factures pour les réservations
        Facture facture1 = new Facture(dateResa1, reservation1);
        /*Facture facture2 = new Facture(dateResa2, reservation2);*/

        reservation1.setFacture(facture1);
        /*reservation2.setFacture(facture2);*/

        // Ajout des réservations aux clients
        client1.ajoutResa(reservation1);
        /*client1.ajoutResa(reservation2);*/

        // Affichage des fenêtres de modification des réservations
        f = new FenModifResa(reservation1);
        f.show();

        /*Stage f2 = new FenModifResa(reservation2);
        f2.show();*/
    }
}
