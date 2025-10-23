/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gt.edu.umg.model.cliente;
import gt.edu.umg.repository.clienteRepository;
import org.json.simple.parser.JSONParser;
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

/**
 *
 * @author Famil
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/cliente")
public class clienteController {

    @Inject
    clienteRepository clienteRepo;

    @POST
    public Response saveNewClient(cliente nuevoCliente) {
        
        
        System.out.println("nuevoCliente->" + nuevoCliente.toString());
        JSONObject JsonRespuesta = new JSONObject();

        try {

            clienteRepo.create(nuevoCliente);
            
            JsonRespuesta.put("Estado",1);
            JsonRespuesta.put("Mensaje", "Cliente creado");
            

            

            return Response.ok().entity(JsonRespuesta).build();
        } catch (Exception e) {
            
            JsonRespuesta.put("Estado",0);
            JsonRespuesta.put("Mensaje", "Cliente no creado");
            return Response.serverError().entity(JsonRespuesta).build();
        }

    }

    
    @GET
    public Response findClientes(){
        JSONObject JsonRespuesta = new JSONObject();
        try {
            
            List<cliente> lstCliente = clienteRepo.findClientes();
            
            ObjectMapper objMapper = new ObjectMapper();
            String jsonArray = objMapper.writeValueAsString(lstCliente);
            
            JSONParser parser = new JSONParser();
            
            JSONArray jsonClientes = (JSONArray) parser.parse(jsonArray);
            JsonRespuesta.put("Estado",1);
            JsonRespuesta.put("Mensaje", "Consulta Exitosa");
            JsonRespuesta.put("Clientes", jsonClientes);
            
            
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
public Response actualizarCliente(@PathParam("id") long id, cliente clienteActualizado) {
    System.out.println("clienteActualizado->" + clienteActualizado.toString());
    JSONObject JsonRespuesta = new JSONObject();

    try {
        boolean actualizado = clienteRepo.actualizarCliente(id, clienteActualizado);

        if (!actualizado) {
            JsonRespuesta.put("Estado", 0);
            JsonRespuesta.put("Mensaje", "Cliente no encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity(JsonRespuesta).build();
        }

        JsonRespuesta.put("Estado", 1);
        JsonRespuesta.put("Mensaje", "Cliente actualizado correctamente");
        return Response.ok(JsonRespuesta).build();

    } catch (Exception e) {
        JsonRespuesta.put("Estado", 0);
        JsonRespuesta.put("Mensaje", "Error al actualizar cliente: " + e.getMessage());
        return Response.serverError().entity(JsonRespuesta).build();
    }
}
        }
    



