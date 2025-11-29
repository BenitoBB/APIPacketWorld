package ws;

import dominio.ColaboradorImp;
import dto.RSColaborador;
import dto.Respuesta;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Colaborador;

@Path("colaborador")
public class ColaboradorWS {

    // Login
    @Path("administracion")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RSColaborador autenticacionColaborador(
            @FormParam("noPersonal") String noPersonal,
            @FormParam("password") String password) {
        if (noPersonal != null && !noPersonal.isEmpty()
                && (password != null && !password.isEmpty())) {
            return ColaboradorImp.autenticarAdministracion(noPersonal, password);
        }
        throw new BadRequestException();
    }

    // Insertar
    @Path("insertar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RSColaborador insertar(Colaborador colaborador) {

        if (colaborador != null) {
            return ColaboradorImp.insertarColaborador(colaborador);
        }

        throw new BadRequestException("Faltan datos del colaborador.");
    }

    // Actualizar
    @Path("actualizar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RSColaborador actualizar(Colaborador colaborador) {

        if (colaborador != null && colaborador.getIdColaborador() != null) {
            return ColaboradorImp.actualizarColaborador(colaborador);
        }

        throw new BadRequestException("Faltan datos para actualizar.");
    }

    // Eliminar
    @Path("eliminar/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminar(@PathParam("id") Integer idColaborador) {

        if (idColaborador != null && idColaborador > 0) {
            return ColaboradorImp.eliminarColaborador(idColaborador);
        }

        throw new BadRequestException();
    }

    // Buscar por Nombre
    @Path("buscar/nombre/{nombre}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> buscarPorNombre(@PathParam("nombre") String nombre) {
        return ColaboradorImp.buscarPorNombre(nombre);
    }

    // Buscar por NoPersonal
    @Path("buscar/noPersonal/{noPersonal}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Colaborador buscarPorNoPersonal(@PathParam("noPersonal") String noPersonal) {
        return ColaboradorImp.buscarPorNoPersonal(noPersonal);
    }

    // Buscar por Rol
    @Path("buscar/rol/{idRol}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> buscarPorRol(@PathParam("idRol") Integer idRol) {
        return ColaboradorImp.buscarPorRol(idRol);
    }

    // Actualizar Perfil (Móvil)
    @Path("perfil/actualizar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RSColaborador actualizarPerfil(Colaborador colaborador) {

        if (colaborador != null && colaborador.getIdColaborador() != null) {
            return ColaboradorImp.actualizarPerfil(colaborador);
        }

        throw new BadRequestException("Datos insuficientes para actualizar perfil.");
    }

    // Asignar Unidad a Conductor
    @Path("asignar-unidad/{idColaborador}/{idUnidad}") 
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta asignarUnidad(
            @PathParam("idColaborador") Integer idColaborador,
            @PathParam("idUnidad") Integer idUnidad) {

        if (idColaborador != null && idUnidad != null) {
            return ColaboradorImp.asignarUnidad(idColaborador, idUnidad);
        }

        throw new BadRequestException("Datos incompletos para asignación.");
    }

    // Desasignar Unidad de Conductor
    @Path("desasignar-unidad/{idColaborador}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta desasignarUnidad(@PathParam("idColaborador") Integer idColaborador) {

        if (idColaborador != null) {
            return ColaboradorImp.desasignarUnidad(idColaborador);
        }

        throw new BadRequestException("Falta idColaborador para desasignar.");
    }

}
