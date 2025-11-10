package com.mycompany.parkplus;

import Clases.Conexion;
import java.sql.Connection;

public class ParkPlus {

    public static void main(String[] args) {
        Connection conectar = Conexion.Conectar();
        
        PantallaPark Pantalla = new PantallaPark();
        Pantalla.setVisible(true);
    }
}