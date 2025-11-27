package ws;

import dominio.CatalogoImp;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Colaborador;
import pojo.Estatus;
import pojo.Rol;
import pojo.Sucursal;
import pojo.Unidad;

/**
 *
 * @authors Ohana & Benito
 */
@Path("catalogo")
public class CatalogoWS {
    // Roles
    @Path("roles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rol> obtenerRolesSistema(){
        return CatalogoImp.obtenerRolesSistema();
    }
    
    // Estatus
    @Path("estatus")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estatus> obtenerEstatusEnvio(){
        return CatalogoImp.obtenerEstatusEnvio();
    }
    
    // Sucursales activas
    @Path("sucursales")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> obtenerSucursalesActivas(){
        return CatalogoImp.obtenerSucursalesActivas();
    }
    
    // Unidades activas
    @Path("unidades")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Unidad> obtenerUnidadesActivas(){
        return CatalogoImp.obtenerUnidadesActivas();
    }
    
    // Conductores
    @Path("conductores")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> obtenerConductores(){
        return CatalogoImp.obtenerConductores();
    }
}
