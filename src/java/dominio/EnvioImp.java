package dominio;

import dto.RSActualizarEstatus;
import dto.RSEnvioDetalle;
import dto.RSEnvioLista;
import dto.Respuesta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.EstatusEnvioHistorial;
import pojo.Paquete;
import utilidades.Mensajes;

/**
 *
 * @author ohana & benito
 */
public class EnvioImp {

    private static final int ID_DETENIDO = 4;
    private static final int ID_CANCELADO = 6;

    public static List<RSEnvioLista> obtenerEnviosPorConductor(int idColaborador) {
        List<RSEnvioLista> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("envio.obtener-envios-por-conductor", idColaborador);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return lista;
    }

    public static RSEnvioDetalle obtenerDetalleEnvio(String numeroGuia) {
        RSEnvioDetalle detalle = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // Obtener el Detalle del Envío (sin paquetes)
                detalle = conexionBD.selectOne(
                        "envio.detalle-envio", numeroGuia
                );
                // Si el detalle existe, obtener sus paquetes
                if (detalle != null) {
                    List<Paquete> paquetes = conexionBD.selectList(
                            "envio.obtener-paquetes-por-envio", detalle.getIdEnvio() // Usamos el ID del envío
                    );
                    detalle.setPaquetes(paquetes);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return detalle;
    }

    public static List<pojo.Paquete> obtenerPaquetesPorEnvio(int idEnvio) {
        List<pojo.Paquete> listaPaquetes = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                listaPaquetes = conexionBD.selectList(
                        "envio.obtener-paquetes-por-envio", idEnvio
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return listaPaquetes;
    }

    public static Respuesta actualizarEstatusEnvio(RSActualizarEstatus solicitud) {

        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);

        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD == null) {
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
            return respuesta;
        }

        try {
            // OBTENER ID DEL ENVÍO
            Integer idEnvio = conexionBD.selectOne("envio.obtener-idEnvio-por-guia", solicitud.getNumeroGuia());

            if (idEnvio == null) {
                respuesta.setMensaje(Mensajes.ENVIO_NO_ENCONTRADO);
                return respuesta;
            }

            // VALIDACIÓN DE COMENTARIO (Regla de negocio)
            Integer nuevoEstatus = solicitud.getNuevoIdEstatus();

            if (nuevoEstatus == ID_DETENIDO || nuevoEstatus == ID_CANCELADO) {
                String comentario = solicitud.getComentario();
                if (comentario == null || comentario.trim().isEmpty()) {
                    respuesta.setMensaje(
                            Mensajes.ENVIO_ERROR_VALIDACION + "El comentario (motivo) es obligatorio para el estatus Detenido o Cancelado.");
                    return respuesta;
                }
            }
            // ACTUALIZAR TABLA ENVIO
            int filasAfectadasEnvio = conexionBD.update("envio.actualizar-estatus", solicitud);

            if (filasAfectadasEnvio > 0) {
                // REGISTRAR EN EL HISTORIAL
                EstatusEnvioHistorial historial = new EstatusEnvioHistorial();

                // Asignamos los datos al POJO
                historial.setIdEnvio(idEnvio);
                // Asignamos el valor del Request (nuevoEstatus) a la propiedad del POJO (idEstatus)
                historial.setIdEstatus(nuevoEstatus);
                historial.setComentario(solicitud.getComentario());
                historial.setIdColaborador(solicitud.getIdColaborador());

                // Usamos el POJO para la inserción
                conexionBD.insert("envio.registrar-historial-estatus", historial);
                // COMMIT DE LA TRANSACCIÓN (ambas operaciones)
                conexionBD.commit();

                // Respuesta de Éxito
                respuesta.setError(false);
                respuesta.setMensaje(Mensajes.ENVIO_ESTATUS_ACTUALIZADO);
            } else {
                // Si la actualización no afectó filas (ej. la guía ya está entregada y no se permite cambiar)
                conexionBD.rollback();
                respuesta.setMensaje(Mensajes.ENVIO_ESTATUS_NO_ACTUALIZADO);
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (conexionBD != null) {
                conexionBD.rollback();
            }
            respuesta.setMensaje(Mensajes.ERROR_DESCONOCIDO + e.getMessage());
        } finally {
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return respuesta;
    }
}
