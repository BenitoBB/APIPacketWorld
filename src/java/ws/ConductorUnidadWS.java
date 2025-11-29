package ws;

import dominio.ConductorUnidadImp;
import dto.RSConductorUnidad;
import dto.Respuesta;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.ConductorUnidad;

/**
 *
 * @authors Ohana & Benito
 */
@Path("conductor-unidad")
public class ConductorUnidadWS {

    // Asignar Unidad a Conductor
    @POST
    @Path("asignar/{idColaborador}/{idUnidad}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta asignarUnidad(
            @PathParam("idColaborador") Integer idColaborador,
            @PathParam("idUnidad") Integer idUnidad
    ) {
        if (idColaborador != null && idUnidad != null) {
            ConductorUnidad cu = new ConductorUnidad();
            cu.setIdColaborador(idColaborador);
            cu.setIdUnidad(idUnidad);

            return ConductorUnidadImp.asignarUnidad(cu);
        }
        throw new BadRequestException("Faltan parámetros idColaborador o idUnidad.");
    }

    // Desasignar Unidad de Conductor
    @PUT
    @Path("desasignar/{idConductorUnidad}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta desasignarUnidad(@PathParam("idConductorUnidad") Integer idConductorUnidad) {
        if (idConductorUnidad != null) {
            return ConductorUnidadImp.desasignarUnidad(idConductorUnidad);
        }
        throw new BadRequestException("Falta idConductorUnidad.");
    }

    // Obtener Undiad actual asignada a Conductor
    @GET
    @Path("actual/conductor/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON)
    public RSConductorUnidad obtenerUnidadActual(@PathParam("idColaborador") Integer idColaborador) {
        RSConductorUnidad respuesta = new RSConductorUnidad();
        ConductorUnidad cu = ConductorUnidadImp.obtenerUnidadActualPorConductor(idColaborador);

        if (cu == null) {
            respuesta.setError(true);
            respuesta.setMensaje("El conductor no tiene unidad asignada actualmente.");
        } else {
            respuesta.setError(false);
            respuesta.setMensaje("Unidad encontrada.");
            respuesta.setAsignacion(cu);
        }

        return respuesta;
    }

    // Obtener Conductor actual asignado a Unidad
    @GET
    @Path("actual/unidad/{idUnidad}")
    @Produces(MediaType.APPLICATION_JSON)
    public RSConductorUnidad obtenerConductorActual(@PathParam("idUnidad") Integer idUnidad) {
        RSConductorUnidad respuesta = new RSConductorUnidad();
        ConductorUnidad cu = ConductorUnidadImp.obtenerConductorActualPorUnidad(idUnidad);

        if (cu == null) {
            respuesta.setError(true);
            respuesta.setMensaje("La unidad no tiene conductor asignado actualmente.");
        } else {
            respuesta.setError(false);
            respuesta.setMensaje("Conductor encontrado.");
            respuesta.setAsignacion(cu);
        }

        return respuesta;
    }

    // Historial por Conductor
    @GET
    @Path("historial/conductor/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConductorUnidad> historialConductor(@PathParam("idColaborador") Integer idColaborador) {
        return ConductorUnidadImp.historialPorConductor(idColaborador);
    }

    // Historial por Unidad
    @GET
    @Path("historial/unidad/{idUnidad}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConductorUnidad> historialUnidad(@PathParam("idUnidad") Integer idUnidad) {
        return ConductorUnidadImp.historialPorUnidad(idUnidad);
    }

    // Validar si Conductor esta disponible
    @GET
    @Path("validar/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta validarConductorLibre(@PathParam("idColaborador") Integer idColaborador) {
        Respuesta respuesta = new Respuesta();
        boolean libre = ConductorUnidadImp.conductorLibre(idColaborador);

        respuesta.setError(false);
        respuesta.setMensaje(libre ? "Conductor libre" : "Conductor ocupado");

        return respuesta;
    }
}
