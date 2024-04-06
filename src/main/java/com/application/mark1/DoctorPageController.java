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

public class DoctorPageController implements Initializable {


    @FXML
    private AnchorPane Login;

    @FXML
    private Button Login_Cancel;

    @FXML
    private CheckBox Login_Checkbox;

    @FXML
    private TextField Login_DoctorID;

    @FXML
    private Hyperlink Login_New;

    @FXML
    private PasswordField Login_PASSWORD;

    @FXML
    private TextField Login_ShowPASSWORD;

    @FXML
    private ComboBox<String> Login_User;

    @FXML
    private Button Login_login;

    @FXML
    private AnchorPane Register;

    @FXML
    private CheckBox Register_CheckBox;

    @FXML
    private TextField Register_DoctorID;

    @FXML
    private TextField Register_Doctor_USERNAME;

    @FXML
    private TextField Register_Email;

    @FXML
    private Hyperlink Register_New;

    @FXML
    private PasswordField Register_PASSWORD;

    @FXML
    private TextField Register_ShowPASSWORD;

    @FXML
    private Button Register_register;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private final AlertSystem alert = new AlertSystem();

    @FXML
    public void RegisterAccount() {
        if(Register_Doctor_USERNAME.getText().isEmpty() || Register_Email.getText().isEmpty()||Register_PASSWORD.getText().isEmpty()){
            alert.errorMessage("Please fill all the blank fields");
        }   else{
            String checkDoctorID = "SELECT * FROM Doctor WHERE D_DID = '" + Register_DoctorID.getText() + "';";
            connect = Database.connectDB();
            try{
                assert connect != null;
                prepare = connect.prepareStatement(checkDoctorID);
                result = prepare.executeQuery();

                if(result.next()){
                    alert.errorMessage(Register_DoctorID.getText() + " is already taken");
                } else if (Register_PASSWORD.getText().length() < 8) {
                    alert.errorMessage("Invalid password, At least 8 character needed");
                }else{
                    String insertData = "INSERT INTO Doctor(D_Name,Email,D_DID,Pass,D_Date,D_Status)" +"VALUES(?,?,?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);
                    Date date = new Date();
                    java.sql.Date sqDate = new java.sql.Date(date.getTime());
                    prepare.setString(1,Register_Doctor_USERNAME.getText());
                    prepare.setString(2,Register_Email.getText());
                    prepare.setString(3,Register_DoctorID.getText());
                    prepare.setString(4,Register_PASSWORD.getText());
                    prepare.setString(5,String.valueOf(sqDate));
                    prepare.setString(6,"Confirm");
                    prepare.executeUpdate();
                    alert.successMessage("Registered Successfully!");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void loginAccount(ActionEvent event) {
    if(Login_DoctorID.getText().isEmpty()||Login_PASSWORD.getText().isEmpty()){
        alert.errorMessage("Empty Data Fields");
    }else{
        try{
        String sql = "SELECT * FROM Doctor WHERE D_DID = ? AND PASS = ? AND Delete_Date IS NULL;";
        connect = Database.connectDB();

            String checkStatus = "SELECT D_Status FROM Doctor WHERE D_DID = '" + Login_DoctorID.getText() + "' AND D_Status = 'Confirm';";

            assert connect != null;
            prepare = connect.prepareStatement(checkStatus);
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
                alert.errorMessage("Need the confirmation of the Admin");
                } else{
                prepare = connect.prepareStatement(sql);
                prepare.setString(1,Login_DoctorID.getText());
                prepare.setString(2,Login_PASSWORD.getText());
                result = prepare.executeQuery();
                    if(result.next()){
                        Data.Doctor_Id = result.getString("D_DID");
                        Data.Doctor_Username = result.getString("D_Name");

                    alert.successMessage("Login successfully");
                    Parent root  =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("DoctorDash.fxml")));
                    Stage stage = new Stage();
                    stage.setTitle("Patient Monitoring System|Doctor Main Dashboard");
                    stage.setScene(new Scene(root));
                    stage.show();
                    }else{
                        alert.errorMessage("Incorrect Username/Password");
                    }
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
            Login_ShowPASSWORD.setText(Login_PASSWORD.getText());
            Login_PASSWORD.setVisible(true);
            Login_ShowPASSWORD.setVisible(false);
        }
    }

    private String DoctorID;
    public void Register_DoctorID()  {
        String doctorId = "DID-";
        String sql = "SELECT MAX(D_ID) FROM Doctor";
        int tempID = 0;
        connect = Database.connectDB();
        try{
            if(!Register_ShowPASSWORD.isVisible()){
                if(!Register_ShowPASSWORD.getText().equals(Register_PASSWORD.getText())){
                    Register_ShowPASSWORD.setText(Register_PASSWORD.getText());
                }else{
                    if(!Register_ShowPASSWORD.getText().equals(Register_PASSWORD.getText())){
                        Register_PASSWORD.setText(Register_ShowPASSWORD.getText());
                    }
                }
            }
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if(result.next()){
                tempID = result.getInt("MAX(D_Id)");
            }
            if(tempID == 0){
            tempID += 1;
                doctorId +=tempID;
            }else{
                doctorId += (tempID+1);
            }
            Register_DoctorID.setText(doctorId);
            Register_DoctorID.setDisable(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void registerShowPassword (){
            if(Register_CheckBox.isSelected()){
                Register_ShowPASSWORD.setText(Register_PASSWORD.getText());
                Register_PASSWORD.setVisible(false);
                Register_ShowPASSWORD.setVisible(true);
            }else{
                Register_PASSWORD.setText(Register_ShowPASSWORD.getText());
                Register_ShowPASSWORD.setVisible(true);
                Register_PASSWORD.setVisible(true);
            }
        }
    public void userList(){
        List<String> listU = new ArrayList<>();
        Collections.addAll(listU, Users.user);
        ObservableList<String> listData = FXCollections.observableList(listU);
        Login_User.setItems(listData);
    }
    public void switchPage(){
        if(Objects.equals(Login_User.getSelectionModel().getSelectedItem(), "Doctor Portal")) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("DoctorPage.fxml")));
                Stage stage = new Stage();
                stage.setMinWidth(320);
                stage.setMinHeight(600);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if(Objects.equals(Login_User.getSelectionModel().getSelectedItem(), "Patient Portal")) {
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
    @FXML
    public void switchForm(ActionEvent event) {
    if(event.getSource() == Login_New){
    Login.setVisible(false);
    Register.setVisible(true);
    }else if(event.getSource() == Register_New){
    Login.setVisible(true);
    Register.setVisible(false);
    }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userList();
        Register_DoctorID();
    }
}
