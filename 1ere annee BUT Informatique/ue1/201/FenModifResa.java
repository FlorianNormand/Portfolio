import java.util.Date;
import java.util.HashMap;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;  // Ajout de l'import pour DateCell
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FenModifResa extends Stage {
    private Label txtClient = new Label();
    private Label lblClient = new Label("Client :");
    private Label txtNoResa = new Label();
    private Label lblNoResa = new Label("Numéro de réservation :");
    private Label lblNbPerso = new Label("Nombre de personnes :");
    private Label txtNbPerso = new Label();
    private Label lblDateDebut = new Label("Date de début :");
    private DatePicker datePickerDebut = new DatePicker();
    private Label lblDateFin = new Label("Date de fin :");
    private DatePicker datePickerFin = new DatePicker();
    private Label lblDateResa = new Label("Date de réservation :");
    private Label txtDateResa = new Label();
    private Label lblNbNuit = new Label("Nombre de nuit(s) prévue(s) :");
    IntegerProperty nbNuitProperty = new SimpleIntegerProperty();
    private Label lblEtatResa = new Label("État de la réservation :");
    private ComboBox<String> txtEtatResa = new ComboBox<String>();
    private Reservation resa;
    private Label txtDateDebut = new Label();
    private Label txtNbNuit = new Label();


    private Button bnValider = new Button("Valider");
    private Button bnRetour = new Button("Retour");

    public FenModifResa(Reservation r) {
        this.resa = r;
        VBox root = new VBox();
        GridPane tabHaut = new GridPane();
        HBox ligneCli = new HBox();
        HBox ligneNoResa = new HBox();

        ligneCli.getChildren().addAll(lblClient, txtClient);
        ligneNoResa.getChildren().addAll(lblNoResa, txtNoResa);

        if (r.getFacture().getNb_pres_total() < 0) {
            showAlert("Erreur", "Le nombre de personne ne peut pas être négatif");
            }
        
        txtClient.setText(String.valueOf(r.getClient().getNo_client()));
        txtNoResa.setText(String.valueOf(r.getNo_resa()));
        txtNbPerso.setText(String.valueOf(r.getFacture().getNb_pres_total()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        datePickerDebut.setValue(r.getDate_debut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        datePickerDebut.setDayCellFactory(picker -> new DateCell() {  // Définition de la date minimale pour datePickerDebut
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });

        datePickerFin.setValue(r.getDate_fin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        datePickerFin.setDayCellFactory(picker -> new DateCell() {  // Définition de la date minimale pour datePickerFin
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });

        txtDateResa.setText(String.valueOf(sdf.format(r.getDate_resa())));

        ChangeListener<LocalDate> dateChangeListener = (observable, oldValue, newValue) -> {
            // Mettre à jour le nombre de nuits prévues
            updateInvoice();
        };

        datePickerDebut.valueProperty().addListener(dateChangeListener);
        datePickerFin.valueProperty().addListener(dateChangeListener);


        datePickerDebut.valueProperty().addListener(dateChangeListener);
        datePickerFin.valueProperty().addListener(dateChangeListener);

        datePickerFin.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                LocalDate debut = datePickerDebut.getValue();
                if (debut != null && item.isBefore(debut)) {
                    setDisable(true); // Grise les jours antérieurs à la date de début
                }
            }
        });

        
        txtEtatResa.getItems().addAll("Close", "Validée", "Arrivée enregistrée");
        txtEtatResa.setValue("Arrivée enregistrée");

     // Calcul initial du nombre de nuits
        LocalDate debut = datePickerDebut.getValue();
        LocalDate fin = datePickerFin.getValue();
        int nbNuit = (int) ChronoUnit.DAYS.between(debut, fin);
        nbNuitProperty.set(nbNuit);
        txtNbNuit.setText(String.valueOf(nbNuitProperty.get()));

        updateTotalOccupants();

        txtDateDebut.setText(String.valueOf(sdf.format(r.getDate_debut())));

        tabHaut.addRow(0, lblClient, txtClient);
        tabHaut.addRow(1, lblNoResa, txtNoResa);
        tabHaut.addRow(2, lblNbPerso, txtNbPerso);
        tabHaut.addRow(3, lblDateDebut, txtDateDebut);
        tabHaut.addRow(4, lblDateFin, datePickerFin);
        tabHaut.addRow(5, lblDateResa, txtDateResa);
        tabHaut.addRow(6, lblNbNuit, txtNbNuit);
        tabHaut.addRow(7, lblEtatResa, txtEtatResa);

        VBox afficherChambres = new VBox();
        GridPane tabChambres = new GridPane();
        tabChambres.setStyle("-fx-grid-lines-visible: true;");
        Label titreChambre = new Label("Chambres liées à la réservation");
        tabChambres.addRow(0, new Label("Catégorie"), new Label("N° Chambre"), new Label("Nombre d'occupant"));
        int nbLig = 1;
        
		
        for (Chambre chambre : r.getLesChambres().keySet()) {
            nbLig++;
            Nb_pers_par_chambre nbPersChambre = r.getLesChambres().get(chambre);
            TextField nbPers = new TextField(String.valueOf(nbPersChambre.getNb_personnes_prevues()));

            nbPers.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && !newValue.isEmpty()) {
                    int nbPersonnes;
                    try {
                        nbPersonnes = Integer.parseInt(newValue);
                        if (nbPersonnes < 0) {
                            nbPers.setText(oldValue); // Restore the previous value
                            showAlert("Erreur", "Le nombre d'occupants ne peut pas être inférieur à zéro.");
                        } else if (nbPersonnes > chambre.getCategorie().getCapacite()) {
                            nbPers.setText(oldValue); // Restore the previous value
                            showAlert("Erreur", "Nombre de personnes supérieur à la capacité de la catégorie de chambre qui est de "+String.valueOf(chambre.getCategorie().getCapacite()));
                        } else {
                            nbPersChambre.setNb_personnes_prevues(nbPersonnes);
                            nbPersChambre.setNb_personnes_reel(nbPersonnes); // Update the actual number of occupants
                            resa.getFacture().recalculerMontant();
                        }
                    } catch (NumberFormatException e) {
                        nbPers.setText(oldValue); // Restore the previous value
                        showAlert("Erreur", "Veuillez entrer un nombre entier valide pour le nombre d'occupants.");
                    }
                }
            });


            tabChambres.add(new Label(chambre.getCategorie().getLibelle()), 0, nbLig);
            tabChambres.add(new Label(String.valueOf(chambre.getNo_chambre())), 1, nbLig);
            tabChambres.add(nbPers, 2, nbLig);
            nbPers.textProperty().addListener((observable, oldValue, newValue) -> updateTotalOccupants());
        }


        
        bnValider.setOnAction(e -> {
            // Vérifier si le nombre d'occupants par chambre est inférieur à 1
            boolean nombreOccupantsValide = true;
            for (Chambre chambre : resa.getLesChambres().keySet()) {
                Nb_pers_par_chambre nbPersChambre = resa.getLesChambres().get(chambre);
                int nbPersonnes = nbPersChambre.getNb_personnes_prevues();
                if (nbPersonnes < 1) {
                    nombreOccupantsValide = false;
                    break;
                }
            }

            if (!nombreOccupantsValide) {
                showAlert("Erreur", "Le nombre d'occupants par chambre doit être supérieur ou égal à 1.");
                return; // Arrêter le traitement et ne pas valider
            }

            // Le nombre d'occupants par chambre est valide, continuer avec la validation

            // Afficher une fenêtre de confirmation
            Alert confirmation = new Alert(AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Êtes-vous sûr de vouloir valider ?");
            confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            // Récupérer la réponse de l'utilisateur
            ButtonType choix = confirmation.showAndWait().orElse(ButtonType.NO);

            if (choix == ButtonType.YES) {
                // Afficher une fenêtre de validation
                Alert validation = new Alert(AlertType.CONFIRMATION);
                validation.setTitle("Validation");
                validation.setHeaderText(null);
                validation.setContentText("Voulez-vous ouvrir la facture ?");

                // Ajouter les boutons personnalisés pour la fenêtre de validation
                ButtonType openButton = new ButtonType("Ouvrir");
                ButtonType cancelButton = new ButtonType("Non");
                validation.getButtonTypes().setAll(openButton, cancelButton);

                // Récupérer la réponse de l'utilisateur
                ButtonType validationChoice = validation.showAndWait().orElse(cancelButton);

                if (validationChoice == openButton) {
                    // Ouvrir la fenêtre de la facture
                    AfficherFacture facture = new AfficherFacture(resa.getFacture());
                    facture.show();
                }
            }
        });

		
		
		afficherChambres.getChildren().addAll(titreChambre,tabChambres);
        bnRetour.setOnAction(e -> this.close());

        HBox boutons = new HBox(15);
        boutons.getChildren().addAll(bnRetour, bnValider);

        tabHaut.setHgap(10);
        tabHaut.setVgap(10);

        boutons.setPadding(new Insets(10));

        root.getChildren().addAll(tabHaut, afficherChambres, boutons);

        boutons.setPadding(new Insets(10));
        boutons.setAlignment(Pos.BASELINE_RIGHT);

        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 450, 350);
        this.setTitle("Modification de réservation");
        this.setScene(scene);
        this.setResizable(false);
        this.show();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }  
    
    private void updateInvoice() {
        LocalDate debut = datePickerDebut.getValue();
        LocalDate fin = datePickerFin.getValue();
        int nbNuit = (int) ChronoUnit.DAYS.between(debut, fin);

        // Update the invoice with the new number of nights
        resa.getFacture().getResa().setNb_nuit_prevues(nbNuit);

        // Calculate the total number of occupants
        int totalOccupants = 0;
        for (Nb_pers_par_chambre nbPersChambre : resa.getLesChambres().values()) {
            totalOccupants += nbPersChambre.getNb_personnes_prevues();
        }

        // Update the total number of persons in the invoice
        resa.getFacture().setNb_pres_total(totalOccupants);

        // Recalculate the invoice amount
        resa.getFacture().recalculerMontant();

        resa.setNb_nuit_prevues(nbNuit);
        resa.getFacture().recalculerMontant();

        // Update the value of txtNbNuit
        txtNbNuit.setText(String.valueOf(nbNuit));
    }

    
    private void updateTotalOccupants() {
        int totalOccupants = 0;

        for (Nb_pers_par_chambre nbPersChambre : resa.getLesChambres().values()) {
            totalOccupants += nbPersChambre.getNb_personnes_prevues();
        }

        resa.getFacture().setNb_pres_total(totalOccupants);
        txtNbPerso.setText(String.valueOf(totalOccupants));
    }



}