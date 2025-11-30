package ws;

import dominio.DireccionImp;
import dto.RSDireccion;
import dto.Respuesta;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Direccion;

/**
 *
 * @authors Ohana & Benito
 */
@Path("direccion")
public class DireccionWS {

    // Insertar Direccion
    @POST
    @Path("insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RSDireccion insertar(Direccion direccion) {

        if (direccion == null) {
            throw new BadRequestException("El objeto Dirección es nulo.");
        }

        return DireccionImp.insertar(direccion);
    }

    // Actualizar Direccion
    @PUT
    @Path("actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta actualizar(Direccion direccion) {

        if (direccion == null || direccion.getIdDireccion() == null) {
            throw new BadRequestException("Falta idDireccion o el objeto es nulo.");
        }

        return DireccionImp.actualizar(direccion);
    }

    // Obtener Direccion por ID
    @GET
    @Path("obtener/{idDireccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public RSDireccion obtenerPorId(@PathParam("idDireccion") Integer idDireccion) {

        if (idDireccion == null) {
            throw new BadRequestException("Falta idDireccion.");
        }

        return DireccionImp.obtenerPorId(idDireccion);
    }

    // Buscar Direccion por texto
    @GET
    @Path("buscar/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Direccion> buscar(@PathParam("param") String param) {

        if (param == null || param.trim().isEmpty()) {
            throw new BadRequestException("El parámetro de búsqueda está vacío.");
        }

        return DireccionImp.buscar(param);
    }
}
