package com.application.mark1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class DoctorDashController implements Initializable {
    AlertSystem alert = new AlertSystem();
    @FXML
    private TextArea UpdateDoctorAddress;
    @FXML
    private Button UpdateDeleteBtn;
    @FXML
    private Button UpdateResetBtn;
    @FXML
    private Button UpdateDoctorBtn;
    @FXML
    private TextField UpdateDoctorEmail;

    @FXML
    private TextField UpdateDoctorID;

    @FXML
    private TextField UpdateDoctorName;

    @FXML
    private TextField UpdateDoctorPass;

    @FXML
    private TextField UpdateDoctorPhoneNo;

    @FXML
    private AnchorPane UpdateDoctorProfile;

    @FXML
    private TextField UpdateDoctorSpecialised;
    @FXML
    private ComboBox<String> UpdateDoctorGender;

    @FXML
    private Button DeleteBtn;

    @FXML
    private AnchorPane DoctorDash;

    @FXML
    private TableView<PatientData> DoctorMain;

    @FXML
    private Button Doctor_Dash;

    @FXML
    private Label Doctor_ID;

    @FXML
    private Button Doctor_Patient;

    @FXML
    private Button Doctor_Profile;

    @FXML
    private TextField HealthIssue;

    @FXML
    private TextField PatientBloodPressure;

    @FXML
    private TableColumn<PatientData, Integer> PatientBps;

    @FXML
    private Button PatientCancelBtn;
    @FXML
    private Button PatientNextCancelBtn;
    @FXML
    private TextField PatientECG;

    @FXML
    private TableColumn<PatientData, Integer> PatientECGs;

    @FXML
    private AnchorPane PatientForm;

    @FXML
    private ComboBox<String> PatientGender;

    @FXML
    private TableColumn<PatientData,String> PatientGenders;

    @FXML
    private TextField PatientID;

    @FXML
    private TableColumn<PatientData,Integer> PatientIDs;

    @FXML
    private TableColumn<PatientData, Integer> PatientITemps;

    @FXML
    private TableColumn<PatientData, String> PatientIssues;

    @FXML
    private TextField PatientName;
    @FXML
    private TableColumn<PatientData, String> PatientNames;

    @FXML
    private AnchorPane PatientNextForm;

    @FXML
    private PasswordField PatientPass;

    @FXML
    private TextField PatientPhone;

    @FXML
    private TextField PatientSpO2;

    @FXML
    private TableColumn<PatientData, Integer> PatientSpO2s;

    @FXML
    private Button PatientSubmit;

    @FXML
    private TableColumn<PatientData, Integer> PatientSugar;

    @FXML
    private TextField PatientSugarLevel;

    @FXML
    private TextField PatientTemp;

    @FXML
    private Button Patient_Next;

    @FXML
    private Button ResetBtn;

    @FXML
    private TextField UpdateBps;

    @FXML
    private Button UpdateBtn;

    @FXML
    private TextField UpdateECGs;

    @FXML
    private ComboBox<String> UpdateGenders;
    @FXML
    private StackPane DoctorDashBoard;
    @FXML
    private TextField UpdateIDs;

    @FXML
    private TextField UpdateIssues;

    @FXML
    private Button UpdateDoctorImage;
    @FXML
    private ImageView UpdateDoctorImageView;
    @FXML
    private ImageView  DoctorImageView;
    @FXML
    private TextField UpdateNames;

    @FXML
    private TextField UpdateSpO2s;

    @FXML
    private TextField UpdateSugars;

    @FXML
    private TextField UpdateTemps;

    @FXML
    private Label UserDisplay;

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    public void SwitchForm(ActionEvent Event){
        if(Event.getSource() == Doctor_Dash){
           controlPatientForm(0);
            UpdateDoctorProfile.setVisible(false);
        }else if(Event.getSource() == Doctor_Patient){
            UpdateDoctorProfile.setVisible(false);
            controlPatientForm(1);
        }
        else if(Event.getSource() == Doctor_Profile){
            UpdateDoctorProfile.setVisible(true);
            controlPatientForm(0);
            DoctorDash.setVisible(false);
            updatePop();
            showUpdateDoctor();
        }
    }
    public void showUpdateDoctor(){
        connect = Database.connectDB();
        String sql = "SELECT * FROM Doctor WHERE D_DID = '" + Data.Doctor_Id + "';";
        try{
            assert connect != null;
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            if(result.next()){
               UpdateDoctorName.setText(result.getString("D_Name"));
               UpdateDoctorPass.setText(result.getString("Pass"));
               UpdateDoctorEmail.setText(result.getString("Email"));
               UpdateDoctorPhoneNo.setText(result.getString("Mob_No"));
               UpdateDoctorSpecialised.setText(result.getString("Specialized"));
               UpdateDoctorAddress.setText(result.getString("Address"));
//                byte[] image = result.getBytes("Img");
//               UpdateDoctorImageView.setImage(new Image(new ByteArrayInputStream(image)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    String doctorID = "",doctor_Name = "";
    public void patientAdd(){
        if(PatientID.getText().isEmpty()
                || PatientName.getText().isEmpty()
                || PatientGender.getSelectionModel() == null
                || PatientPhone.getText().isEmpty()
                || PatientPass.getText().isEmpty()
                || HealthIssue.getText().isEmpty()
                ||PatientTemp.getText().isEmpty()
                ||PatientBloodPressure.getText().isEmpty()
                ||PatientSpO2.getText().isEmpty()
                ||PatientSugarLevel.getText().isEmpty()
                ||PatientECG.getText().isEmpty()){
            alert.errorMessage("Please fill all the necessary fields");
        }else{
            connect = Database.connectDB();
            try {
                String getDoctorData = "SELECT * FROM Doctor WHERE D_DID = '" + Data.Doctor_Id + "';";
                assert connect != null;
                statement = connect.createStatement();
                result = statement.executeQuery(getDoctorData);
                if(result.next()){
                doctor_Name = result.getString("D_Name");
                doctorID = result.getString("D_ID");
                }
                String checkPatientId = "SELECT * FROM Patient WHERE P_Id = '" + PatientID +"';";
                statement = connect.createStatement();
                result = statement.executeQuery(checkPatientId);
                if(result.next()){
                    alert.errorMessage(PatientID.getText() + " already exists");
                }else{
                String insertData = "INSERT INTO Patient(P_Id,P_Name,P_Gender,P_MobNo,Pass,P_Issue,P_Temp,P_BP,P_SPO2,P_SL,P_ECG,P_Date,D_ID,D_DID,D_Name)" +
                                 "VALUES" +
                                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare = connect.prepareStatement(insertData);
                prepare.setString(1,PatientID.getText());
                prepare.setString(2,PatientName.getText());
                prepare.setString(3, PatientGender.getSelectionModel().getSelectedItem());
                prepare.setString(4,PatientPhone.getText());
                prepare.setString(5,PatientPass.getText());
                prepare.setString(6,HealthIssue.getText());
                prepare.setString(7,PatientTemp.getText());
                prepare.setString(8,PatientBloodPressure.getText());
                prepare.setString(9,PatientSpO2.getText());
                prepare.setString(10,PatientSugarLevel.getText());
                prepare.setString(11,PatientECG.getText());
                prepare.setString(12,"" + sqlDate);
                prepare.setString(13,doctorID);
                prepare.setString(14,Doctor_ID.getText());
                prepare.setString(15,doctor_Name);
                prepare.executeUpdate();
                displayPatientData();
                patientIdGenerate();
                alert.successMessage("Added Successfully!");
                patientClear();
                controlPatientForm(0);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Image img;
    public void addPhoto() throws IOException {
        FileChooser open = new FileChooser();
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image","*jpg","*jpeg","*png"));
        File file = open.showOpenDialog(UpdateDoctorImage.getScene().getWindow());
        if(file!=null){
        Data.path = Path.of(file.getAbsolutePath());
        img = new Image(file.toURI().toString(), 155,139,false,true);
        byte[] imgData = Files.readAllBytes(file.toPath());
        String imgAdd = "INSERT INTO Doctor (D_DID,Img) VALUES (?,?);";
        connect = Database.connectDB();
            try {
                assert connect != null;
                prepare = connect.prepareStatement(imgAdd);
                prepare.setString(1,Doctor_ID.getText());
                prepare.setBytes(2,imgData);
                prepare.executeUpdate();
                UpdateDoctorImageView.setImage(img);
                DoctorImageView.setImage(img);
                alert.successMessage("Image added successfully");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void resetPatientUpdate(){
        UpdateNames.clear();
        UpdateGenders.getSelectionModel().clearSelection();
        UpdateIssues.clear();
        UpdateECGs.clear();
        UpdateSpO2s.clear();
        UpdateTemps.clear();
        UpdateBps.clear();
        UpdateSugars.clear();
    }
    public void resetPatientData(){
        PatientGender.getSelectionModel().clearSelection();
        PatientPhone.clear();
        PatientPass.clear();
        PatientName.clear();
        HealthIssue.clear();
        PatientTemp.clear();
        PatientBloodPressure.clear();
        PatientSpO2.clear();
        PatientSugarLevel.clear();
        PatientECG.clear();
    }
    public void UpdateData(){
        if(UpdateIDs.getText().isEmpty()||
           UpdateNames.getText().isEmpty()||
           UpdateGenders.getSelectionModel().getSelectedItem() == null ||
           UpdateIssues.getText().isEmpty()||
                UpdateTemps.getText().isEmpty()||
                UpdateBps.getText().isEmpty()||
                UpdateSugars.getText().isEmpty()||
                UpdateECGs.getText().isEmpty()
        ){
            alert.errorMessage("Please fill all the fields");
        }else{
            String updateData = "UPDATE Patient " +
                                "SET P_Name = '" + UpdateNames.getText() + "'," +
                                "P_Gender = '" + UpdateGenders.getSelectionModel().getSelectedItem() + "'," +
                                "P_Issue = '" + UpdateIssues.getText() + "'," +
                                "P_Temp = '" + UpdateTemps.getText() + "'," +
                                "P_BP = '" + UpdateBps.getText() + "'," +
                                "P_SPO2 = '" + UpdateSpO2s.getText() + "'," +
                                "P_SL = '" + UpdateSugars.getText() + "'," +
                                "P_ECG = '" + UpdateECGs.getText() + "'," +
                                "Date_Modify = '" + new java.sql.Date(new Date().getTime()) + "' " +
                                "WHERE P_Id = '" + UpdateIDs.getText() + "';";
            connect = Database.connectDB();
            try{
                if(alert.confirmationMessage("Are you sure you want to Update Patient Id " + pData.getPatientID() +  " ?")){
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();
                    alert.successMessage("Data updated successfully!");
                    displayPatientData();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void patientIdGenerate(){
        String sql = "SELECT MAX(id) FROM Patient;";
        connect = Database.connectDB();
        int temp = 0;
        String PatId = "PID-";
        try{
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if(result.next()){
                temp = result.getInt("MAX(id)");
            }
            if(temp == 0){
                temp++;
                PatId = PatId + temp;
            }else{
                PatId = PatId + (temp + 1);
            }
            displayPatientData();
            PatientID.setText(PatId);
            PatientID.setDisable(true);
            UpdateDoctorID.setText(Data.Doctor_Id);
            UpdateDoctorID.setDisable(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void updatePop() {
        String sql = "SELECT Modify_Date FROM Doctor WHERE D_DID = '" + Data.Doctor_Id + "' AND Modify_Date IS NULL;";
        try {
            connect = Database.connectDB();
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                alert.errorMessage("Please update your profile!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void updateDoctorProfile(){
        if(UpdateDoctorID.getText().isEmpty()||
                UpdateDoctorName.getText().isEmpty()||
                UpdateDoctorPass.getText().isEmpty()||
                UpdateDoctorEmail.getText().isEmpty()||
                UpdateDoctorGender.getSelectionModel().getSelectedItem() == null||
                UpdateDoctorPhoneNo.getText().isEmpty()||
                UpdateDoctorSpecialised.getText().isEmpty()||
                UpdateDoctorAddress.getText().isEmpty()
        ){
            alert.errorMessage("Please all the data fields!");
        }else{
            String UpdateData = "UPDATE Doctor " +
                                "SET D_Name = '" + UpdateDoctorName.getText() + "'," +
                                "Pass = '" + UpdateDoctorPass.getText() + "'," +
                                "Email = '" + UpdateDoctorEmail.getText() + "'," +
                                "Gender = '" + UpdateDoctorGender.getSelectionModel().getSelectedItem() + "'," +
                                "Mob_No = '" + UpdateDoctorPhoneNo.getText() + "'," +
                                "Specialized = '" + UpdateDoctorSpecialised.getText() + "'," +
                                "Address = '" + UpdateDoctorAddress.getText() + "'," +
                                "Modify_Date = '" + new java.sql.Date(new Date().getTime()) + "' " +
                                "Modify_Date = '" + new java.sql.Date(new Date().getTime()) + "' " +
                                "WHERE D_DID = '" + UpdateDoctorID.getText() + "';";
            connect = Database.connectDB();
            try{
            assert connect != null;
            prepare = connect.prepareStatement(UpdateData);
            prepare.executeUpdate();
            alert.successMessage("Data updated successfully!");
            doctorClear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void UpdatePatientClear(){
        UpdateIDs.clear();
        UpdateNames.clear();
        UpdateGenders.getSelectionModel().clearSelection();
        UpdateIssues.clear();
        UpdateECGs.clear();
        UpdateSpO2s.clear();
        UpdateTemps.clear();
        UpdateBps.clear();
        UpdateSugars.clear();
    }
    public void doctorClear(){
    UpdateDoctorName.clear();
    UpdateDoctorPass.clear();
    UpdateDoctorEmail.clear();
    UpdateDoctorGender.getSelectionModel().clearSelection();
    UpdateDoctorPhoneNo.clear();
    UpdateDoctorSpecialised.clear();
    UpdateDoctorAddress.clear();
    }
    public void patientClear(){
        PatientID.clear();
        PatientName.clear();
        PatientGender.getSelectionModel().clearSelection();
        PatientPhone.clear();
        PatientPass.clear();
        HealthIssue.clear();
        PatientTemp.clear();
        PatientBloodPressure.clear();
        PatientSpO2.clear();
        PatientSugarLevel.clear();
        PatientECG.clear();
    }
    public void controlPatientForm(int op){
        if(op == 1){
            PatientForm.setVisible(true);
            PatientNextForm.setVisible(false);
            DoctorDash.setVisible(false);
        }else if (op == -1){
            PatientNextForm.setVisible(true);
            PatientForm.setVisible(false);
            DoctorDash.setVisible(false);
        }else if(op == 0){
            PatientForm.setVisible(false);
            PatientNextForm.setVisible(false);
            DoctorDash.setVisible(true);
        }
    }
    public void DeleteAccount(){
        if(UpdateDoctorID.getText().isEmpty()){
            alert.errorMessage("Cannot Delete without Doctor ID!");
        }else{
            String DeleteACC = "UPDATE Doctor SET D_Name ='"  + UpdateDoctorName.getText() + "', Delete_Date = '" +new java.sql.Date(new Date().getTime())+ "' WHERE D_DID = '" + UpdateDoctorID.getText() + "';";
            try{
                connect = Database.connectDB();
                assert connect != null;
                prepare = connect.prepareStatement(DeleteACC);
                if(alert.confirmationMessage("Are you sure to delete your Account?")){
                    prepare.executeUpdate();
                    alert.successMessage("Account Deleted successfully!");
                    DoctorDashBoard.getScene().getWindow().hide();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void displayDoctorData(){
        String name = Data.Doctor_Username;
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        UserDisplay.setText(name);
        Doctor_ID.setText(Data.Doctor_Id);
    }

    public void patientGenderList(){
        List<String> listG = new ArrayList<>();
        Collections.addAll(listG, Data.Gender);
        ObservableList<String> listData = FXCollections.observableList(listG);
        PatientGender.setItems(listData);
        UpdateGenders.setItems(listData);
        UpdateDoctorGender.setItems(listData);
    }
    public void NextPatientForm(ActionEvent event){
        if(event.getSource() == Patient_Next){
            PatientForm.setVisible(false);
            PatientNextForm.setVisible(true);
        }
    }
    PatientData pData;
    public ObservableList<PatientData> getPatientRecordData(){
        ObservableList<PatientData> listData = FXCollections.observableArrayList();
        String selectData = "SELECT * FROM Patient WHERE Date_Delete IS NULL ;";
        connect = Database.connectDB();
        try{
            assert connect != null;
        prepare = connect.prepareStatement(selectData);
        result = prepare.executeQuery();
        while(result.next()){
            pData = new PatientData(result.getString("P_Id")
            ,result.getString("P_Name"),result.getString("P_Gender")
                    ,result.getInt("P_MobNo"),result.getString("Pass"),result.getString("P_Issue")
                    ,result.getInt("P_Temp"),result.getInt("P_BP"),result.getInt("P_SPO2"),result.getInt("P_SL")
                    ,result.getInt("P_ECG"));
            listData.add(pData);
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listData;
    }
    public void DeleteEntry(){
        if(UpdateIDs.getText().isEmpty()){
            alert.errorMessage("Please Enter the Id of the patient to be deleted!");
        } else {
                    String DeleteData = "UPDATE Patient SET Date_Delete ='" + new java.sql.Date(new Date().getTime()) + "' WHERE P_Id = '" +UpdateIDs.getText() + "';";
                    connect = Database.connectDB();
                try{
                    assert connect != null;
                    prepare = connect.prepareStatement(DeleteData);
                    if(alert.confirmationMessage("Are you sure to delete this entry with Id: " + UpdateIDs.getText() + " ?")){
                        prepare.executeUpdate();
                        alert.confirmationMessage("Data deleted successfully!");
                        displayPatientData();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
    }
    public void displayPatientData(){
        ObservableList<PatientData> patientRecordData = getPatientRecordData();
        PatientIDs.setCellValueFactory(new PropertyValueFactory<>("PatientID"));
        PatientNames.setCellValueFactory(new PropertyValueFactory<>("PatientName"));
        PatientGenders.setCellValueFactory(new PropertyValueFactory<>("PatientGender"));
        PatientIssues.setCellValueFactory(new PropertyValueFactory<>("HealthIssue"));
        PatientITemps.setCellValueFactory(new PropertyValueFactory<>("Temp"));
        PatientBps.setCellValueFactory(new PropertyValueFactory<>("Bp"));
        PatientSpO2s.setCellValueFactory(new PropertyValueFactory<>("SpO2"));
        PatientSugar.setCellValueFactory(new PropertyValueFactory<>("BloodSugar"));
        PatientECGs.setCellValueFactory(new PropertyValueFactory<>("ECG"));
        DoctorMain.setItems(patientRecordData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    displayPatientData();
    displayDoctorData();
    patientGenderList();
    patientIdGenerate();
    }
}
