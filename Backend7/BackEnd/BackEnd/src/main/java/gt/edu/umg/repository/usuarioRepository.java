/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.repository;

import gt.edu.umg.model.usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 *
 * @author Famil
 */
@RequestScoped
public class usuarioRepository implements PanacheRepository<usuario> {
    
    @Transactional
    
    public void create(usuario nuevoUsuario){
        this.persist(nuevoUsuario);
    }
    
    public List<usuario> findUsuarios(){
        try{
            Query q =getEntityManager().createQuery("SELECT C FROM usuario C order by C.id_usuario asc");
            return q.getResultList();
        } catch (Exception e){
            System.out.println("e->"+e);
            return null;
            
        }
    }
    
    @Transactional
    public boolean actualizarUsuario(long id, usuario datosActualizados) {
        usuario existente = findById(id);
        if (existente == null) {
            return false;
        }

        existente.setNombre(datosActualizados.getNombre());
        existente.setCorreo(datosActualizados.getCorreo());
        existente.setContraseña(datosActualizados.getContraseña());
        existente.setRol(datosActualizados.getRol());

        persist(existente);
        return true;
    }
}

    

