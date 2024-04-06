package com.application.mark1;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertSystem {
    private Alert alert;
    public void errorMessage(String message){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void successMessage(String message){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public boolean confirmationMessage(String message) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> option = alert.showAndWait();
        return option.get().equals(ButtonType.OK);
    }
}
