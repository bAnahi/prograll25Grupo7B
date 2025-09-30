package com.prograii25.prograii25grupo7b;

import com.prograii25.prograii25grupo7b.db.Usuario;
import com.prograii25.prograii25grupo7b.persistencia.UsuarioJpaController;

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

        // ===== LOGIN INICIAL =====
        boolean loginOk = false;
        while (!loginOk) {
            System.out.println("===== LOGIN =====");
            System.out.print("Correo: ");
            String correo = scanner.nextLine();

            System.out.print("Contraseña: ");
            String contrasena = scanner.nextLine();

            loginOk = usuarioJpa.login(correo, contrasena);

            if (!loginOk) {
                System.out.println("Credenciales incorrectas. Intente nuevamente.");
            }
        }

        // ===== MENÚ PRINCIPAL =====
        int opcion;
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Registrar usuario");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    listarUsuarios(em);
                    break;
                case 2:
                    registrarUsuario(em, scanner);
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);

        em.close();
        emf.close();
        scanner.close();
    }

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

        System.out.print("Rol: ");
        String rol = scanner.nextLine();

        Usuario nuevo = new Usuario();
        nuevo.setIdusuario(idUsuario);
        nuevo.setNombre(nombre);
        nuevo.setEmail(correo);
        nuevo.setContrasena(contrasena);
        nuevo.setRol(rol);

        boolean ok = usuarioJpa.registrarUsuario(nuevo);
        System.out.println(ok ? "Usuario registrado ?" : "Error al registrar ");
    }
}

