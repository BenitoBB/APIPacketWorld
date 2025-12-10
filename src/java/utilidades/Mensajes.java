package utilidades;

/**
 *
 * @authors Ohana & Benito
 */
public class Mensajes {

    // GENÉRICOS
    public static final String SIN_CONEXION = "Sin conexión a la base de datos.";
    public static final String ERROR_DESCONOCIDO = "Ocurrió un error inesperado: ";

    // CLIENTE 
    public static final String CLIENTE_REGISTRADO = "Cliente registrado correctamente.";
    public static final String CLIENTE_NO_REGISTRADO = "No se pudo registrar el cliente.";
    public static final String CLIENTE_ACTUALIZADO = "Cliente actualizado correctamente.";
    public static final String CLIENTE_NO_ACTUALIZADO = "No se pudo actualizar el cliente.";
    public static final String CLIENTE_ELIMINADO = "Cliente eliminado correctamente.";
    public static final String CLIENTE_NO_ELIMINADO = "No existe un cliente con ese ID.";
    public static final String CLIENTE_ERROR_REGISTRAR = "Error al registrar cliente: ";
    public static final String CLIENTE_ERROR_ACTUALIZAR = "Error al actualizar cliente: ";
    public static final String CLIENTE_ERROR_ELIMINAR = "Error al eliminar cliente: ";

    // COLABORADOR
    public static final String COLABORADOR_LOGUEADO = "Autenticación correcta. Bienvenid@ ";
    public static final String COLABORADOR_REGISTRADO = "Colaborador registrado correctamente.";
    public static final String COLABORADOR_NO_REGISTRADO = "No se pudo registrar el colaborador.";
    public static final String COLABORADOR_ACTUALIZADO = "Colaborador actualizado correctamente.";
    public static final String COLABORADOR_NO_ACTUALIZADO = "No se pudo actualizar el colaborador.";
    public static final String COLABORADOR_ELIMINADO = "Colaborador eliminado correctamente.";
    public static final String COLABORADOR_NO_ELIMINADO = "No existe un colaborador con ese ID.";
    public static final String COLABORADOR_ERROR = "Error en operación de colaborador: ";
    public static final String COLABORADOR_ERROR_CREDENCIALES = "Usuario o contraseña incorrectos.";
    public static final String COLABORADOR_PERFIL_ACTUALIZADO = "Perfil actualizado.";
    public static final String COLABORADOR_PERFIL_NO_ACTUALIZADO = "No se pudo actualizar el perfi.";

    // DIRECCION 
    public static final String DIRECCION_REGISTRADA = "Dirección registrada correctamente.";
    public static final String DIRECCION_NO_REGISTRADA = "No se pudo registrar la dirección.";
    public static final String DIRECCION_ACTUALIZADA = "Dirección actualizada correctamente.";
    public static final String DIRECCION_NO_ACTUALIZADA = "No se pudo actualizar la dirección.";
    public static final String DIRECCION_ELIMINADA = "Dirección eliminada correctamente.";
    public static final String DIRECCION_ENCONTRADA = "Dirección encontrada.";
    public static final String DIRECCION_NO_ENCONTRADA = "No existe la dirección solicitada.";
    public static final String DIRECCION_ERROR = "Error en operación de dirección: ";

    // SUCURSAL 
    public static final String SUCURSAL_REGISTRADA = "Sucursal registrada correctamente.";
    public static final String SUCURSAL_NO_REGISTRADA = "No se pudo registrar la sucursal.";
    public static final String SUCURSAL_ACTUALIZADA = "Sucursal actualizada correctamente.";
    public static final String SUCURSAL_NO_ACTUALIZADA = "No se pudo actualizar la sucursal.";
    public static final String SUCURSAL_BAJA = "Sucursal dada de baja correctamente.";
    public static final String SUCURSAL_NO_BAJA = "No se pudo dar de baja la sucursal.";
    public static final String SUCURSAL_ENCONTRADA = "Sucursal encontrada.";
    public static final String SUCURSAL_NO_ENCONTRADA = "No existe la sucursal solicitada.";
    public static final String SUCURSAL_ERROR = "Error en operación de sucursal: ";

    // UNIDAD 
    public static final String UNIDAD_REGISTRADA = "Unidad registrada correctamente.";
    public static final String UNIDAD_NO_REGISTRADA = "No se pudo registrar la unidad.";
    public static final String UNIDAD_ACTUALIZADA = "Unidad actualizada correctamente.";
    public static final String UNIDAD_NO_ACTUALIZADA = "No se pudo actualizar la unidad.";
    public static final String UNIDAD_BAJA = "Unidad dada de baja correctamente.";
    public static final String UNIDAD_NO_BAJA = "No se pudo dar de baja la unidad.";
    public static final String UNIDAD_NO_EXISTE = "No existe la unidad solicitada.";
    public static final String UNIDAD_ERROR = "Error en operación de unidad: ";

    // CONDUCTOR-UNIDAD 
    public static final String ASIGNACION_REALIZADA = "Unidad asignada correctamente al conductor.";
    public static final String ASIGNACION_NO_REALIZADA = "No se pudo asignar la unidad.";
    public static final String DESASIGNACION_REALIZADA = "Unidad desasignada correctamente.";
    public static final String DESASIGNACION_NO_REALIZADA = "No se encontró asignación activa.";
    public static final String ASIGNACION_ERROR = "Error en asignación/desasignación de unidad: ";
    public static final String ASIGNACION_EXISTE = "El conductor ya tiene una unidad asignada.";
    public static final String ASIGNACION_NO_EXISTE = "No se encontró la asignación.";
    
    // ENVÍO
public static final String ENVIO_NO_ENCONTRADO = "El envío no existe con ese número de guía.";
public static final String ENVIO_ESTATUS_ACTUALIZADO = "Estatus del envío actualizado y registrado correctamente.";
public static final String ENVIO_ESTATUS_NO_ACTUALIZADO = "No se pudo actualizar el estatus del envío.";
public static final String ENVIO_ERROR_VALIDACION = "Error de validación: ";
public static final String ENVIO_DATOS_INCOMPLETOS = "Datos incompletos (Guía, Estatus o Colaborador).";
}
