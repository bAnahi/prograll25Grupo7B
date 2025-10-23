/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.repository;

import gt.edu.umg.model.cliente;
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
public class clienteRepository implements PanacheRepository<cliente> {
    
    @Transactional
    
    public void create(cliente nuevoCliente){
        this.persist(nuevoCliente);
    }
    
    public List<cliente> findClientes(){
        try{
            Query q =getEntityManager().createQuery("SELECT C FROM cliente C order by C.id_cliente asc");
            return q.getResultList();
        } catch (Exception e){
            System.out.println("e->"+e);
            return null;
            
        }
    }
    
    @Transactional
    public boolean actualizarCliente(long id, cliente datosActualizados) {
        cliente existente = findById(id);
        if (existente == null) {
            return false;
        }

        existente.setNombre(datosActualizados.getNombre());
        existente.setCorreo(datosActualizados.getCorreo());
        existente.setTelefono(datosActualizados.getTelefono());
        existente.setDireccion(datosActualizados.getDireccion());

        persist(existente);
        return true;
    }
}
