package ws;

import dominio.ColaboradorImp;
import dto.ColaboradorTablaDTO;
import dto.RSCambioPassword;
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

    // Obtener todos
    @Path("obtener-todos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ColaboradorTablaDTO> obtenerColaboradoresTabla() {
        return ColaboradorImp.obtenerColaboradoresTabla();
    }

    // Login
    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
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
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public RSColaborador actualizarPerfil(Colaborador colaborador) {

        if (colaborador != null && colaborador.getIdColaborador() != null) {
            return ColaboradorImp.actualizarPerfil(colaborador);
        }

        throw new BadRequestException("Datos insuficientes para actualizar perfil.");
    }

    @Path("perfil/obtener/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public RSColaborador obtenerDatosPerfil(@PathParam("id") Integer idColaborador) {

        if (idColaborador != null && idColaborador > 0) {
            // Llama al método que creamos en ColaboradorImp
            return ColaboradorImp.buscarPerfil(idColaborador);
        }

        throw new BadRequestException("ID de colaborador no válido.");
    }

    @Path("password/cambiar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Respuesta cambiarPassword(RSCambioPassword datosCambio) {

        // 1. Validaciones de null/vacío para los campos críticos
        if (datosCambio == null
                || datosCambio.getIdColaborador() == null
                || datosCambio.getIdColaborador() <= 0
                || datosCambio.getPasswordActual() == null || datosCambio.getPasswordActual().isEmpty()
                || datosCambio.getPasswordNueva() == null || datosCambio.getPasswordNueva().isEmpty()) {
            throw new BadRequestException("Datos insuficientes para cambiar la contraseña (ID, actual y nueva son obligatorios).");
        }

        // 2. Llamada a la lógica de dominio
        return ColaboradorImp.cambiarPassword(datosCambio);
    }

    @Path("obtenerPassword/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta ObtenerPassword(@PathParam("id") Integer idColaborador) {
        if (idColaborador == null || idColaborador <= 0) {
            throw new BadRequestException("ID no válido.");
        }
        return ColaboradorImp.ObtenerPassword(idColaborador);
    }

    @PUT
    @Path("subir-foto/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta subirFoto(@PathParam("idColaborador") Integer idColaborador, byte[] foto) {
        if (idColaborador != null && idColaborador > 0 && foto != null && foto.length > 0) {
            return ColaboradorImp.guardarFoto(idColaborador, foto);
        }
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        respuesta.setMensaje("Datos inválidos");
        return respuesta;
    }

    @GET
    @Path("obtener-foto/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Colaborador obtenerFoto(@PathParam("idColaborador") Integer idColaborador) {
        if (idColaborador != null && idColaborador > 0) {
            return ColaboradorImp.obtenerFoto(idColaborador);
        }
        return null;
    }

    @PUT
    @Path("escritorio/subir-foto/{idColaborador}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta subirFotoEscritorio(
            @PathParam("idColaborador") Integer idColaborador,
            byte[] foto) {

        if (idColaborador != null && idColaborador > 0
                && foto != null && foto.length > 0) {
            return ColaboradorImp.guardarFotoEscritorio(idColaborador, foto);
        }

        Respuesta r = new Respuesta();
        r.setError(true);
        r.setMensaje("Datos inválidos");
        return r;
    }

    @GET
    @Path("escritorio/obtener-foto/{idColaborador}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] obtenerFotoEscritorio(
            @PathParam("idColaborador") Integer idColaborador) {
        return ColaboradorImp.obtenerFotoEscritorio(idColaborador);
    }

}
