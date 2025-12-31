package ws;

import dominio.CostoEnvioImp;
import dto.RSCostoEnvio;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("costo-envio")
public class CostoEnvioWS {

    @GET
    @Path("{cpOrigen}/{cpDestino}/{paquetes}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public RSCostoEnvio calcularCosto(
            @PathParam("cpOrigen") String cpOrigen,
            @PathParam("cpDestino") String cpDestino,
            @PathParam("paquetes") Integer paquetes) {

        if (cpOrigen == null || cpDestino == null
                || paquetes == null || paquetes <= 0) {
            throw new BadRequestException("Datos inválidos para calcular costo");
        }

        return CostoEnvioImp.calcularCosto(cpOrigen, cpDestino, paquetes);
    }
}
