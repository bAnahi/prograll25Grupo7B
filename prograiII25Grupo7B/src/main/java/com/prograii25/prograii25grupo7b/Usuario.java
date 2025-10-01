package com.prograii25.prograii25grupo7b.db;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
public class Usuario {

    @Id
    @Column(name = "id_usuario") // Corregido para coincidir con BD
    private Long idusuario;

    private String nombre;

    @Column(name = "correo")
    private String email;

    @Column(name = "contrasena") // Ajuste para coincidir
    private String contrasena;

    private String rol;

    public Usuario() {}

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
}
