/*package com.prograii25.prograii25grupo7b.servicios;

import com.prograii25.prograii25grupo7b.ConexionSQL;
import com.prograii25.prograii25grupo7b.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicioUsuario {

    public boolean login(String correo, String contrasena) {
        String sql = "SELECT id_usuario, nombre, rol FROM Usuario WHERE correo = ? AND contraseña = ?";

        try (Connection conn = ConexionSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                long idUsuario = rs.getLong("id_usuario");
                String nombre = rs.getString("nombre");
                String rol = rs.getString("rol");

                registrarBitacora(idUsuario, "Login exitoso");
                System.out.println(" Bienvenido " + nombre + " (" + rol + ")");
                return true;
            } else {
                System.out.println(" Usuario o contraseña incorrectos");
                return false;
            }

        } catch (SQLException e) {
            System.out.println(" Error en login: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario (id_usuario, nombre, correo, contraseña, rol) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, usuario.getIdUsuario());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getContrasena());
            stmt.setString(5, usuario.getRol());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                registrarBitacora(usuario.getIdUsuario(), "Registro de usuario: " + usuario.getNombre());
                return true;
            }

            return false;

        } catch (SQLException e) {
            System.out.println(" Error registrando usuario: " + e.getMessage());
            return false;
        }
    }

    private void registrarBitacora(long idUsuario, String accion) {
        String sql = "INSERT INTO Bitacora (id_usuario, accion) VALUES (?, ?)";

        try (Connection conn = ConexionSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idUsuario);
            stmt.setString(2, accion);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("? Error registrando bitácora: " + e.getMessage());
        }
    }
}
*/