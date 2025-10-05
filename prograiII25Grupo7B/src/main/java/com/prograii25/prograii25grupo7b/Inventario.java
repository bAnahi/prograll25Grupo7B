package com.prograii25.prograii25grupo7b.db;

import javax.persistence.*;

@Entity
@Table(name = "Inventario")
public class Inventario {

    @Id
    @Column(name = "id_inventario")
    private long idInventario;

    @Column(name = "id_producto")
    private long idProducto;

    @Column(name = "cantidad_disponible")
    private int cantidadDisponible;

    public Inventario() {}

    public Inventario(long idInventario, long idProducto, int cantidadDisponible) {
        this.idInventario = idInventario;
        this.idProducto = idProducto;
        this.cantidadDisponible = cantidadDisponible;
    }

    public long getIdInventario() { return idInventario; }
    public void setIdInventario(long idInventario) { this.idInventario = idInventario; }

    public long getIdProducto() { return idProducto; }
    public void setIdProducto(long idProducto) { this.idProducto = idProducto; }

    public int getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(int cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }
}


