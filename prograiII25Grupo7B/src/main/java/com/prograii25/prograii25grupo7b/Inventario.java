package com.prograii25.prograii25grupo7b;


public class Inventario {
    
    private long idInventario;
    private long idProducto; 
    private int cantidadDisponible;

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


