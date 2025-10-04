package com.prograii25.prograii25grupo7b.persistencia;

import com.prograii25.prograii25grupo7b.db.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class UsuarioJpaController {

    private EntityManagerFactory emf = null;

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // ========== LOGIN ==========
    public boolean login(String correo, String contrasena) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.email = :correo AND u.contrasena = :contrasena", Usuario.class
            );
            query.setParameter("correo", correo);
            query.setParameter("contrasena", contrasena);

            List<Usuario> result = query.getResultList();
            return !result.isEmpty();
        } finally {
            em.close();
        }
    }

    // ========== READ ==========
    public List<Usuario> findUsuarioEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    // ========== CREATE ==========
    public boolean registrarUsuario(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
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

    // ========== UPDATE ==========
    public boolean actualizarUsuario(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario); // merge actualiza el registro
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

    // ========== DELETE ==========
    public boolean eliminarUsuario(long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                em.remove(usuario);
                em.getTransaction().commit();
                return true;
            } else {
                return false; // No se encontró el usuario
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
