package com.prograii25.prograii25grupo7b.db;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Venta")
public class Venta {

    @Id
    @Column(name = "id_venta")
    private long idVenta;

    @Column(name = "id_cliente")
    private long idCliente;

    @Column(name = "id_usuario")
    private long idUsuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "total")
    private double total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleFactura> detalles;

    public Venta() {}

    public Venta(long idVenta, long idCliente, long idUsuario, Date fecha, double total) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.total = total;
    }

    // Getters y setters
    public long getIdVenta() { return idVenta; }
    public void setIdVenta(long idVenta) { this.idVenta = idVenta; }
    public long getIdCliente() { return idCliente; }
    public void setIdCliente(long idCliente) { this.idCliente = idCliente; }
    public long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(long idUsuario) { this.idUsuario = idUsuario; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public List<DetalleFactura> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleFactura> detalles) { this.detalles = detalles; }
}

