/*
package com.prograii25.prograii25grupo7b;

import com.prograii25.prograii25grupo7b.db.Usuario;

import java.util.List;

public class SistemaService {

    private UsuarioService usuarioService;
    private VentaService ventaService;
    private InventarioService inventarioService;

    public SistemaService(UsuarioService usuarioService, VentaService ventaService, InventarioService inventarioService) {
        this.usuarioService = usuarioService;
        this.ventaService = ventaService;
        this.inventarioService = inventarioService;
    }

    // Métodos de Usuario
    public boolean registrarUsuario(Usuario usuario) {
        return usuarioService.registrarUsuarioConValidacion(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarioService.buscarUsuarioPorCorreo(correo);
    }

    // Métodos de Inventario
    public boolean agregarStock(long idProducto, int cantidad) {
        return inventarioService.agregarStock(idProducto, cantidad);
    }

    public boolean reducirStock(long idProducto, int cantidad) {
        return inventarioService.reducirStock(idProducto, cantidad);
    }

    public int consultarStock(long idProducto) {
        return inventarioService.consultarStock(idProducto);
    }

    // Métodos de Venta
    public boolean registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        return ventaService.registrarVenta(venta, detalles);
    }
}

*\