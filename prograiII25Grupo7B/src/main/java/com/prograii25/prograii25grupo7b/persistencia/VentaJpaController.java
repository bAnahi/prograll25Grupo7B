
package com.prograii25.prograii25grupo7b.persistencia;

import com.prograii25.prograii25grupo7b.db.Venta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class VentaJpaController {

    private EntityManagerFactory emf = null;

    public VentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta venta) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(venta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Venta> findVentaEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT v FROM Venta v", Venta.class).getResultList();
        } finally {
            em.close();
        }
    }
}

