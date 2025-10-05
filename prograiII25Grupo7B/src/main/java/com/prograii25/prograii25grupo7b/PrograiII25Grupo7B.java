package com.prograii25.prograii25grupo7b;

import com.prograii25.prograii25grupo7b.db.Usuario;
import com.prograii25.prograii25grupo7b.db.Cliente;
import com.prograii25.prograii25grupo7b.db.Producto;
import com.prograii25.prograii25grupo7b.db.DetalleFactura;
import com.prograii25.prograii25grupo7b.db.Venta;
import com.prograii25.prograii25grupo7b.persistencia.UsuarioJpaController;
import com.prograii25.prograii25grupo7b.db.Permisos;
import com.prograii25.prograii25grupo7b.db.Factura;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;


public class PrograiII25Grupo7B {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        UsuarioJpaController usuarioJpa = new UsuarioJpaController(em.getEntityManagerFactory());

        // ===== LOGIN INICIAL =====
        Usuario usuarioLogeado = null;
        boolean loginOk = false;
        while (!loginOk) {
            System.out.println("===== LOGIN =====");
            System.out.print("Correo: ");
            String correo = scanner.nextLine();
            System.out.print("Contraseña: ");
            String contrasena = scanner.nextLine();

            loginOk = usuarioJpa.login(correo, contrasena);

            if (loginOk) {
                usuarioLogeado = usuarioJpa.findUsuarioEntities().stream()
                        .filter(u -> u.getEmail().equals(correo))
                        .findFirst()
                        .orElse(null);
            } else {
                System.out.println("Credenciales incorrectas. Intente nuevamente.");
            }
        }

        Rol rolUsuario = usuarioLogeado.getRolEnum();

        // ===== MENU PRINCIPAL =====
        int opcion;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");

