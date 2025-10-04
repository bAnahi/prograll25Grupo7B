package com.prograii25.prograii25grupo7b;

import com.prograii25.prograii25grupo7b.db.Usuario;
import com.prograii25.prograii25grupo7b.db.Cliente;
import com.prograii25.prograii25grupo7b.db.Producto;
import com.prograii25.prograii25grupo7b.db.DetalleFactura;
import com.prograii25.prograii25grupo7b.db.Venta;
import com.prograii25.prograii25grupo7b.persistencia.UsuarioJpaController;
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

        Rol rolUsuario = usuarioLogeado.getRolEnum();

        // ===== MENÚ PRINCIPAL =====
        int opcion;
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_USUARIOS")) {
                System.out.println("1. Listar usuarios");
                System.out.println("2. Registrar usuario");
            }
            if (Permisos.tienePermiso(rolUsuario, "REGISTRAR_VENTA")) {
                System.out.println("3. Registrar venta");
            }
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_USUARIOS")) {
                        listarUsuarios(em);
                    } else {
                        System.out.println("No tiene permiso para esta acción.");
                    }
                    break;
                case 2:
                    if (Permisos.tienePermiso(rolUsuario, "GESTIONAR_USUARIOS")) {
                        registrarUsuario(em, scanner);
                    } else {
                        System.out.println("No tiene permiso para esta acción.");
                    }
                    break;
                case 3:
                    if (Permisos.tienePermiso(rolUsuario, "REGISTRAR_VENTA")) {
                        registrarVenta(em, scanner, usuarioLogeado);
                    } else {
                        System.out.println("No tiene permiso para esta acción.");
                    }
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        em.close();
        emf.close();
        scanner.close();
    }

    // ==================== MÉTODOS ====================

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

        System.out.print("Contraseña: ");
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
        System.out.println(ok ? "Usuario registrado ?" : "Error al registrar ?");
    }

    // -------------------- VENTAS --------------------

    private static void registrarVenta(EntityManager em, Scanner scanner, Usuario usuarioLogeado) {
        try {
            System.out.print("Ingrese ID del cliente: ");
            long idCliente = scanner.nextLong();
            scanner.nextLine();

            Cliente cliente = em.find(Cliente.class, idCliente);
            if (cliente == null) {
                System.out.println("Cliente no encontrado ?");
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
                    System.out.println("Producto no encontrado ?");
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

            em.getTransaction().begin();
            em.persist(venta);
            for (DetalleFactura det : detalles) {
                em.persist(det);
            }
            em.getTransaction().commit();

            System.out.println("Venta registrada ? Total: Q" + totalVenta);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Error al registrar la venta ?");
        }
    }
}







