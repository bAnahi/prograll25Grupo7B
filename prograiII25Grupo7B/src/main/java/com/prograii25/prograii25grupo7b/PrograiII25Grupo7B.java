package com.prograii25.prograii25grupo7b;

import com.prograii25.prograii25grupo7b.db.Usuario;
import com.prograii25.prograii25grupo7b.db.Cliente;
import com.prograii25.prograii25grupo7b.db.Producto;
import com.prograii25.prograii25grupo7b.db.DetalleFactura;
import com.prograii25.prograii25grupo7b.db.Venta;
import com.prograii25.prograii25grupo7b.persistencia.UsuarioJpaController;
import com.prograii25.prograii25grupo7b.persistencia.ClienteJpaController;
import com.prograii25.prograii25grupo7b.persistencia.ProductoJpaController;
import com.prograii25.prograii25grupo7b.db.Permisos;

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

        // ===== MENÚ PRINCIPAL =====
        int opcion;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Registrar usuario");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");

            System.out.println("6. Listar clientes");
            System.out.println("7. Registrar cliente");
            System.out.println("8. Actualizar cliente");
            System.out.println("9. Eliminar cliente");

            System.out.println("11. Listar productos");
            System.out.println("12. Registrar producto");
            System.out.println("13. Actualizar producto");
            System.out.println("14. Eliminar producto");

            System.out.println("15. Registrar venta"); // <-- NUEVA OPCIÓN
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                // ===== CRUD USUARIO =====
                case 1: listarUsuarios(em); break;
                case 2: registrarUsuario(em, scanner); break;
                case 3: actualizarUsuario(em, scanner); break;
                case 4: eliminarUsuario(em, scanner); break;

                // ===== CRUD CLIENTE =====
                case 6: listarClientes(em); break;
                case 7: registrarCliente(em, scanner); break;
                case 8: actualizarCliente(em, scanner); break;
                case 9: eliminarCliente(em, scanner); break;

                // ===== CRUD PRODUCTO =====
                case 11: listarProductos(em); break;
                case 12: registrarProducto(em, scanner); break;
                case 13: actualizarProducto(em, scanner); break;
                case 14: eliminarProducto(em, scanner); break;

                // ===== VENTAS =====
                case 15: registrarVenta(em, scanner, usuarioLogeado); break;

                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opcion no valida."); break;
            }

        } while (opcion != 0);

        em.close();
        emf.close();
        scanner.close();
    }

    // ==================== MÉTODOS USUARIO ====================
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
        long idUsuario = scanner.nextLong(); scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Contrasena: ");
        String contrasena = scanner.nextLine();
        System.out.print("Rol: ");
        String rol = scanner.nextLine();

        Usuario nuevo = new Usuario();
        nuevo.setIdusuario(idUsuario);
        nuevo.setNombre(nombre);
        nuevo.setEmail(correo);
        nuevo.setContrasena(contrasena);
        nuevo.setRol(rol);

        boolean ok = usuarioJpa.registrarUsuario(nuevo);
        System.out.println(ok ? "Usuario registrado ✅" : "Error al registrar ❌");
    }

    private static void actualizarUsuario(EntityManager em, Scanner scanner) {
        UsuarioJpaController usuarioJpa = new UsuarioJpaController(em.getEntityManagerFactory());

        System.out.print("Ingrese ID del usuario a actualizar: ");
        long id = scanner.nextLong(); scanner.nextLine();

        Usuario usuario = usuarioJpa.findUsuarioEntities().stream()
                .filter(u -> u.getIdusuario() == id)
                .findFirst()
                .orElse(null);

        if (usuario == null) { System.out.println("Usuario no encontrado."); return; }

        System.out.print("Nuevo nombre (" + usuario.getNombre() + "): ");
        String nombre = scanner.nextLine(); if (!nombre.isEmpty()) usuario.setNombre(nombre);
        System.out.print("Nuevo correo (" + usuario.getEmail() + "): ");
        String correo = scanner.nextLine(); if (!correo.isEmpty()) usuario.setEmail(correo);
        System.out.print("Nueva contrasena: ");
        String contrasena = scanner.nextLine(); if (!contrasena.isEmpty()) usuario.setContrasena(contrasena);
        System.out.print("Nuevo rol (" + usuario.getRol() + "): ");
        String rol = scanner.nextLine(); if (!rol.isEmpty()) usuario.setRol(rol);

        boolean ok = usuarioJpa.actualizarUsuario(usuario);
        System.out.println(ok ? "Usuario actualizado ✅" : "Error al actualizar ❌");
    }

    private static void eliminarUsuario(EntityManager em, Scanner scanner) {
        UsuarioJpaController usuarioJpa = new UsuarioJpaController(em.getEntityManagerFactory());

        System.out.print("Ingrese ID del usuario a eliminar: ");
        long id = scanner.nextLong(); scanner.nextLine();

        boolean ok = usuarioJpa.eliminarUsuario(id);
        System.out.println(ok ? "Usuario eliminado ✅" : "Error al eliminar ❌");
    }

    // ==================== MÉTODOS CLIENTE ====================
    private static void listarClientes(EntityManager em) {
        ClienteJpaController clienteJpa = new ClienteJpaController(em.getEntityManagerFactory());
        System.out.println("\n===== Lista de Clientes =====");
        clienteJpa.findClienteEntities().forEach(c ->
                System.out.println(c.getIdCliente() + " | " + c.getNombre() + " | " + c.getCorreo() + " | " + c.getTelefono() + " | " + c.getDireccion())
        );
    }

    private static void registrarCliente(EntityManager em, Scanner scanner) {
        ClienteJpaController clienteJpa = new ClienteJpaController(em.getEntityManagerFactory());

        System.out.print("ID cliente: "); long idCliente = scanner.nextLong(); scanner.nextLine();
        System.out.print("Nombre: "); String nombre = scanner.nextLine();
        System.out.print("Correo: "); String correo = scanner.nextLine();
        System.out.print("Telefono: "); String telefono = scanner.nextLine();
        System.out.print("Direccion: "); String direccion = scanner.nextLine();

        Cliente nuevo = new Cliente(idCliente, nombre, correo, telefono, direccion);
        boolean ok = clienteJpa.registrarCliente(nuevo);
        System.out.println(ok ? "Cliente registrado ✅" : "Error al registrar ❌");
    }

    private static void actualizarCliente(EntityManager em, Scanner scanner) {
        ClienteJpaController clienteJpa = new ClienteJpaController(em.getEntityManagerFactory());

        System.out.print("Ingrese ID del cliente a actualizar: "); long id = scanner.nextLong(); scanner.nextLine();
        Cliente cliente = clienteJpa.findCliente(id);
        if (cliente == null) { System.out.println("Cliente no encontrado."); return; }

        System.out.print("Nuevo nombre (" + cliente.getNombre() + "): "); String nombre = scanner.nextLine(); if (!nombre.isEmpty()) cliente.setNombre(nombre);
        System.out.print("Nuevo correo (" + cliente.getCorreo() + "): "); String correo = scanner.nextLine(); if (!correo.isEmpty()) cliente.setCorreo(correo);
        System.out.print("Nuevo telefono (" + cliente.getTelefono() + "): "); String telefono = scanner.nextLine(); if (!telefono.isEmpty()) cliente.setTelefono(telefono);
        System.out.print("Nueva direccion (" + cliente.getDireccion() + "): "); String direccion = scanner.nextLine(); if (!direccion.isEmpty()) cliente.setDireccion(direccion);

        boolean ok = clienteJpa.actualizarCliente(cliente);
        System.out.println(ok ? "Cliente actualizado ✅" : "Error al actualizar ❌");
    }

    private static void eliminarCliente(EntityManager em, Scanner scanner) {
        ClienteJpaController clienteJpa = new ClienteJpaController(em.getEntityManagerFactory());

        System.out.print("Ingrese ID del cliente a eliminar: "); long id = scanner.nextLong(); scanner.nextLine();
        boolean ok = clienteJpa.eliminarCliente(id);
        System.out.println(ok ? "Cliente eliminado ✅" : "Error al eliminar ❌");
    }

    // ==================== MÉTODOS PRODUCTO ====================
    private static void listarProductos(EntityManager em) {
        ProductoJpaController productoJpa = new ProductoJpaController(em.getEntityManagerFactory());
        System.out.println("\n===== Lista de Productos =====");
        productoJpa.findProductoEntities().forEach(p ->
                System.out.println(p.getIdProducto() + " | " + p.getNombre() + " | " + p.getDescripcion() + " | Q" + p.getPrecioUnitario())
        );
    }

    private static void registrarProducto(EntityManager em, Scanner scanner) {
        ProductoJpaController productoJpa = new ProductoJpaController(em.getEntityManagerFactory());

        System.out.print("ID producto: "); long idProducto = scanner.nextLong(); scanner.nextLine();
        System.out.print("Nombre: "); String nombre = scanner.nextLine();
        System.out.print("Descripción: "); String descripcion = scanner.nextLine();
        System.out.print("Precio unitario: "); float precio = scanner.nextFloat(); scanner.nextLine();

        Producto nuevo = new Producto(idProducto, nombre, descripcion, precio);
        boolean ok = productoJpa.registrarProducto(nuevo);
        System.out.println(ok ? "Producto registrado ✅" : "Error al registrar ❌");
    }

    private static void actualizarProducto(EntityManager em, Scanner scanner) {
        ProductoJpaController productoJpa = new ProductoJpaController(em.getEntityManagerFactory());

        System.out.print("Ingrese ID del producto a actualizar: "); long id = scanner.nextLong(); scanner.nextLine();
        Producto producto = productoJpa.findProducto(id);
        if (producto == null) { System.out.println("Producto no encontrado."); return; }

        System.out.print("Nuevo nombre (" + producto.getNombre() + "): "); String nombre = scanner.nextLine(); if (!nombre.isEmpty()) producto.setNombre(nombre);
        System.out.print("Nueva descripcion (" + producto.getDescripcion() + "): "); String descripcion = scanner.nextLine(); if (!descripcion.isEmpty()) producto.setDescripcion(descripcion);
        System.out.print("Nuevo precio unitario (" + producto.getPrecioUnitario() + "): "); String precioStr = scanner.nextLine(); if (!precioStr.isEmpty()) producto.setPrecioUnitario(Float.parseFloat(precioStr));

        boolean ok = productoJpa.actualizarProducto(producto);
        System.out.println(ok ? "Producto actualizado ✅" : "Error al actualizar ❌");
    }

    private static void eliminarProducto(EntityManager em, Scanner scanner) {
        ProductoJpaController productoJpa = new ProductoJpaController(em.getEntityManagerFactory());

        System.out.print("Ingrese ID del producto a eliminar: "); long id = scanner.nextLong(); scanner.nextLine();
        boolean ok = productoJpa.eliminarProducto(id);
        System.out.println(ok ? "Producto eliminado ✅" : "Error al eliminar ❌");
    }

    // ==================== MÉTODO VENTA ====================
    private static void registrarVenta(EntityManager em, Scanner scanner, Usuario usuarioLogeado) {
        try {
            System.out.print("Ingrese ID del cliente: ");
            long idCliente = scanner.nextLong();
            scanner.nextLine();

            Cliente cliente = em.find(Cliente.class, idCliente);
            if (cliente == null) {
                System.out.println("Cliente no encontrado ❌");
                return;
            }

            long idVenta = System.currentTimeMillis(); // Generar ID único
            java.util.Date fecha = new java.util.Date();
            double totalVenta = 0.0;

            Venta venta = new Venta();
            venta.setIdVenta(idVenta);
            venta.setIdCliente(cliente.getIdCliente());
            venta.setIdUsuario(usuarioLogeado.getIdusuario());
            venta.setFecha(fecha);
            venta.setTotal(totalVenta);

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
                    System.out.println("Producto no encontrado ❌");
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

                detalles.add(detalle);

                System.out.print("¿Desea agregar otro producto? (S/N): ");
                String resp = scanner.nextLine();
                if (!resp.equalsIgnoreCase("S")) {
                    agregarMas = false;
                }
            }

            venta.setTotal(totalVenta);

            em.getTransaction().begin();
            em.persist(venta);
            for (DetalleFactura det : detalles) {
                em.persist(det);
            }
            em.getTransaction().commit();

            System.out.println("Venta registrada ✅ Total: Q" + totalVenta);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Error al registrar la venta ❌");
        }
    }
}
