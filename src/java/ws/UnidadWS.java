package ws;

import dominio.UnidadImp;
import dto.RSUnidad;
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
import pojo.Unidad;

/**
 *
 * @authors Ohana & Benito
 */
@Path("unidad")
public class UnidadWS {

    // Obtener todas las unidades
    @Path("obtener-todas")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Unidad> obtenerTodas() {
        return UnidadImp.obtenerTodas();
    }

    // Insertar Unidad
    @Path("insertar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RSUnidad insertarUnidad(Unidad unidad) {

        if (unidad != null) {
            return UnidadImp.insertarUnidad(unidad);
        }

        throw new BadRequestException("Faltan datos de unidad.");
    }

    // Actualizar Unidad
    @Path("actualizar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RSUnidad actualizarUnidad(Unidad unidad) {

        if (unidad != null && unidad.getIdUnidad() != null) {
            return UnidadImp.actualizarUnidad(unidad);
        }

        throw new BadRequestException("Datos insuficientes para actualizar la unidad.");
    }

    // Dar de baja Unidad
    @Path("baja/{idUnidad}/{motivo}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta darDeBaja(
            @PathParam("idUnidad") Integer idUnidad,
            @PathParam("motivo") String motivo) {

        if (idUnidad != null && motivo != null && !motivo.isEmpty()) {
            return UnidadImp.darDeBaja(idUnidad, motivo);
        }

        throw new BadRequestException("Datos insuficientes para dar de baja la unidad.");
    }

    // Buscar por VIN 
    @Path("buscar/vin/{vin}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Unidad buscarPorVIN(@PathParam("vin") String vin) {
        return UnidadImp.buscarPorVIN(vin);
    }

    // Buscar por Marca
    @Path("buscar/marca/{marca}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Unidad> buscarPorMarca(@PathParam("marca") String marca) {
        return UnidadImp.buscarPorMarca(marca);
    }

    // Buscar por Número Interno NII
    @Path("buscar/nii/{nii}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Unidad buscarPorNII(@PathParam("nii") String nii) {
        return UnidadImp.buscarPorNII(nii);
    }

    @Path("puede-baja/{idUnidad}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta puedeDarseDeBaja(@PathParam("idUnidad") Integer idUnidad) {
        Respuesta r = new Respuesta();

        if (UnidadImp.puedeDarseDeBaja(idUnidad)) {
            r.setError(false);
        } else {
            r.setError(true);
            r.setMensaje("La unidad tiene un conductor asignado.");
        }
        return r;
    }

}
