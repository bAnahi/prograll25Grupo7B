/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prograii25.prograii25grupo7b;

public class DetalleVenta {
    
    private long idDetalle;
    private long idVenta;      
    private long idProducto;   
    private int cantidad;
    private float precioUnitario;
    private float subtotal;

    public DetalleVenta(long idDetalle, long idVenta, long idProducto, int cantidad, float precioUnitario, float subtotal) {
        this.idDetalle = idDetalle;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

  
    public long getIdDetalle() { return idDetalle; }
    public void setIdDetalle(long idDetalle) { this.idDetalle = idDetalle; }

    public long getIdVenta() { return idVenta; }
    public void setIdVenta(long idVenta) { this.idVenta = idVenta; }

    public long getIdProducto() { return idProducto; }
    public void setIdProducto(long idProducto) { this.idProducto = idProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public float getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(float precioUnitario) { this.precioUnitario = precioUnitario; }

    public float getSubtotal() { return subtotal; }
    public void setSubtotal(float subtotal) { this.subtotal = subtotal; }
}

    

