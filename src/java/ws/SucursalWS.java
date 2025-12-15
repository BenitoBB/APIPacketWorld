package ws;

import dominio.SucursalImp;
import dto.RSSucursal;
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
import pojo.Sucursal;

/**
 *
 * @authors Ohana & Benito
 */
@Path("sucursal")
public class SucursalWS {

    // Obtener todas
    @GET
    @Path("obtener-todas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> obtenerTodas() {
        return SucursalImp.obtenerTodas();
    }

    // Insertar
    @POST
    @Path("insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RSSucursal insertarSucursal(Sucursal sucursal) {

        if (sucursal == null) {
            throw new BadRequestException("Cuerpo JSON vacío.");
        }

        return SucursalImp.insertar(sucursal);
    }

    // Actualizar (NO modificar código ni estatus)
    @PUT
    @Path("actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta actualizarSucursal(Sucursal sucursal) {

        if (sucursal == null || sucursal.getIdSucursal() == null) {
            throw new BadRequestException("Falta idSucursal en el JSON.");
        }

        return SucursalImp.actualizar(sucursal);
    }

    // Dar de Baja (estatus = Inactiva)
    @PUT
    @Path("baja/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta darDeBaja(@PathParam("idSucursal") Integer idSucursal) {

        if (idSucursal == null) {
            throw new BadRequestException("Falta idSucursal.");
        }

        return SucursalImp.darDeBaja(idSucursal);
    }

    // Obtener por ID
    @GET
    @Path("obtener/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public RSSucursal obtenerPorId(@PathParam("idSucursal") Integer idSucursal) {

        if (idSucursal == null) {
            throw new BadRequestException("Falta idSucursal.");
        }

        return SucursalImp.obtenerPorId(idSucursal);
    }

    // Buscar por Nombre o Codigo
    @GET
    @Path("buscar/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> buscar(@PathParam("param") String param) {

        return SucursalImp.buscar(param);
    }

}
