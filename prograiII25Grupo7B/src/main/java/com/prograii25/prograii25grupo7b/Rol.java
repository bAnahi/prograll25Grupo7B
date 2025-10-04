package com.prograii25.prograii25grupo7b; // nuevo paquete 'model', no 'db'

public enum Rol {
    ADMINISTRADOR,
    VENDEDOR,
    CLIENTE,
    INVENTARIO,
    CAJERO,
    GERENTE,
    SOPORTE_TECNICO,
    CONTADOR;

    public static Rol fromString(String s) {
        if (s == null) return null;
        try {
            return Rol.valueOf(s.trim().toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            return null; 
        }
    }
}




