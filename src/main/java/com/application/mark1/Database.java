package com.application.mark1;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public static Connection connectDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalProject","root","Welcome@2023");
            return connect;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
