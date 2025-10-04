
package com.prograii25.prograii25grupo7b.persistencia;

import com.prograii25.prograii25grupo7b.db.DetalleFactura;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class DetalleFacturaJpaController {

    private EntityManagerFactory emf = null;

    public DetalleFacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleFactura detalle) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(detalle);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<DetalleFactura> findDetalleVentaEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT d FROM DetalleFactura d", DetalleFactura.class).getResultList();
        } finally {
            em.close();
        }
    }
}
