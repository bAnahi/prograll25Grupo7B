package com.prograii25.prograii25grupo7b.servicios;

import com.prograii25.prograii25grupo7b.ConexionSQL;
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

    private void registrarBitacora(long idUsuario, String accion) {
        String sql = "INSERT INTO Bitacora (id_usuario, accion) VALUES (?, ?)";

        try (Connection conn = ConexionSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idUsuario);
            stmt.setString(2, accion);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("❌ Error registrando bitácora: " + e.getMessage());
        }
    }
}

public static class Usuario {
        private long id;
        private String nombre;
        private String correo;
        private String contraseña;
        private String rol;

        public Usuario(String nombre, String correo, String contraseña, String rol) {
            this.nombre = nombre;
            this.correo = correo;
            this.contraseña = contraseña;
            this.rol = rol;
        }

        public Usuario(long id, String nombre, String correo, String contraseña, String rol) {
            this.id = id;
            this.nombre = nombre;
            this.correo = correo;
            this.contraseña = contraseña;
            this.rol = rol;
        }

        public long getId() { return id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
        public String getContraseña() { return contraseña; }
        public void setContraseña(String contraseña) { this.contraseña = contraseña; }
        public String getRol() { return rol; }
        public void setRol(String rol) { this.rol = rol; }
    }


    public static class UsuarioDAO {

        
        public boolean crearUsuario(Usuario u) {
            String sql = "INSERT INTO Usuario(nombre, correo, contraseña, rol) VALUES(?, ?, ?, ?)";
            try (Connection conn = ConexionSQL.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setString(1, u.getNombre());
                pst.setString(2, u.getCorreo());
                pst.setString(3, u.getContraseña());
                pst.setString(4, u.getRol());

                int filas = pst.executeUpdate();
                return filas > 0;

            } catch (SQLException e) {
                System.out.println("Error al crear usuario: " + e.getMessage());
                return false;
            }
        }

     
        public List<Usuario> listarUsuarios() {
            List<Usuario> lista = new ArrayList<>();
            String sql = "SELECT * FROM Usuario";

            try (Connection conn = ConexionSQL.getConnection();
                 Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                while (rs.next()) {
                    Usuario u = new Usuario(
                            rs.getLong("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("contraseña"),
                            rs.getString("rol")
                    );
                    lista.add(u);
                }

            } catch (SQLException e) {
                System.out.println("Error al listar usuarios: " + e.getMessage());
            }

            return lista;
        }

     
        public boolean actualizarUsuario(Usuario u) {
            String sql = "UPDATE Usuario SET nombre = ?, correo = ?, contraseña = ?, rol = ? WHERE id_usuario = ?";
            try (Connection conn = ConexionSQL.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setString(1, u.getNombre());
                pst.setString(2, u.getCorreo());
                pst.setString(3, u.getContraseña());
                pst.setString(4, u.getRol());
                pst.setLong(5, u.getId());

                int filas = pst.executeUpdate();
                return filas > 0;

            } catch (SQLException e) {
                System.out.println("Error al actualizar usuario: " + e.getMessage());
                return false;
            }
        }

     
        public boolean eliminarUsuario(long id) {
            String sql = "DELETE FROM Usuario WHERE id_usuario = ?";
            try (Connection conn = ConexionSQL.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setLong(1, id);
                int filas = pst.executeUpdate();
                return filas > 0;

            } catch (SQLException e) {
                System.out.println("Error al eliminar usuario: " + e.getMessage());
                return false;
            }
        }
    }

   
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();

        
        Usuario u1 = new Usuario("Ana Perez", "ana@email.com", "1234", "Admin");
        if (dao.crearUsuario(u1)) {
            System.out.println("Usuario creado con éxito.");
        }

        
        List<Usuario> lista = dao.listarUsuarios();
        System.out.println("Usuarios registrados:");
        for (Usuario u : lista) {
            System.out.println(u.getId() + "\t" + u.getNombre() + "\t" + u.getCorreo() + "\t" + u.getRol());
        }

     
        if (!lista.isEmpty()) {
            Usuario mod = lista.get(0);
            mod.setNombre("Ana P. Modificada");
            if (dao.actualizarUsuario(mod)) {
                System.out.println("Usuario actualizado con exito.");
            }
        }


        if (!lista.isEmpty()) {
            long idEliminar = lista.get(0).getId();
            if (dao.eliminarUsuario(idEliminar)) {
                System.out.println("Usuario eliminado con éxito.");
            }
        }
    }
