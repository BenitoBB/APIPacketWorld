package ws;

import dominio.DashboardImp;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.DashboardConteo;
import pojo.DashboardEnvio;
import pojo.DashboardResumen;

/**
 *
 * @authors Ohana & Benito
 */
@Path("dashboard")
public class DashboardWS {

    @Path("resumen")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DashboardResumen obtenerResumen() {
        return DashboardImp.obtenerResumen();
    }

    @Path("envios-estatus")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DashboardConteo> obtenerEnviosPorEstatus() {
        return DashboardImp.obtenerEnviosPorEstatus();
    }

    @Path("envios-sucursal")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DashboardConteo> obtenerEnviosPorSucursal() {
        return DashboardImp.obtenerEnviosPorSucursal();
    }

    @Path("ultimos-envios")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DashboardEnvio> obtenerUltimosEnvios() {
        return DashboardImp.obtenerUltimosEnvios();
    }
}
