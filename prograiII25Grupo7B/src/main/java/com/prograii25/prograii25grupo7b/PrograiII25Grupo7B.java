package com.prograii25.prograii25grupo7b;
import com.prograii25.prograii25grupo7b.servicios.ServicioUsuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PrograiII25Grupo7B {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Probar conexión y listar tablas");
            System.out.println("2. Login de usuario");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    probarConexion();
                    break;
                case 2:
                    loginUsuario(scanner);
                    break;
                case 3:
                    System.out.println("👋 Saliendo del sistema...");
                    break;
                default:
                    System.out.println("⚠️ Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 3);

        scanner.close();
    }

    private static void probarConexion() {
        try (Connection conn = ConexionSQL.getConnection()) {
            if (conn != null) {
                System.out.println(" Conexión establecida correctamente.");

                
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'");

                System.out.println("Tablas en la base de datos EmpresaTecnologia:");
                while (rs.next()) {
                    System.out.println("- " + rs.getString("TABLE_NAME"));
                }

                rs.close();
                stmt.close();
            } else {
                System.out.println(" No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            System.out.println(" Error al trabajar con la base de datos:");
            e.printStackTrace();
        }
    }
     private static void loginUsuario(Scanner scanner) {
        ServicioUsuario servicioUsuario = new ServicioUsuario();

        System.out.print("Ingrese su correo: ");
        String correo = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        boolean ok = servicioUsuario.login(correo, contrasena);

        if (ok) {
            System.out.println("Acceso concedido. Bienvenido al sistema.");
            
        } else {
            System.out.println(" Credenciales incorrectas. Intente de nuevo.");
        }
    }
}
