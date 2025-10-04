package com.prograii25.prograii25grupo7b.db;

import com.prograii25.prograii25grupo7b.Rol;

import java.util.Arrays;
import java.util.List;

public class Permisos {

    public static boolean tienePermiso(Rol rol, String accion) {
        if (rol == null) return false;

        switch (rol) {
            case ADMINISTRADOR:
                return true; // Todo permiso
            case VENDEDOR:
                return accion.equals("REGISTRAR_VENTA");
            case CLIENTE:
                return false; // Clientes NADA
            case INVENTARIO:
                return accion.equals("GESTIONAR_INVENTARIO");
            case CAJERO:
                return accion.equals("REGISTRAR_PAGO");
            case GERENTE:
                return Arrays.asList("GESTIONAR_USUARIOS", "REGISTRAR_VENTA").contains(accion);
            case SOPORTE_TECNICO:
                return accion.equals("GESTIONAR_SOPORTE");
            case CONTADOR:
                return accion.equals("GENERAR_REPORTES");
            default:
                return false;
        }
    }
}


