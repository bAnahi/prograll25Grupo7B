
package com.prograii25.prograii25grupo7b;


public class Cliente {
    
    
    private long idCliente;
    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;

    public Cliente(long idCliente, String nombre, String correo, String telefono, String direccion) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

   public long getIdCliente() { return idCliente; }
    public void setIdCliente(long idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}

    
