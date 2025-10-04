package com.prograii25.prograii25grupo7b;

import com.prograii25.prograii25grupo7b.db.Usuario;
import com.prograii25.prograii25grupo7b.Rol;
import com.prograii25.prograii25grupo7b.persistencia.UsuarioJpaController;

import java.util.List;

public class UsuarioService {

    private UsuarioJpaController usuarioJpa;

    public UsuarioService(UsuarioJpaController usuarioJpa) {
        this.usuarioJpa = usuarioJpa;
    }

    public boolean registrarUsuarioConValidacion(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) return false;
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) return false;
        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) return false;
        if (usuario.getRolEnum() == null) return false;

        List<Usuario> usuarios = usuarioJpa.findUsuarioEntities();
        boolean existe = usuarios.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(usuario.getEmail()));
        if (existe) return false;

        return usuarioJpa.registrarUsuario(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioJpa.findUsuarioEntities();
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarioJpa.findUsuarioEntities().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(correo))
                .findFirst()
                .orElse(null);
    }
}

