package ws;

import dominio.ClienteImp;
import dto.RSCliente;
import dto.Respuesta;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Cliente;

/**
 *
 * @authors Ohana & Benito
 */
@Path("cliente")
public class ClienteWS {

    // Obtener todos
    @GET
    @Path("obtener-todos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> obtenerTodos() {
        return ClienteImp.obtenerTodos();
    }

    // Insertar
    @POST
    @Path("insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RSCliente insertarCliente(Cliente cliente) {
        if (cliente != null) {
            return ClienteImp.insertar(cliente);
        }
        throw new BadRequestException("El cliente recibido es nulo.");
    }

    // Actualizar
    @PUT
    @Path("actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta actualizarCliente(Cliente cliente) {
        if (cliente != null && cliente.getIdCliente() != null) {
            return ClienteImp.actualizar(cliente);
        }
        throw new BadRequestException("Datos insuficientes para actualizar.");
    }

    // Eliminar
    @DELETE
    @Path("eliminar/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminarCliente(@PathParam("idCliente") Integer idCliente) {
        if (idCliente != null) {
            return ClienteImp.eliminar(idCliente);
        }
        throw new BadRequestException("Debe enviar un idCliente.");
    }

    // Buscar por Nombre
    @GET
    @Path("buscar/nombre/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> buscarPorNombre(@PathParam("param") String param) {
        return ClienteImp.buscarPorNombre(param);
    }

    // Buscar por Telefono
    @GET
    @Path("buscar/telefono/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> buscarPorTelefono(@PathParam("param") String param) {
        return ClienteImp.buscarPorTelefono(param);
    }

    // Buscar por Correo
    @GET
    @Path("buscar/correo/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> buscarPorCorreo(@PathParam("param") String param) {
        return ClienteImp.buscarPorCorreo(param);
    }
}
