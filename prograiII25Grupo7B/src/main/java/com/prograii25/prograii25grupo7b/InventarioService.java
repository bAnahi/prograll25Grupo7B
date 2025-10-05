package com.prograii25.prograii25grupo7b.db;

import javax.persistence.*;
import java.util.List;

public class InventarioService {

    private EntityManagerFactory emf;
    private EntityManager em;

    public InventarioService() {
        emf = Persistence.createEntityManagerFactory("PU"); // tu persistence-unit
        em = emf.createEntityManager();
    }

    // Consultar stock de un producto
    public int consultarStock(long idProducto) {
        try {
            TypedQuery<Inventario> query = em.createQuery(
                "SELECT i FROM Inventario i WHERE i.idProducto = :idProducto", Inventario.class);
            query.setParameter("idProducto", idProducto);
            List<Inventario> result = query.getResultList();
            if (!result.isEmpty()) {
                return result.get(0).getCantidadDisponible();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Agregar stock
    public boolean agregarStock(long idProducto, int cantidad) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Inventario> query = em.createQuery(
                "SELECT i FROM Inventario i WHERE i.idProducto = :idProducto", Inventario.class);
            query.setParameter("idProducto", idProducto);
            List<Inventario> result = query.getResultList();
            if (!result.isEmpty()) {
                Inventario inv = result.get(0);
                inv.setCantidadDisponible(inv.getCantidadDisponible() + cantidad);
                em.merge(inv);
                tx.commit();
                return true;
            }
            tx.rollback();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
        return false;
    }

    public boolean reducirStock(long idProducto, int cantidad) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Inventario> query = em.createQuery(
                "SELECT i FROM Inventario i WHERE i.idProducto = :idProducto", Inventario.class);
            query.setParameter("idProducto", idProducto);
            List<Inventario> result = query.getResultList();
            if (!result.isEmpty()) {
                Inventario inv = result.get(0);
                if (inv.getCantidadDisponible() >= cantidad) {
                    inv.setCantidadDisponible(inv.getCantidadDisponible() - cantidad);
                    em.merge(inv);
                    tx.commit();
                    return true;
                }
            }
            tx.rollback();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
        return false;
    }

    
    public List<Inventario> listarInventario() {
        try {
            TypedQuery<Inventario> query = em.createQuery(
                "SELECT i FROM Inventario i", Inventario.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public void cerrar() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }
}
