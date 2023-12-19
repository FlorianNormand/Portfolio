import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AfficheChambre extends Stage {
    private Label lblRecherche = new Label("Recherche de chambres");
    private DatePicker datePickerDebut = new DatePicker();
    private DatePicker datePickerFin = new DatePicker();
    private Label lblResultat = new Label("Résultat de la recherche : ");
    private ListView<Chambre> listViewChambres = new ListView<>();

    public AfficheChambre() {
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(8);

        GridPane resultat = new GridPane();

        // Définir les valeurs par défaut
        LocalDate today = LocalDate.now();
        datePickerDebut.setValue(today);
        datePickerFin.setValue(today.plus(1, ChronoUnit.DAYS));

        // Griser les jours antérieurs à la date du jour dans le premier DatePicker
        datePickerDebut.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(today));
            }
        });

        // Créer un écouteur de changement de valeur pour la date de début
        datePickerDebut.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si la nouvelle date de début est supérieure à la date de fin actuelle
            if (newValue.isAfter(datePickerFin.getValue())) {
                // Mettre à jour la date de fin avec la nouvelle date de début
                datePickerFin.setValue(newValue.plus(1, ChronoUnit.DAYS));
            }
        });

        // Griser les jours avant la date de début dans le deuxième DatePicker
        datePickerFin.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(datePickerDebut.getValue().plus(1, ChronoUnit.DAYS)));
            }
        });

        // Créer le bouton "Rechercher"
        Button btnRechercher = new Button("Rechercher");
        btnRechercher.setOnAction(e -> rechercherChambres());

        // Définir la cellule personnalisée pour afficher les informations des chambres
        listViewChambres.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Chambre chambre, boolean empty) {
                super.updateItem(chambre, empty);
                if (empty || chambre == null) {
                    setText(null);
                } else {
                    // Définir le texte à afficher pour chaque chambre
                    setText("Numéro : " + chambre.getNo_chambre() + ", Type : " + chambre.getCategorie().getLibelle());
                }
            }
        });

        root.getChildren().addAll(lblRecherche, datePickerDebut, datePickerFin, btnRechercher, lblResultat, resultat, listViewChambres);

        Scene scene = new Scene(root);
        this.setTitle("Affichage des chambres");
        this.setScene(scene);
        this.setMinHeight(200);
        this.setMinWidth(200);
        this.setResizable(true);
        this.show();
    }

    private void rechercherChambres() {
        LocalDate debut = datePickerDebut.getValue();
        LocalDate fin = datePickerFin.getValue();

        Date dateDebut = convertToLocalDateToDate(debut);
        Date dateFin = convertToLocalDateToDate(fin);

        ArrayList<Chambre> lesChambres = getChambresReserveesEntreDates(dateDebut, dateFin);

        // Create an ObservableList from the ArrayList
        ObservableList<Chambre> chambresObservableList = FXCollections.observableArrayList(lesChambres);

        // Set the ObservableList as the items of the ListView
        listViewChambres.setItems(chambresObservableList);
    }

    public static ArrayList<Chambre> getChambresReserveesEntreDates(Date dateDebut, Date dateFin) {
        ArrayList<Chambre> chambresReservees = new ArrayList<>();

        for (Reservation reservation : Reservation.getAllReservations()) {
            HashMap<Chambre, Nb_pers_par_chambre> lesChambres = reservation.getLesChambres();
            for (Chambre chambre : lesChambres.keySet()) {
                if (reservation.getDate_debut().compareTo(dateFin) <= 0 &&
                        reservation.getDate_fin().compareTo(dateDebut) >= 0) {
                    chambresReservees.add(chambre);
                    break;
                }
            }
        }

        return chambresReservees;
    }

    public static Date convertToLocalDateToDate(LocalDate localDate) {
        // Obtenir l'année, le mois et le jour à partir de LocalDate
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        // Créer un objet Calendar
        Calendar calendar = Calendar.getInstance();

        // Définir l'année, le mois et le jour dans le calendrier
        calendar.set(year, month - 1, day);

        // Convertir le calendrier en Date
        Date date = calendar.getTime();

        return date;
    }
}
