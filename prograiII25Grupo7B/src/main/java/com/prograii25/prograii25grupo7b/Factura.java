
package com.prograii25.prograii25grupo7b;

import java.util.Date;
public class Factura {
    
    private long idFactura;
    private long idVenta; 
    private String numeroFactura;
    private Date fechaEmision;
    private double total;

    public Factura(long idFactura, long idVenta, String numeroFactura, Date fechaEmision, double total) {
        this.idFactura = idFactura;
        this.idVenta = idVenta;
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.total = total;
    }

    // Getters y Setters
    public long getIdFactura() { return idFactura; }
    public void setIdFactura(long idFactura) { this.idFactura = idFactura; }

    public long getIdVenta() { return idVenta; }
    public void setIdVenta(long idVenta) { this.idVenta = idVenta; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Date fechaEmision) { this.fechaEmision = fechaEmision; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}

    

