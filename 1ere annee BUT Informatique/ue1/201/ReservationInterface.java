import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReservationInterface extends Stage {
    private ListView<Reservation> reservationListView;

    public ReservationInterface() {
        // Création des éléments de l'interface
        Label label = new Label("Rechercher une réservation :");
        TextField inputField = new TextField();
        Button searchButton = new Button("Rechercher");
        reservationListView = new ListView<>();

        // Configuration des éléments
        reservationListView.setPrefHeight(300);
        reservationListView.setStyle("-fx-padding: 10px;");
        reservationListView.setCellFactory(param -> new ListCell<Reservation>() {
            @Override
            protected void updateItem(Reservation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatReservationDetails(item));
                }
                setStyle("-fx-border-color: darkgray;");
            }
        });

        // Écouteur d'événement pour le bouton de recherche
        searchButton.setOnAction(event -> {
            String searchInput = inputField.getText();
            List<Reservation> matchingReservations = searchReservations(searchInput);
            reservationListView.getItems().clear();
            reservationListView.getItems().addAll(matchingReservations);
        });

        // Écouteur d'événement pour la sélection d'une réservation dans la liste
        reservationListView.setOnMouseClicked(event -> {
            Reservation selectedReservation = reservationListView.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                showReservationDetails(selectedReservation);
            }
        });

        // Configuration de la mise en page
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, inputField, searchButton, reservationListView);

        // Configuration de la scène et affichage de la fenêtre
        Scene scene = new Scene(layout, 600, 400);
        this.setTitle("Accueil");
        this.setScene(scene);
        this.setResizable(false);
        this.show();
    }

    private List<Reservation> searchReservations(String searchInput) {
        List<Reservation> matchingReservations = new ArrayList<>();
        String searchLowerCase = searchInput.toLowerCase();
        Pattern initialsPattern = Pattern.compile("\\b[a-zA-Z]");
        Matcher initialsMatcher = initialsPattern.matcher(searchLowerCase);

        StringBuilder initialsBuilder = new StringBuilder();
        while (initialsMatcher.find()) {
            initialsBuilder.append(initialsMatcher.group().toUpperCase());
        }
        String initials = initialsBuilder.toString();

        for (Reservation reservation : Reservation.getAllReservations()) {
            String clientName = reservation.getClient().getNom().toUpperCase();
            String clientFirstName = reservation.getClient().getPrenom().toUpperCase();
            String nameInitials = getClientInitials(clientName, clientFirstName);

            if (String.valueOf(reservation.getNo_resa()).equals(searchInput) ||
                    reservation.getClient().getNo_tel().equals(searchInput) ||
                    nameInitials.startsWith(initials)) {
                matchingReservations.add(reservation);
            }
        }

        return matchingReservations;
    }

    private String getClientInitials(String clientName, String clientFirstName) {
        StringBuilder initialsBuilder = new StringBuilder();
        initialsBuilder.append(clientName.charAt(0));
        initialsBuilder.append(clientFirstName.charAt(0));
        return initialsBuilder.toString().toUpperCase();
    }





    private String formatReservationDetails(Reservation reservation) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String clientDetails = "Numéro de client: " + reservation.getClient().getNo_client() + "\n" +
                "Nom et prénom du client: " + reservation.getClient().getNom() + " " + reservation.getClient().getPrenom();
        String reservationDetails = "Numéro de réservation: " + reservation.getNo_resa() + "\n" +
                "Date d'arrivée: " + dateFormat.format(reservation.getDate_debut()) + "\n" +
                "Date de départ: " + dateFormat.format(reservation.getDate_fin());

        return clientDetails + "\n\n" + reservationDetails;
    }

    private void showReservationDetails(Reservation reservation) {
        Stage detailsStage = new Stage();
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.setTitle("Détails de la réservation");

        GridPane detailsPane = new GridPane();
        detailsPane.setHgap(10);
        detailsPane.setVgap(10);
        detailsPane.setPadding(new Insets(10));

        Text clientLabel = new Text("Client:");
        Text reservationLabel = new Text("Réservation:");

        Text clientDetails = new Text(formatClientDetails(reservation.getClient()));
        Text reservationDetails = new Text(formatReservationDetails(reservation));

        detailsPane.addRow(0, clientLabel, clientDetails);
        detailsPane.addRow(1, reservationLabel, reservationDetails);

        Button editButton = new Button("Modifier réservation");
        if(!reservation.getEtat_resa().equals("Validée"))
        {
        	editButton.setDisable(true);
        }
        editButton.setOnAction(event -> {
        	FenModifResa pageModif = new FenModifResa(reservation);  // Remplacez "PageSuivante" par la classe représentant la page suivante
        	pageModif.show();
        });

        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(event -> detailsStage.close());

        HBox buttonBox = new HBox(10, editButton, closeButton);
        buttonBox.setAlignment(Pos.CENTER);

        detailsPane.add(buttonBox, 0, 2, 2, 1);

        // Ajuster la taille de la fenêtre en fonction du contenu du texte
        detailsPane.getChildren().forEach(node -> GridPane.setFillWidth(node, true));
        detailsPane.prefWidthProperty().bind(detailsStage.widthProperty().multiply(0.8));
        detailsPane.prefHeightProperty().bind(detailsStage.heightProperty().multiply(0.8));

        Scene detailsScene = new Scene(detailsPane);
        detailsStage.setScene(detailsScene);
        detailsStage.sizeToScene();
        detailsStage.showAndWait();
    }



    private String formatClientDetails(Client client) {
        return "Numéro de client: " + client.getNo_client() + "\n" +
                "Nom et prénom: " + client.getNom() + " " + client.getPrenom() + "\n" +
                "Numéro de téléphone: " + client.getNo_tel() + "\n" +
                "Email: " + client.getMail();
    }
}