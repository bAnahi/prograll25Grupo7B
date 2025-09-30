
/* package com.prograii25.prograii25grupo7b;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException;

public class ConexionSQL {
    

    // Datos de conexión
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=EmpresaTecnologia;encrypt=false;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "DiosEsBueno2024";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Conexión exitosa a SQL Server");
        } catch (SQLException e) {
            System.out.println(" Error en la conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}
*/