            // ---- USUARIOS ----
            if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_USUARIOS")) {
                System.out.println("1. Listar usuarios");
                System.out.println("2. Registrar usuario");
                System.out.println("3. Editar usuario");
                System.out.println("4. Eliminar usuario");
            }

            // ---- CLIENTES ----
            if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_CLIENTES")) {
                System.out.println("5. Listar clientes");
                System.out.println("6. Registrar cliente");
                System.out.println("7. Editar cliente");
                System.out.println("8. Eliminar cliente");
            }

            // ---- PRODUCTOS ----
            if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_PRODUCTOS")) {
                System.out.println("9. Listar productos");
                System.out.println("10. Registrar producto");
                System.out.println("11. Editar producto");
                System.out.println("12. Eliminar producto");
            }

            // ---- VENTAS ----
            if (Permisos.tienePermiso(rolUsuario, "REGISTRAR_VENTA")) {
                System.out.println("13. Registrar venta");
            }

            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                // ---- USUARIOS ----
                case 1:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_USUARIOS")) listarUsuarios(em);
                    else System.out.println("No tiene permiso para esta acción.");
                    break;
                case 2:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_USUARIOS")) registrarUsuario(em, scanner);
                    else System.out.println("No tiene permiso para esta acción.");
                    break;
                case 3:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_USUARIOS")) editarUsuario(em, scanner);
                    else System.out.println("No tiene permiso para esta acción.");
                    break;
                case 4:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_USUARIOS")) eliminarUsuario(em, scanner);
                    else System.out.println("No tiene permiso para esta acción.");
                    break;

                // ---- CLIENTES ----
                case 5:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_CLIENTES")) listarClientes(em);
                    else System.out.println("No tiene permiso para esta acción.");
                    break;
                case 6:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_CLIENTES")) registrarCliente(em, scanner);
                    else System.out.println("No tiene permiso para esta acción.");
                    break;
                case 7:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_CLIENTES")) editarCliente(em, scanner);
                    else System.out.println("No tiene permiso para esta acción.");
                    break;
                case 8:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_CLIENTES")) eliminarCliente(em, scanner);
                    else System.out.println("No tiene permiso para esta acción.");
                    break;

                // ---- PRODUCTOS ----
                case 9:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_PRODUCTOS")) listarProductos(em);
                    else System.out.println("No tiene permiso para esta acción.");
                    break;
                case 10:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_PRODUCTOS")) registrarProducto(em, scanner);
                    else System.out.println("No tiene permiso para esta acción.");
                    break;
                case 11:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_PRODUCTOS")) editarProducto(em, scanner);
                    else System.out.println("No tiene permiso para esta acción.");
                    break;
                case 12:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_PRODUCTOS")) eliminarProducto(em, scanner);
                    else System.out.println("No tiene permiso para esta acción.");
                    break;

                // ---- VENTAS ----
                case 13:
                    if (Permisos.tienePermiso(rolUsuario, "REGISTRAR_VENTA")) registrarVenta(em, scanner, usuarioLogeado);
                    else System.out.println("No tiene permiso para esta accion.");
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no valida.");
            }

        } while (opcion != 0);

        em.close();
        emf.close();
        scanner.close();
    }

    // ==================== METODOS USUARIOS ====================
    private static void listarUsuarios(EntityManager em) {
        UsuarioJpaController usuarioJpa = new UsuarioJpaController(em.getEntityManagerFactory());
        System.out.println("\n===== Lista de Usuarios =====");
        usuarioJpa.findUsuarioEntities().forEach(u ->
                System.out.println(u.getIdusuario() + " | " + u.getNombre() + " | " + u.getEmail() + " | " + u.getRol())
        );
    }

    private static void registrarUsuario(EntityManager em, Scanner scanner) {
        UsuarioJpaController usuarioJpa = new UsuarioJpaController(em.getEntityManagerFactory());

        System.out.print("ID usuario: ");
        long idUsuario = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        System.out.print("Contrasena: ");
        String contrasena = scanner.nextLine();

        System.out.print("Rol (ADMINISTRADOR, VENDEDOR, etc): ");
        String rol = scanner.nextLine();

        Usuario nuevo = new Usuario();
        nuevo.setIdusuario(idUsuario);
        nuevo.setNombre(nombre);
        nuevo.setEmail(correo);
        nuevo.setContrasena(contrasena);
        nuevo.setRol(rol);

        boolean ok = usuarioJpa.registrarUsuario(nuevo);
        System.out.println(ok ? "Usuario registrado" : "Error al registrar");
    }

    private static void editarUsuario(EntityManager em, Scanner scanner) {
        System.out.print("Ingrese ID del usuario a editar: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Usuario u = em.find(Usuario.class, id);
        if (u == null) {
            System.out.println("Usuario no encontrado");
            return;
        }

        System.out.print("Nuevo nombre (" + u.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) u.setNombre(nombre);

        System.out.print("Nuevo correo (" + u.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) u.setEmail(email);

        System.out.print("Nueva contrasena: ");
        String pass = scanner.nextLine();
        if (!pass.isEmpty()) u.setContrasena(pass);

        System.out.print("Nuevo rol (" + u.getRol() + "): ");
        String rol = scanner.nextLine();
        if (!rol.isEmpty()) u.setRol(rol);

        em.getTransaction().begin();
        em.merge(u);
        em.getTransaction().commit();

        System.out.println("Usuario actualizado ?");
    }

    private static void eliminarUsuario(EntityManager em, Scanner scanner) {
        System.out.print("Ingrese ID del usuario a eliminar: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Usuario u = em.find(Usuario.class, id);
        if (u == null) {
            System.out.println("Usuario no encontrado ?");
            return;
        }

        em.getTransaction().begin();
        em.remove(u);
        em.getTransaction().commit();
        System.out.println("Usuario eliminado ?");
    }

    // ==================== METODOS CLIENTES ====================
    private static void listarClientes(EntityManager em) {
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        System.out.println("\n===== Lista de Clientes =====");
        for (Cliente c : clientes) {
            System.out.println(c.getIdCliente() + " | " + c.getNombre() + " | " + c.getTelefono());
        }
    }

    private static void registrarCliente(EntityManager em, Scanner scanner) {
        System.out.print("ID Cliente: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Telefono: ");
        String tel = scanner.nextLine();

        Cliente c = new Cliente();
        c.setIdCliente(id);
        c.setNombre(nombre);
        c.setTelefono(tel);

        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();

        System.out.println("Cliente registrado ?");
    }

    private static void editarCliente(EntityManager em, Scanner scanner) {
        System.out.print("Ingrese ID del cliente a editar: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Cliente c = em.find(Cliente.class, id);
        if (c == null) {
            System.out.println("Cliente no encontrado ?");
            return;
        }

        System.out.print("Nuevo nombre (" + c.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) c.setNombre(nombre);

        System.out.print("Nuevo telefono (" + c.getTelefono() + "): ");
        String tel = scanner.nextLine();
        if (!tel.isEmpty()) c.setTelefono(tel);

        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();

        System.out.println("Cliente actualizado ?");
    }

    private static void eliminarCliente(EntityManager em, Scanner scanner) {
        System.out.print("Ingrese ID del cliente a eliminar: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Cliente c = em.find(Cliente.class, id);
        if (c == null) {
            System.out.println("Cliente no encontrado ?");
            return;
        }

        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
        System.out.println("Cliente eliminado ?");
    }

    // ==================== METODOS PRODUCTOS ====================
    private static void listarProductos(EntityManager em) {
        List<Producto> productos = em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
        System.out.println("\n===== Lista de Productos =====");
        for (Producto p : productos) {
            System.out.println(p.getIdProducto() + " | " + p.getNombre() + " | Q" + p.getPrecioUnitario());
        }
    }

    private static void registrarProducto(EntityManager em, Scanner scanner) {
        System.out.print("ID Producto: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio unitario: ");
        float precio = scanner.nextFloat();
        scanner.nextLine();

        Producto p = new Producto();
        p.setIdProducto(id);
        p.setNombre(nombre);
        p.setPrecioUnitario(precio);

        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

        System.out.println("Producto registrado ?");
    }

    private static void editarProducto(EntityManager em, Scanner scanner) {
        System.out.print("Ingrese ID del producto a editar: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Producto p = em.find(Producto.class, id);
        if (p == null) {
            System.out.println("Producto no encontrado ?");
            return;
        }

        System.out.print("Nuevo nombre (" + p.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) p.setNombre(nombre);

        System.out.print("Nuevo precio (" + p.getPrecioUnitario() + "): ");
        String precioStr = scanner.nextLine();
        if (!precioStr.isEmpty()) p.setPrecioUnitario(Float.parseFloat(precioStr));

        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();

        System.out.println("Producto actualizado ?");
    }

    private static void eliminarProducto(EntityManager em, Scanner scanner) {
        System.out.print("Ingrese ID del producto a eliminar: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Producto p = em.find(Producto.class, id);
        if (p == null) {
            System.out.println("Producto no encontrado ?");
            return;
        }

        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        System.out.println("Producto eliminado ?");
    }

   
    // -------------------- VENTAS --------------------
private static void registrarVenta(EntityManager em, Scanner scanner, Usuario usuarioLogeado) {
    try {
        System.out.print("Ingrese ID del cliente: ");
        long idCliente = scanner.nextLong();
        scanner.nextLine();

        Cliente cliente = em.find(Cliente.class, idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado 😕");
            return;
        }

        long idVenta = System.currentTimeMillis();
        java.util.Date fecha = new java.util.Date();
        double totalVenta = 0.0;

        
        Venta venta = new Venta(idVenta, cliente.getIdCliente(), usuarioLogeado.getIdusuario(), fecha, totalVenta);

        List<DetalleFactura> detalles = new java.util.ArrayList<>();
        boolean agregarMas = true;

        while (agregarMas) {
            List<Producto> productos = em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
            System.out.println("\n===== Lista de Productos =====");
            for (Producto p : productos) {
                System.out.println(p.getIdProducto() + " | " + p.getNombre() + " | Q" + p.getPrecioUnitario());
            }

            System.out.print("Ingrese ID del producto: ");
            long idProducto = scanner.nextLong();
            scanner.nextLine();

            Producto producto = em.find(Producto.class, idProducto);
            if (producto == null) {
                System.out.println("Producto no encontrado ");
                continue;
            }

            System.out.print("Ingrese cantidad: ");
            int cantidad = scanner.nextInt();
            scanner.nextLine();

            float subtotal = cantidad * producto.getPrecioUnitario();
            totalVenta += subtotal;

            long idDetalle = System.currentTimeMillis();
            DetalleFactura detalle = new DetalleFactura();
            detalle.setIdDetalle(idDetalle);
            detalle.setVenta(venta);
            detalle.setIdProducto(idProducto);
            detalle.setCantidad(cantidad);
            detalle.setSubtotal(subtotal);
            detalle.setPrecioUnitario(producto.getPrecioUnitario());

            detalles.add(detalle);

            System.out.print("¿Desea agregar otro producto? (S/N): ");
            String resp = scanner.nextLine();
            if (!resp.equalsIgnoreCase("S")) agregarMas = false;
        }

        // Crear la factura
        long idFactura = System.currentTimeMillis() + 1;
        String numeroFactura = "FAC-" + idFactura;
        Factura factura = new Factura(idFactura, cliente.getIdCliente(), fecha, totalVenta, usuarioLogeado.getIdusuario());

        
        em.getTransaction().begin();
        em.persist(venta);
        for (DetalleFactura det : detalles) em.persist(det);
        em.persist(factura);
        em.getTransaction().commit();

        // ===== IMPRIMIR FACTURA EN CONSOL =====
        System.out.println("\n===== FACTURA =====");
        System.out.println("Número: " + numeroFactura);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Fecha: " + fecha);
        System.out.println("Productos:");
        for (DetalleFactura d : detalles) {
            Producto p = em.find(Producto.class, d.getIdProducto()); 
            System.out.println("  - " + p.getNombre() + " x" + d.getCantidad() + " = Q" + d.getSubtotal());
        }
        System.out.println("Total: Q" + totalVenta);
        System.out.println("==================");

    } catch (Exception e) {
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        e.printStackTrace();
        System.out.println("Error al registrar la venta ❌");
    }
}

}