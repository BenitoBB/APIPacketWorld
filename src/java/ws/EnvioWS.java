package ws;

import com.google.gson.Gson;
import dominio.EnvioImp;
import dto.RSActualizarEstatus;
import dto.RSEnvio;
import dto.RSEnvioDetalle;
import dto.RSEnvioLista;
import dto.RSEnvioTabla;
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
import pojo.EstatusEnvioHistorial;
import utilidades.Mensajes;

@Path("envio")
public class EnvioWS {

    @GET
    @Path("porConductor/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public List<RSEnvioLista> obtenerEnviosPorConductor(
            @PathParam("idColaborador") Integer idColaborador) {

        if (idColaborador == null || idColaborador <= 0) {
            throw new BadRequestException("ID conductor inválido");
        }

        return EnvioImp.obtenerEnviosPorConductor(idColaborador);
    }

    @GET
    @Path("detalle/{numeroGuia}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public RSEnvioDetalle obtenerDetalleEnvio(
            @PathParam("numeroGuia") String numeroGuia) {

        if (numeroGuia == null || numeroGuia.trim().isEmpty()) {
            throw new BadRequestException("Número de guía inválido");
        }

        return EnvioImp.obtenerDetalleEnvio(numeroGuia);

    }

    @GET
    @Path("paquetes/{idEnvio}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<pojo.Paquete> obtenerPaquetesPorEnvio(
            @PathParam("idEnvio") Integer idEnvio) {

        if (idEnvio == null || idEnvio <= 0) {
            throw new BadRequestException("ID de envío inválido");
        }

        return EnvioImp.obtenerPaquetesPorEnvio(idEnvio);
    }

    @PUT
    @Path("estatus")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta actualizarEstatus(String jsonSolicitud) {
        Gson gson = new Gson();
        try {
            RSActualizarEstatus solicitud = gson.fromJson(jsonSolicitud, RSActualizarEstatus.class);
            if (solicitud == null || solicitud.getNumeroGuia() == null || solicitud.getNuevoIdEstatus() <= 0 || solicitud.getIdColaborador() <= 0) {
                throw new BadRequestException(Mensajes.ENVIO_DATOS_INCOMPLETOS);
            }
            return EnvioImp.actualizarEstatusEnvio(solicitud);
        } catch (BadRequestException bre) {
            throw bre;
        } catch (Exception e) {
            throw new BadRequestException("Error en el formato de la solicitud JSON: " + e.getMessage());
        }
    }

    @GET
    @Path("ultimoComentario/{idEnvio}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public String obtenerUltimoComentario(@PathParam("idEnvio") Integer idEnvio) {
        if (idEnvio == null || idEnvio <= 0) {
            throw new BadRequestException("ID de envío inválido");
        }
        String comentario = EnvioImp.obtenerUltimoComentario(idEnvio);
        return comentario != null ? comentario : "";
    }

    @GET
    @Path("tabla")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public List<RSEnvioTabla> obtenerEnviosTabla() {
        return EnvioImp.obtenerEnviosTabla();
    }

    @PUT
    @Path("actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta actualizarEnvio(String json) {

        Gson gson = new Gson();
        RSEnvio envio = gson.fromJson(json, RSEnvio.class);

        if (envio == null || envio.getNumeroGuia() == null) {
            throw new BadRequestException("Número de guía requerido");
        }

        if (envio.getCosto() != null) {
            throw new BadRequestException("El costo del envío no puede ser modificado");
        }

        return EnvioImp.actualizarEnvio(envio);
    }

    @POST
    @Path("registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarEnvio(String json) {

        Gson gson = new Gson();
        RSEnvio envio = gson.fromJson(json, RSEnvio.class);

        if (envio == null
                || envio.getNumeroGuia() == null
                || envio.getIdClienteRemitente() == null
                || envio.getIdSucursalOrigen() == null
                // DIRECCIÓN COMPLETA
                || envio.getCalle() == null
                || envio.getNumero() == null
                || envio.getColonia() == null
                || envio.getCodigoPostal() == null
                || envio.getCiudad() == null
                || envio.getEstado() == null
                // DESTINATARIO
                || envio.getNombreDestinatario() == null
                || envio.getApellidoPaternoDestinatario() == null
                || envio.getCosto() == null) {
            throw new BadRequestException(Mensajes.ENVIO_DATOS_INCOMPLETOS);
        }

        return EnvioImp.registrarEnvio(envio);
    }

    @PUT
    @Path("asignar/{guia}/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta asignar(
            @PathParam("guia") String guia,
            @PathParam("idColaborador") Integer idColaborador
    ) {
        return EnvioImp.asignarConductor(guia, idColaborador);
    }

    @PUT
    @Path("desasignar/{guia}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta desasignar(@PathParam("guia") String guia) {
        return EnvioImp.desasignarConductor(guia);
    }

    @GET
    @Path("historial/{idEnvio}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public List<EstatusEnvioHistorial> obtenerHistorialEnvio(
            @PathParam("idEnvio") int idEnvio) {

        if (idEnvio <= 0) {
            throw new BadRequestException("ID de envío inválido");
        }

        return EnvioImp.obtenerHistorialEnvio(idEnvio);
    }

}
