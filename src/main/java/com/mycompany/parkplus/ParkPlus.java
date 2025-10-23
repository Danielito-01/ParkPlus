package com.mycompany.parkplus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ParkPlus {

    public static void main(String[] args) {
        String url = "jdbc:sqlserver://Daniel:1433;databaseName=ParkPlus;encrypt=true;trustServerCertificate=True";
        String usuario = "danipark";
        String contra = "Dani01";
        try {
            Connection con = DriverManager.getConnection(url,usuario,contra);
            System.out.println("Conexion exitosa!!");
            
        }catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("Eror al conectar a la base de datos");
            System.out.println(ex.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        PantallaPark Pantalla = new PantallaPark();
        Pantalla.setVisible(true);
    }
}