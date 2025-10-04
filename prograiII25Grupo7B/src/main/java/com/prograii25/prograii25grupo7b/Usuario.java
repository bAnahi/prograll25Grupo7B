package com.prograii25.prograii25grupo7b.db;

import com.prograii25.prograii25grupo7b.Rol;

import javax.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @Column(name = "id_usuario")
    private Long idusuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    private String email;

    @Column(name = "contraseña")
    private String contrasena;

    @Column(name = "rol")
    private String rol;

    // Getters y Setters
    public Long getIdusuario() { return idusuario; }
    public void setIdusuario(Long idusuario) { this.idusuario = idusuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // Método para obtener enum
    public Rol getRolEnum() {
        return Rol.fromString(this.rol);
    }
}

