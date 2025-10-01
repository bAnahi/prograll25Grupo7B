package com.prograii25.prograii25grupo7b;

import com.prograii25.prograii25grupo7b.db.Usuario;
import com.prograii25.prograii25grupo7b.persistencia.UsuarioJpaController;
import com.prograii25.prograii25grupo7b.persistencia.ClienteJpaController;
import com.prograii25.prograii25grupo7b.persistencia.ProductoJpaController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class PrograiII25Grupo7B {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        UsuarioJpaController usuarioJpa = new UsuarioJpaController(em.getEntityManagerFactory());

        // ===== LOGIN =====
        boolean loginOk = false;
        while (!loginOk) {
            System.out.println("===== LOGIN =====");
            System.out.print("Correo: ");
            String correo = scanner.nextLine();

            System.out.print("Contrasena: ");
            String contrasena = scanner.nextLine();

            loginOk = usuarioJpa.login(correo, contrasena);

            if (!loginOk) {
                System.out.println("Credenciales incorrectas. Intente nuevamente.");
            }
        }

        // ===== MENU PRINCIPAL =====
        int opcion;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Usuario - Listar");
            System.out.println("2. Usuario - Registrar");
            System.out.println("3. Usuario - Actualizar");
            System.out.println("4. Usuario - Eliminar");
            System.out.println("5. Cliente - Listar");
            System.out.println("6. Cliente - Registrar");
            System.out.println("7. Cliente - Actualizar");
            System.out.println("8. Cliente - Eliminar");
            System.out.println("9. Producto - Listar");
            System.out.println("10. Producto - Registrar");
            System.out.println("11. Producto - Actualizar");
            System.out.println("12. Producto - Eliminar");
            System.out.println("13. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                // ===== USUARIO =====
                case 1 -> listarUsuarios(em);
                case 2 -> registrarUsuario(em, scanner);
                case 3 -> actualizarUsuario(em, scanner);
                case 4 -> eliminarUsuario(em, scanner);

                // ===== CLIENTE =====
                case 5 -> listarClientes(em);
                case 6 -> registrarCliente(em, scanner);
                case 7 -> actualizarCliente(em, scanner);
                case 8 -> eliminarCliente(em, scanner);

                // ===== PRODUCTO =====
                case 9 -> listarProductos(em);
                case 10 -> registrarProducto(em, scanner);
                case 11 -> actualizarProducto(em, scanner);
                case 12 -> eliminarProducto(em, scanner);

                case 13 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcion != 13);

        em.close();
        emf.close();
        scanner.close();
    }

    // ================== USUARIO ==================
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

        System.out.print("Rol: ");
        String rol = scanner.nextLine();

        Usuario nuevo = new Usuario();
        nuevo.setIdusuario(idUsuario);
        nuevo.setNombre(nombre);
        nuevo.setEmail(correo);
        nuevo.setContrasena(contrasena);
        nuevo.setRol(rol);

        boolean ok = usuarioJpa.registrarUsuario(nuevo);
        System.out.println(ok ? "Usuario registrado ✔" : "Error al registrar ❌");
    }

    private static void actualizarUsuario(EntityManager em, Scanner scanner) {
        UsuarioJpaController usuarioJpa = new UsuarioJpaController(em.getEntityManagerFactory());

        System.out.print("Ingrese el ID del usuario a actualizar: ");
        long idUsuario = scanner.nextLong();
        scanner.nextLine();

        Usuario usuario = usuarioJpa.findUsuario(idUsuario);
        if (usuario == null) {
            System.out.println("Usuario no encontrado ❌");
            return;
        }

        System.out.print("Nuevo nombre (actual: " + usuario.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) usuario.setNombre(nombre);

        System.out.print("Nuevo correo (actual: " + usuario.getEmail() + "): ");
        String correo = scanner.nextLine();
        if (!correo.isEmpty()) usuario.setEmail(correo);

        System.out.print("Nueva contrasena (actual: " + usuario.getContrasena() + "): ");
        String contrasena = scanner.nextLine();
        if (!contrasena.isEmpty()) usuario.setContrasena(contrasena);

        System.out.print("Nuevo rol (actual: " + usuario.getRol() + "): ");
        String rol = scanner.nextLine();
        if (!rol.isEmpty()) usuario.setRol(rol);

        boolean ok = usuarioJpa.actualizarUsuario(usuario);
        System.out.println(ok ? "Usuario actualizado ✔" : "Error al actualizar ❌");
    }

    private static void eliminarUsuario(EntityManager em, Scanner scanner) {
        UsuarioJpaController usuarioJpa = new UsuarioJpaController(em.getEntityManagerFactory());

        System.out.print("Ingrese el ID del usuario a eliminar: ");
        long idUsuario = scanner.nextLong();
        scanner.nextLine();

        boolean ok = usuarioJpa.eliminarUsuario(idUsuario);
        System.out.println(ok ? "Usuario eliminado ✔" : "Error al eliminar ❌");
    }

    // ================== CLIENTE ==================
    private static void listarClientes(EntityManager em) {
        ClienteJpaController clienteJpa = new ClienteJpaController(em.getEntityManagerFactory());
        System.out.println("\n===== Lista de Clientes =====");
        clienteJpa.findClienteEntities().forEach(c ->
                System.out.println(c.getIdCliente() + " | " + c.getNombre() + " | " + c.getCorreo() + " | " + c.getTelefono() + " | " + c.getDireccion())
        );
    }

    private static void registrarCliente(EntityManager em, Scanner scanner) {
        ClienteJpaController clienteJpa = new ClienteJpaController(em.getEntityManagerFactory());

        System.out.print("ID cliente: ");
        long idCliente = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();

        System.out.print("Direccion: ");
        String direccion = scanner.nextLine();

        com.prograii25.prograii25grupo7b.Cliente nuevo = new com.prograii25.prograii25grupo7b.Cliente(idCliente, nombre, correo, telefono, direccion);

        boolean ok = clienteJpa.registrarCliente(nuevo);
        System.out.println(ok ? "Cliente registrado ✔" : "Error al registrar ❌");
    }

    private static void actualizarCliente(EntityManager em, Scanner scanner) {
        ClienteJpaController clienteJpa = new ClienteJpaController(em.getEntityManagerFactory());

        System.out.print("Ingrese el ID del cliente a actualizar: ");
        long idCliente = scanner.nextLong();
        scanner.nextLine();

        com.prograii25.prograii25grupo7b.Cliente cliente = clienteJpa.findCliente(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado ❌");
            return;
        }

        System.out.print("Nuevo nombre (actual: " + cliente.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) cliente.setNombre(nombre);

        System.out.print("Nuevo correo (actual: " + cliente.getCorreo() + "): ");
        String correo = scanner.nextLine();
        if (!correo.isEmpty()) cliente.setCorreo(correo);

        System.out.print("Nuevo telefono (actual: " + cliente.getTelefono() + "): ");
        String telefono = scanner.nextLine();
        if (!telefono.isEmpty()) cliente.setTelefono(telefono);

        System.out.print("Nueva direccion (actual: " + cliente.getDireccion() + "): ");
        String direccion = scanner.nextLine();
        if (!direccion.isEmpty()) cliente.setDireccion(direccion);

        boolean ok = clienteJpa.actualizarCliente(cliente);
        System.out.println(ok ? "Cliente actualizado ✔" : "Error al actualizar ❌");
    }

    private static void eliminarCliente(EntityManager em, Scanner scanner) {
        ClienteJpaController clienteJpa = new ClienteJpaController(em.getEntityManagerFactory());

        System.out.print("Ingrese el ID del cliente a eliminar: ");
        long idCliente = scanner.nextLong();
        scanner.nextLine();

        boolean ok = clienteJpa.eliminarCliente(idCliente);
        System.out.println(ok ? "Cliente eliminado ✔" : "Error al eliminar ❌");
    }

    // ================== PRODUCTO ==================
    private static void listarProductos(EntityManager em) {
        ProductoJpaController productoJpa = new ProductoJpaController(em.getEntityManagerFactory());
        System.out.println("\n===== Lista de Productos =====");
        productoJpa.findProductoEntities().forEach(p ->
                System.out.println(p.getIdProducto() + " | " + p.getNombre() + " | " + p.getDescripcion() + " | " + p.getPrecioUnitario())
        );
    }

    private static void registrarProducto(EntityManager em, Scanner scanner) {
        ProductoJpaController productoJpa = new ProductoJpaController(em.getEntityManagerFactory());

        System.out.print("ID producto: ");
        long idProducto = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();

        System.out.print("Precio unitario: ");
        float precioUnitario = scanner.nextFloat();
        scanner.nextLine();

        com.prograii25.prograii25grupo7b.Producto nuevo = new com.prograii25.prograii25grupo7b.Producto(idProducto, nombre, descripcion, precioUnitario);

        boolean ok = productoJpa.registrarProducto(nuevo);
        System.out.println(ok ? "Producto registrado ✔" : "Error al registrar ❌");
    }

    private static void actualizarProducto(EntityManager em, Scanner scanner) {
        ProductoJpaController productoJpa = new ProductoJpaController(em.getEntityManagerFactory());

        System.out.print("Ingrese el ID del producto a actualizar: ");
        long idProducto = scanner.nextLong();
        scanner.nextLine();

        com.prograii25.prograii25grupo7b.Producto producto = productoJpa.findProducto(idProducto);
        if (producto == null) {
            System.out.println("Producto no encontrado ❌");
            return;
        }

        System.out.print("Nuevo nombre (actual: " + producto.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) producto.setNombre(nombre);

        System.out.print("Nueva descripcion (actual: " + producto.getDescripcion() + "): ");
        String descripcion = scanner.nextLine();
        if (!descripcion.isEmpty()) producto.setDescripcion(descripcion);

        System.out.print("Nuevo precio unitario (actual: " + producto.getPrecioUnitario() + "): ");
        String precioStr = scanner.nextLine();
        if (!precioStr.isEmpty()) producto.setPrecioUnitario(Float.parseFloat(precioStr));

        boolean ok = productoJpa.actualizarProducto(producto);
        System.out.println(ok ? "Producto actualizado ✔" : "Error al actualizar ❌");
    }

    private static void eliminarProducto(EntityManager em, Scanner scanner) {
        ProductoJpaController productoJpa = new ProductoJpaController(em.getEntityManagerFactory());

        System.out.print("Ingrese el ID del producto a eliminar: ");
        long idProducto = scanner.nextLong();
        scanner.nextLine();

        boolean ok = productoJpa.eliminarProducto(idProducto);
        System.out.println(ok ? "Producto eliminado ✔" : "Error al eliminar ❌");
    }
}
