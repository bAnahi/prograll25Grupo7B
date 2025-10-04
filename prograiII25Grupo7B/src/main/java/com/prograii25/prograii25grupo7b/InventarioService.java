package com.prograii25.prograii25grupo7b;

import java.util.List;

public class InventarioService {

    private List<Inventario> inventario;

    public InventarioService(List<Inventario> inventario) {
        this.inventario = inventario;
    }

    public boolean agregarStock(long idProducto, int cantidad) {
        for (Inventario inv : inventario) {
            if (inv.getIdProducto() == idProducto) {
                inv.setCantidadDisponible(inv.getCantidadDisponible() + cantidad);
                return true;
            }
        }
        return false;
    }

    public boolean reducirStock(long idProducto, int cantidad) {
        for (Inventario inv : inventario) {
            if (inv.getIdProducto() == idProducto && inv.getCantidadDisponible() >= cantidad) {
                inv.setCantidadDisponible(inv.getCantidadDisponible() - cantidad);
                return true;
            }
        }
        return false;
    }

    public int consultarStock(long idProducto) {
        for (Inventario inv : inventario) {
            if (inv.getIdProducto() == idProducto) {
                return inv.getCantidadDisponible();
            }
        }
        return 0;
    }
}
