package com.prograii25.prograii25grupo7b.db;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Factura")
public class Factura {

    @Id
    @Column(name = "id_factura")
    private long idFactura;

    @Column(name = "id_cliente")
    private long idCliente;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "total")
    private double total;

    @Column(name = "id_usuario")
    private long idUsuario;

    public Factura() {}

    public Factura(long idFactura, long idCliente, Date fecha, double total, long idUsuario) {
        this.idFactura = idFactura;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.total = total;
        this.idUsuario = idUsuario;
    }

    // Getters y setters
    public long getIdFactura() { return idFactura; }
    public void setIdFactura(long idFactura) { this.idFactura = idFactura; }

    public long getIdCliente() { return idCliente; }
    public void setIdCliente(long idCliente) { this.idCliente = idCliente; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(long idUsuario) { this.idUsuario = idUsuario; }
}
