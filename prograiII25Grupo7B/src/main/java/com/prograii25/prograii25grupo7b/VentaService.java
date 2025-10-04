package com.prograii25.prograii25grupo7b;

import com.prograii25.prograii25grupo7b.db.Cliente;
import com.prograii25.prograii25grupo7b.db.Producto;
import com.prograii25.prograii25grupo7b.db.Usuario;
import com.prograii25.prograii25grupo7b.db.Venta;
import com.prograii25.prograii25grupo7b.db.Factura;
import com.prograii25.prograii25grupo7b.db.DetalleFactura;
import com.prograii25.prograii25grupo7b.Rol;

import java.util.Date;
import java.util.List;

public class VentaService {

    private List<Cliente> clientes;
    private List<Producto> productos;
    private List<Usuario> usuarios;
    private List<Inventario> inventario;

    public VentaService(List<Cliente> clientes,
                        List<Producto> productos,
                        List<Usuario> usuarios,
                        List<Inventario> inventario) {
        this.clientes = clientes;
        this.productos = productos;
        this.usuarios = usuarios;
        this.inventario = inventario;
    }

    public boolean registrarVenta(Venta venta, List<DetalleFactura> detalles) {

        // Validar cliente
        Cliente cliente = clientes.stream()
                .filter(c -> c.getIdCliente() == venta.getIdCliente())
                .findFirst()
                .orElse(null);
        if (cliente == null) {
            System.out.println("El cliente no existe.");
            return false;
        }

        // Validar usuario
        Usuario usuario = usuarios.stream()
                .filter(u -> u.getIdusuario() == venta.getIdUsuario())
                .findFirst()
                .orElse(null);
        if (usuario == null || usuario.getRolEnum() == null) {
            System.out.println("Usuario no valido.");
            return false;
        }
        Rol rolUsuario = usuario.getRolEnum();
        if (!rolUsuario.equals(Rol.ADMINISTRADOR) && !rolUsuario.equals(Rol.VENDEDOR)) {
            System.out.println("Usuario no tiene permisos para registrar ventas.");
            return false;
        }

        // Validar productos y stock
        for (DetalleFactura detalle : detalles) {
            Producto producto = productos.stream()
                    .filter(p -> p.getIdProducto() == detalle.getIdProducto())
                    .findFirst()
                    .orElse(null);
            if (producto == null) {
                System.out.println("Producto con ID " + detalle.getIdProducto() + " no existe.");
                return false;
            }

            Inventario inv = inventario.stream()
                    .filter(i -> i.getIdProducto() == detalle.getIdProducto())
                    .findFirst()
                    .orElse(null);
            if (inv == null || inv.getCantidadDisponible() < detalle.getCantidad()) {
                System.out.println("Producto " + producto.getNombre() + " no tiene suficiente stock.");
                return false;
            }
        }

        // Calcular total
        double totalVenta = 0;
        for (DetalleFactura detalle : detalles) {
            detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
            totalVenta += detalle.getSubtotal();
        }
        venta.setTotal(totalVenta);
        venta.setFecha(new Date());

        // Reducir stock
        for (DetalleFactura detalle : detalles) {
            Inventario inv = inventario.stream()
                    .filter(i -> i.getIdProducto() == detalle.getIdProducto())
                    .findFirst()
                    .orElse(null);
            inv.setCantidadDisponible(inv.getCantidadDisponible() - detalle.getCantidad());
        }

        // Generar factura
        Factura factura = new Factura(
                new Date().getTime(),
                venta.getIdVenta(),
                "FAC-" + venta.getIdVenta(),
                new Date(),
                totalVenta
        );

        System.out.println("Venta registrada con exito. Total: Q" + totalVenta);
        System.out.println("Factura generada: " + factura.getNumeroFactura());

        return true;
    }
}
