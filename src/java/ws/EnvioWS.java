package ws;

import dominio.EnvioImp;
import dto.RSEnvioLista;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("envio") 
public class EnvioWS {
    @GET
    @Path("porConductor/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RSEnvioLista> obtenerEnviosPorConductor(
            @PathParam("idColaborador") Integer idColaborador) {

        if (idColaborador == null || idColaborador <= 0) {
            throw new BadRequestException("ID conductor inválido");
        }

        return EnvioImp.obtenerEnviosPorConductor(idColaborador);
    }
}
