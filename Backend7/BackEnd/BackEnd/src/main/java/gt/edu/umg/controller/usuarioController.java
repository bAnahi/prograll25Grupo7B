/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gt.edu.umg.model.usuario;
import gt.edu.umg.repository.usuarioRepository;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Famil
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/usuario")
public class usuarioController {
    
    @Inject
    usuarioRepository usuarioRepo;
    
    @POST
    public Response saveNewUser(usuario nuevoUsuario) {
        
        System.out.println("nuevoUsuario->" + nuevoUsuario.toString());
        JSONObject JsonRespuesta = new JSONObject();
        
        try {

            usuarioRepo.create(nuevoUsuario);
            
            JsonRespuesta.put("Estado",1);
            JsonRespuesta.put("Mensaje", "Usuario creado");
            

            

            return Response.ok().entity(JsonRespuesta).build();
        } catch (Exception e) {
            
            JsonRespuesta.put("Estado",0);
            JsonRespuesta.put("Mensaje", "Usuario no creado");
            return Response.serverError().entity(JsonRespuesta).build();
        }

    }

    
    @GET
    public Response findUsuario(){
        JSONObject JsonRespuesta = new JSONObject();
        try {
            
            List<usuario> lstUsuario = usuarioRepo.findUsuarios();
            
            ObjectMapper objMapper = new ObjectMapper();
            String jsonArray = objMapper.writeValueAsString(lstUsuario);
            
            JSONParser parser = new JSONParser();
            
            JSONArray jsonUsuarios = (JSONArray) parser.parse(jsonArray);
            JsonRespuesta.put("Estado",1);
            JsonRespuesta.put("Mensaje", "Consulta Exitosa");
            JsonRespuesta.put("Usuarios", jsonUsuarios);
            
            
            return Response.ok().entity(JsonRespuesta).build();
        } catch (Exception e){
             JsonRespuesta.put("Estado",0);
            JsonRespuesta.put("Mensaje", "Consulta no Exitosa");
            return Response.serverError().build();
            
        }
        
    }
 
      @PUT
@Path("/{id}")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
public Response actualizarUsuario(@PathParam("id") long id, usuario usuarioActualizado) {
    System.out.println("usuarioActualizado->" + usuarioActualizado.toString());
    JSONObject JsonRespuesta = new JSONObject();

    try {
        boolean actualizado = usuarioRepo.actualizarUsuario(id, usuarioActualizado);

        if (!actualizado) {
            JsonRespuesta.put("Estado", 0);
            JsonRespuesta.put("Mensaje", "Usuario no encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity(JsonRespuesta).build();
        }

        JsonRespuesta.put("Estado", 1);
        JsonRespuesta.put("Mensaje", "Usuario actualizado correctamente");
        return Response.ok(JsonRespuesta).build();

    } catch (Exception e) {
        JsonRespuesta.put("Estado", 0);
        JsonRespuesta.put("Mensaje", "Error al actualizar usuario: " + e.getMessage());
        return Response.serverError().entity(JsonRespuesta).build();
    }
}
        }
    




    
        

