package com.prograii25.prograii25grupo7b;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestConexion {
    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
            EntityManager em = emf.createEntityManager();

            System.out.println(" Conexion exitosa a la base de datos.");

            em.close();
            emf.close();
        } catch (Exception e) {
            System.out.println("? Error de conexion:");
            e.printStackTrace();
        }
    }
}
