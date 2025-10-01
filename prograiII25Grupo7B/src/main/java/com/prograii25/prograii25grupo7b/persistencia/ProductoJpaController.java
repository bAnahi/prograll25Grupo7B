package com.prograii25.prograii25grupo7b.persistencia;

import com.prograii25.prograii25grupo7b.Producto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductoJpaController {

    private EntityManagerFactory emf = null;

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Producto> findProductoEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
        } finally {
            em.close();
        }
    }

    public boolean registrarProducto(Producto producto) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(producto);
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

    public Producto findProducto(long idProducto) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, idProducto);
        } finally {
            em.close();
        }
    }

    public boolean actualizarProducto(Producto producto) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(producto);
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
    
    public boolean eliminarProducto(long idProducto) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, idProducto);
            if (producto != null) {
                em.remove(producto);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
