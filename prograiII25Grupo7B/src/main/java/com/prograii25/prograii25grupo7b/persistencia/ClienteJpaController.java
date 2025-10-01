package com.prograii25.prograii25grupo7b.persistencia;

import com.prograii25.prograii25grupo7b.Cliente;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClienteJpaController {

    private EntityManagerFactory emf = null;

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear/Registrar cliente
    public boolean registrarCliente(Cliente cliente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Obtener todos los clientes
    public List<Cliente> findClienteEntities() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Actualizar cliente
    public boolean actualizarCliente(Cliente cliente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Eliminar cliente
    public boolean eliminarCliente(long idCliente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, idCliente);
            if (cliente != null) {
                em.remove(cliente);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
