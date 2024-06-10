import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AfficherFacture extends Stage{
	public AfficherFacture(Facture f)
	{
		VBox root = new VBox();
		root.setPadding(new Insets(10));
		root.setSpacing(50);
		/* haut de la page */
		HBox hautPage = new HBox();
		hautPage.setSpacing(300);
		Label titre = new Label("Facture");
		Label infos = new Label("Hôtel du Cheval Blanc ****\n"+ "6 Route des Pins\n"+ "13 000 MARSEILLE");
		hautPage.getChildren().addAll(titre,infos);
		
		HBox entete = new HBox();
		entete.setSpacing(200);
		Label client = new Label("Client : \n"
				+ f.getResa().getClient().getCivilite()
				+ f.getResa().getClient().getNom()+ f.getResa().getClient().getPrenom() + "\n"
				+ f.getResa().getClient().getAdresse1()+"\n"+f.getResa().getClient().getAdresse2());
        GridPane tabEntete = new GridPane();
        tabEntete.setPadding(new Insets(10));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints colConstraints = new ColumnConstraints();
        tabEntete.getColumnConstraints().addAll(col1, col2,colConstraints);
        

        Label date = new Label("Date :");
        Label fo_facture = new Label("Facture N° :");
        LocalDate dateDuJour = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateFormatted = dateDuJour.format(formatter);  
        Label dateValue = new Label(dateFormatted);
        Label factureValue = new Label(String.valueOf(f.getNo_facture()));
        tabEntete.add(date, 0, 0);
        tabEntete.add(fo_facture, 0, 1);
        tabEntete.add(dateValue, 1, 0);
        tabEntete.add(factureValue, 1, 1);
        
        tabEntete.setStyle("-fx-grid-lines-visible: true;");
        
        
        String pers = "personnes";
        if(f.getNb_pres_total() == 1)
        {
        	pers = "personne";
        }
        
        LocalDate dateDeb = f.getResa().getDate_debut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateFin = f.getResa().getDate_fin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);
        String dateDebutFormatted = dateDeb.format(dateFormatter);
        String dateFinFormatted = dateFin.format(dateFormatter);

        Label infosSejour = new Label("Informations Séjour\n"
                + "Séjour du " + dateDebutFormatted + " au " + dateFinFormatted + "\n"
                + f.getNb_pres_total() + " " + pers);

        entete.getChildren().addAll(client, tabEntete);
        
        GridPane tabPrix = new GridPane();
        tabPrix.setGridLinesVisible(true);
        tabPrix.addRow(0,
                new Label("Description"), new Label("Quantité"), new Label("PU TTC"),
                new Label("% TVA"), new Label("Total TVA"), new Label("Total TTC")
        );
        int nbLig = 0;
        for (Chambre chambre : f.getResa().getLesChambres().keySet()) {
            nbLig++;
            tabPrix.addRow(nbLig,
                    new Label("Séjour :\nNuit en " + chambre.getCategorie().getLibelle()),
                    new Label(String.valueOf(f.getResa().getNb_nuit_prevues())),
                    new Label(String.valueOf(chambre.getCategorie().getTarif_de_base() + "€")),
                    new Label(String.valueOf(Facture.getTVA()) + "%"),
                    new Label(String.valueOf(f.getMontant_tva()) + "€"),
                    new Label(String.valueOf(f.getResa().getNb_nuit_prevues() * chambre.getCategorie().getTarif_de_base() + "€"))
            );

            if (f.getResa().getLesChambres().get(chambre).getNb_personnes_reel() > 2) {
                nbLig++;
                tabPrix.addRow(nbLig,
                        new Label("Nuit pour " + f.getPers_supp() + " " + pers + " supplémentaire"),
                        new Label(String.valueOf(f.getResa().getNb_nuit_prevues())),
                        new Label(String.valueOf(chambre.getCategorie().getTarif_de_base() * Facture.getPERS_EN_PLUS() + "€")),
                        new Label(String.valueOf(Facture.getTVA()) + "%"),
                        new Label(String.valueOf((f.getResa().getNb_nuit_prevues()) * chambre.getCategorie().getTarif_de_base() * Facture.getPERS_EN_PLUS() / Facture.getTVA()) + "€"),
                        new Label(String.valueOf((f.getResa().getNb_nuit_prevues()) * chambre.getCategorie().getTarif_de_base() * Facture.getPERS_EN_PLUS() + "€"))
                );
            }
        }

        nbLig++;
        tabPrix.add(new Label("Total TTC"), 0, nbLig);
        tabPrix.add(new Label(String.valueOf(f.getTotal() + "€")), 5, nbLig);
        
        nbLig++;
        tabPrix.add(new Label("Dont TVA"), 0, nbLig);
        tabPrix.add(new Label(String.valueOf((f.getTotalTva()) + "€")), 5, nbLig);

        HBox basPage = new HBox();
        Button bnFermer = new Button("Fermer");
        Button bnValiderPaiment = new Button("Valider le paiement");
        basPage.getChildren().addAll(bnFermer,bnValiderPaiment);
		bnFermer.setOnAction(e -> this.close() );
		// Dans la méthode AfficherFacture
		bnValiderPaiment.setOnAction(e -> {
		    // Création de la boîte de dialogue personnalisée
		    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
		    confirmationDialog.setTitle("Confirmation");
		    confirmationDialog.setHeaderText("Validation du paiement");

		    // Configuration de la disposition personnalisée
		    GridPane content = new GridPane();
		    content.setHgap(10);
		    content.setVgap(10);

		    // Ajout du message personnalisé
		    Label messageLabel = new Label("Êtes-vous sûr de vouloir valider le paiement ?");
		    messageLabel.setWrapText(true);
		    content.add(messageLabel, 0, 0);

		    Label descriptionLabel = new Label("Ceci enregistrera la facture et passera l'état de la réservation à 'Arrivée enregistrée'.");
		    descriptionLabel.setWrapText(true);
		    content.add(descriptionLabel, 0, 1);

		    confirmationDialog.getDialogPane().setContent(content);

		    // Configuration des boutons de la boîte de dialogue
		    ButtonType ouiButton = new ButtonType("Oui");
		    ButtonType nonButton = new ButtonType("Non");
		    confirmationDialog.getButtonTypes().setAll(ouiButton, nonButton);

		    // Attente de la réponse de l'utilisateur
		    confirmationDialog.showAndWait().ifPresent(response -> {
		        if (response == ouiButton) {
		            f.setPaimentValide(true);
		            showConfirmationDialog("Action effectuée", "Le paiement a été validé.");
		            bnValiderPaiment.setDisable(true);
		            // Mettre à jour l'état de la réservation à "Arrivée enregistrée"
		            f.getResa().setEtat_resa("Arrivée enregistrée");
		        }
		    });
		});

		
		basPage.setSpacing(30);
		
		Label lbletatPaiement = new Label("Etait du paiement : ");
        Label pNonValide = new Label("Validé");
        Label pValide = new Label("Non validé");
        
        GridPane etatPaiement = new GridPane();
        
        pNonValide.visibleProperty().bind(f.paimentValideProperty());
        pValide.visibleProperty().bind(f.paimentValideProperty().not());
        
        etatPaiement.addRow(0,lbletatPaiement,pNonValide);
        etatPaiement.add(pValide, 1,0 );

        root.getChildren().addAll(hautPage, entete, infosSejour,tabPrix,etatPaiement,basPage);
		Scene scene = new Scene(root);
		this.setTitle("Facture");
		this.setScene(scene);
		this.setResizable(false);
		this.show();
	}
	
	private void showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

