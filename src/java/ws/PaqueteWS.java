package ws;

import dominio.PaqueteImp;
import dto.RSPaquete;
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
import pojo.Paquete;

/**
 *
 * @authors Ohana & Benito
 */
@Path("paquete")
public class PaqueteWS {

    // Obtener todos los paquetes
    @Path("obtener-todos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Paquete> obtenerTodos() {
        return PaqueteImp.obtenerTodos();
    }

    @Path("insertar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RSPaquete insertar(Paquete paquete) {

        if (paquete != null) {
            return PaqueteImp.insertar(paquete);
        }

        throw new BadRequestException("Datos insuficientes para registrar el paquete.");
    }

    // Actualizar paquete
    @Path("actualizar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RSPaquete actualizar(Paquete paquete) {

        if (paquete != null && paquete.getIdPaquete() != null) {
            return PaqueteImp.actualizar(paquete);
        }

        throw new BadRequestException("Datos insuficientes para actualizar el paquete.");
    }

    // Obtener paquetes por envío
    @Path("envio/{idEnvio}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Paquete> obtenerPorEnvio(@PathParam("idEnvio") Integer idEnvio) {
        return PaqueteImp.obtenerPorEnvio(idEnvio);
    }

    // Eliminar paquete
    @Path("eliminar/{idPaquete}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminar(@PathParam("idPaquete") Integer idPaquete) {

        if (idPaquete != null) {
            return PaqueteImp.eliminar(idPaquete);
        }

        throw new BadRequestException("ID de paquete requerido.");
    }

    // Asignar Paquete a un Envío
    @PUT
    @Path("asignar/{idPaquete}/{idEnvio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta asignar(
            @PathParam("idPaquete") Integer idPaquete,
            @PathParam("idEnvio") Integer idEnvio) {

        return PaqueteImp.asignarEnvio(idPaquete, idEnvio);
    }

    @PUT
    @Path("desasignar/{idPaquete}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta desasignar(
            @PathParam("idPaquete") Integer idPaquete) {

        if (idPaquete != null) {
            return PaqueteImp.desasignarEnvio(idPaquete);
        }

        throw new BadRequestException("ID de paquete requerido.");
    }
// Obtener paquetes disponibles (sin envío)

    @Path("obtener-disponibles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Paquete> obtenerDisponibles() {
        return PaqueteImp.obtenerDisponibles();
    }

}
