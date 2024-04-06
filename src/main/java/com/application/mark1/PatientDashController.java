package com.application.mark1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class PatientDashController implements Initializable {

    @FXML
    private Label HealthIssue;

    @FXML
    private Label PatientBp;

    @FXML
    private Label PatientECG;

    @FXML
    private Label PatientID;

    @FXML
    private Label PatientName;

    @FXML
    private Label PatientSpO2;

    @FXML
    private Label PatientSugar;

    @FXML
    private Label PatientTemp;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    public void displayData(){
        String getData = "SELECT * FROM Patient WHERE P_Id = '" +Data.PatientID + "';";
        try{
            connect = Database.connectDB();
            assert connect != null;
            prepare = connect.prepareStatement(getData);
            result = prepare.executeQuery();
            while(result.next()){
               PatientData pData = new PatientData(result.getString("P_Id")
                        ,result.getString("P_Name"),result.getString("P_Gender")
                        ,result.getInt("P_MobNo"),result.getString("Pass"),result.getString("P_Issue")
                        ,result.getInt("P_Temp"),result.getInt("P_BP"),result.getInt("P_SPO2"),result.getInt("P_SL")
                        ,result.getInt("P_ECG"));
                PatientTemp.setText("" + pData.getTemp());
                PatientBp.setText("" + pData.getBp());
                PatientSpO2.setText("" + pData.getSpO2());
                PatientSugar.setText("" + pData.getBloodSugar());
                PatientECG.setText("" + pData.getECG());
                PatientName.setText(pData.getPatientName());
                PatientID.setText(pData.getPatientID());
                HealthIssue.setText(pData.getHealthIssue());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            displayData();
    }
}
