package com.application.mark1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PatientPageController implements Initializable {

    @FXML
    private AnchorPane Login;

    @FXML
    private Button Login_Cancel;

    @FXML
    private CheckBox Login_Checkbox;

    @FXML
    private PasswordField Login_PASSWORD;

    @FXML
    private TextField Login_PatientID;

    @FXML
    private TextField Login_ShowPASSWORD;

    @FXML
    private ComboBox<String> Login_User;

    @FXML
    private Button Login_login;


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private final AlertSystem alert = new AlertSystem();


    @FXML
    void loginAccount(ActionEvent event) {
        if(Login_PatientID.getText().isEmpty()||Login_PASSWORD.getText().isEmpty()){
            alert.errorMessage("Incorrect Username/Password");
        }else{
            try{
                String sql = "SELECT * FROM Patient WHERE P_Id = ? AND PASS = ? AND Date_Delete IS NULL;";
                connect = Database.connectDB();
                assert connect != null;
                prepare = connect.prepareStatement(sql);
                prepare.setString(1,Login_PatientID.getText());
                prepare.setString(2,Login_PASSWORD.getText());
                result = prepare.executeQuery();
                if(!result.next()){
                    if(Login_ShowPASSWORD.isVisible()){
                        if(!Login_ShowPASSWORD.getText().equals(Login_PASSWORD.getText())){
                            Login_ShowPASSWORD.setText(Login_PASSWORD.getText());
                        }else{
                            if(!Login_ShowPASSWORD.getText().equals(Login_PASSWORD.getText())) {
                                Login_PASSWORD.setText(Login_ShowPASSWORD.getText());
                            }
                        }
                    }
                    alert.errorMessage("You might be not registered, please contact the Consultant doctor for registration");
                } else{
                        alert.successMessage("Login successfully");
                        Data.PatientID = Login_PatientID.getText();
                        Parent root  =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PatientDash.fxml")));
                        Stage stage = new Stage();
                        stage.setTitle("Patient Monitoring System|Patient Main Dashboard");
                        stage.setScene(new Scene(root));
                        stage.show();
                }
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void loginShowPassword(ActionEvent event) {
        if(Login_Checkbox.isSelected()){
            Login_ShowPASSWORD.setText(Login_PASSWORD.getText());
            Login_PASSWORD.setVisible(false);
            Login_ShowPASSWORD.setVisible(true);
        }else{
            Login_PASSWORD.setText(Login_ShowPASSWORD.getText());
            Login_ShowPASSWORD.setVisible(true);
            Login_PASSWORD.setVisible(true);
        }

    }

    @FXML
    public void switchPage(){
        if(Objects.equals(Login_User.getSelectionModel().getSelectedItem(), "Doctor Portal")) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("DoctorPage.fxml")));
                Stage stage = new Stage();
                stage.setMinWidth(320);
                stage.setMinHeight(500);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(Objects.equals(Login_User.getSelectionModel().getSelectedItem(), "Patient Portal")) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PatientPage.fxml")));
                Stage stage = new Stage();
                stage.setMinWidth(320);
                stage.setMinHeight(500);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Login_User.getScene().getWindow().hide();
    }
    public void userList(){
        List<String> listU = new ArrayList<>();
        Collections.addAll(listU, Users.user);
        ObservableList<String> listData = FXCollections.observableList(listU);
        Login_User.setItems(listData);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userList();
    }
}
