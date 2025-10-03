package com.prograii25.prograii25grupo7b.db;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
public class Usuario {

    @Id
    @Column(name = "id_usuario") // Corregido para coincidir con BD
    private Long idusuario;

    private String nombre;

    @Column(name = "correo")
    private String email;

    @Column(name = "contrase�a") // Ajuste para coincidir
    private String contrasena;

    private String rol ;
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
            return Rol.valueOf(s.trim().toUpperCase().replace(' ', '_'));
        } catch (IllegalArgumentException e) {
            return null; 
        }
    }
}
    public Usuario() {}
public class Usuario {
    private String id;
    private String nombre;

    // campo antiguo (compatibilidad)
    private String rol;

    // nuevo campo seguro con enum
    private Rol rolEnum;

    // Constructor que mantiene compatibilidad
    public Usuario(String id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.rolEnum = Rol.fromString(rol);
    }

    // Nuevo constructor usando enum
    public Usuario(String id, String nombre, Rol rolEnum) {
        this.id = id;
        this.nombre = nombre;
        this.rolEnum = rolEnum;
        this.rol = (rolEnum != null) ? rolEnum.name() : null;
    }

    // getters y setters compatibles
    public String getId() { return id; }
    public String getNombre() { return nombre; }

    // getter antiguo (devuelve String para compatibilidad)
    public String getRol() {
        if (rol != null) return rol;
        return (rolEnum != null) ? rolEnum.name() : null;
    }

    // setter antiguo
    public void setRol(String rol) {
        this.rol = rol;
        this.rolEnum = Rol.fromString(rol);
    }

    // getter/setter nuevo
    public Rol getRolEnum() { return rolEnum; }

    public void setRolEnum(Rol rolEnum) {
        this.rolEnum = rolEnum;
        this.rol = (rolEnum != null) ? rolEnum.name() : null;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", rol=" + getRol() +
                '}';
    }
}
    public permisos 
     public class Permisos {

    private static final Map<Rol, List<String>> permisos = new EnumMap<>(Rol.class);

    static {
        permisos.put(Rol.ADMINISTRADOR, Arrays.asList(
                "GESTIONAR_USUARIOS",
                "GESTIONAR_INVENTARIO",
                "REGISTRAR_VENTA",
                "GENERAR_FACTURA",
                "VER_REPORTES"
        ));

        permisos.put(Rol.VENDEDOR, Arrays.asList(
                "REGISTRAR_VENTA",
                "GENERAR_FACTURA"
        ));

        permisos.put(Rol.CLIENTE, Arrays.asList(
                "CONSULTAR_FACTURA"
        ));

        permisos.put(Rol.INVENTARIO, Arrays.asList(
                "GESTIONAR_INVENTARIO"
        ));

        permisos.put(Rol.CAJERO, Arrays.asList(
                "GENERAR_FACTURA",
                "COBRAR"
        ));

        permisos.put(Rol.GERENTE, Arrays.asList(
                "VER_REPORTES"
        ));

        permisos.put(Rol.SOPORTE_TECNICO, Arrays.asList(
                "GESTIONAR_RECLAMOS"
        ));

        permisos.put(Rol.CONTADOR, Arrays.asList(
                "VER_REPORTES",
                "GESTIONAR_CONTABILIDAD"
        ));
    }

    public static boolean tienePermiso(Rol rol, String accion) {
        if (rol == null || accion == null) return false;
        List<String> lista = permisos.get(rol);
        return lista != null && lista.contains(accion);
    }

    public static List<String> getPermisos(Rol rol) {
        return Collections.unmodifiableList(permisos.getOrDefault(rol, Collections.emptyList()));
    }
}
    public Long getIdusuario() { return idusuario; }
    public void setIdusuario(Long idusuario) { this.idusuario = idusuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}


