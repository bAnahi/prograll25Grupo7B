package com.prograii25.prograii25grupo7b.db;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Factura")
public class Factura {

    @Id
    @Column(name = "id_factura")
    private long idFactura;

    @Column(name = "id_venta")
    private long idVenta;

    @Column(name = "numero_factura")
    private String numeroFactura;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_emision")
    private Date fechaEmision;

    @Column(name = "total")
    private double total;

    public Factura() {}

    public Factura(long idFactura, long idVenta, String numeroFactura, Date fechaEmision, double total) {
        this.idFactura = idFactura;
        this.idVenta = idVenta;
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.total = total;
    }

    // Getters y setters
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


