package dominio;

import dto.RSActualizarEstatus;
import dto.RSEnvio;
import dto.RSCostoEnvio;
import dto.RSEnvioDetalle;
import dto.RSEnvioLista;
import dto.RSEnvioTabla;
import dto.Respuesta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.EstatusEnvioHistorial;
import pojo.Paquete;
import utilidades.Mensajes;
import java.math.BigDecimal;

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
        recalcularCostoEnvio(numeroGuia);
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

    public static String obtenerUltimoComentario(int idEnvio) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        try {
            return conexionBD.selectOne("envio.obtener-ultimo-comentario", idEnvio);
        } finally {
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
    }

    // METODOS NECESARIOS PARA CLIENTE FX
    public static List<RSEnvioTabla> obtenerEnviosTabla() {
        List<RSEnvioTabla> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("envio.obtener-envios-tabla");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return lista;
    }

    public static Respuesta registrarEnvio(RSEnvio envio) {

        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);

        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD == null) {
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
            return respuesta;
        }

        try {
            // Insertar dirección
            conexionBD.insert("envio.insertar-direccion", envio);

            if (envio.getIdDireccion() == null) {
                conexionBD.rollback();
                respuesta.setMensaje("No se pudo registrar la dirección");
                return respuesta;
            }

            // Registrar envío con costo inicial 0
            envio.setCosto(BigDecimal.ZERO);
            conexionBD.insert("envio.registrar-envio", envio);
            recalcularCostoEnvio(envio.getNumeroGuia());
            conexionBD.commit();
            respuesta.setError(false);
            respuesta.setMensaje(Mensajes.ENVIO_REGISTRADO);

        } catch (Exception e) {
            e.printStackTrace();
            conexionBD.rollback();
            respuesta.setMensaje(Mensajes.ERROR_DESCONOCIDO);
        } finally {
            conexionBD.close();
        }

        return respuesta;
    }

    public static Respuesta actualizarEnvio(RSEnvio envio) {

        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);

        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD == null) {
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
            return respuesta;
        }

        try {
            int filasEnvio = conexionBD.update("envio.actualizar-envio", envio);
            int filasDireccion = 0;
            if (envio.getCalle() != null) {
                filasDireccion = conexionBD.update(
                        "envio.actualizar-direccion-envio", envio
                );
            }
            if (filasEnvio > 0 || filasDireccion > 0) {
                conexionBD.commit();
                respuesta.setError(false);
                respuesta.setMensaje(Mensajes.ENVIO_ACTUALIZADO);
            } else {
                conexionBD.rollback();
                respuesta.setMensaje(Mensajes.ENVIO_NO_ACTUALIZADO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            conexionBD.rollback();
            respuesta.setMensaje(Mensajes.ERROR_DESCONOCIDO);
        } finally {
            conexionBD.close();
        }

        return respuesta;
    }

    public static Respuesta asignarConductor(String numeroGuia, Integer idColaborador) {
        Respuesta r = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();

        try {

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numeroGuia", numeroGuia);
            parametros.put("idColaborador", idColaborador);

            int filas = conexion.update("envio.asignar-conductor", parametros);

            conexion.commit();

            r.setError(filas <= 0);
            r.setMensaje(filas > 0
                    ? "Conductor asignado correctamente"
                    : "No se pudo asignar el conductor");
        } catch (Exception e) {
            conexion.rollback();
            r.setError(true);
            r.setMensaje("Error al asignar conductor");
        } finally {
            conexion.close();
        }
        return r;
    }

    public static Respuesta desasignarConductor(String numeroGuia) {

        Respuesta r = new Respuesta();
        r.setError(true);

        SqlSession conexion = MyBatisUtil.getSession();

        if (conexion == null) {
            r.setMensaje(Mensajes.SIN_CONEXION);
            return r;
        }

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("numeroGuia", numeroGuia);

            int filas = conexion.update("envio.desasignar-conductor", params);
            conexion.commit();

            if (filas > 0) {
                r.setError(false);
                r.setMensaje("Conductor desasignado correctamente");
            } else {
                r.setMensaje("No se pudo desasignar el conductor");
            }

        } catch (Exception e) {
            e.printStackTrace();
            conexion.rollback();
            r.setMensaje("Error al desasignar conductor");
        } finally {
            conexion.close();
        }

        return r;
    }

    public static List<EstatusEnvioHistorial> obtenerHistorialEnvio(int idEnvio) {
        List<EstatusEnvioHistorial> historial = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                historial = conexionBD.selectList(
                        "envio.obtener-historial-envio",
                        idEnvio
                );
            } finally {
                conexionBD.close();
            }
        }
        return historial;
    }

    public static Respuesta recalcularCostoEnvio(String numeroGuia) {

        Respuesta r = new Respuesta();
        r.setError(true);

        SqlSession conexion = MyBatisUtil.getSession();
        if (conexion == null) {
            r.setMensaje(Mensajes.SIN_CONEXION);
            return r;
        }

        try {

            Integer idEnvio = conexion.selectOne(
                    "envio.obtener-idEnvio-por-guia",
                    numeroGuia
            );

            if (idEnvio == null) {
                r.setMensaje("Envío no encontrado");

                return r;
            }

            String cpOrigen = conexion.selectOne(
                    "envio.obtener-cp-sucursal-origen",
                    numeroGuia
            );

            String cpDestino = conexion.selectOne(
                    "envio.obtener-cp-destino",
                    numeroGuia
            );

            if (cpOrigen == null || cpDestino == null) {
                r.setMensaje("No se pudieron obtener los códigos postales");
                return r;
            }

            Integer paquetes = conexion.selectOne(
                    "envio.contar-paquetes-por-envio",
                    numeroGuia
            );

            if (paquetes == null || paquetes <= 0) {
                paquetes = 1;
            }

            RSCostoEnvio costo = CostoEnvioImp.calcularCosto(
                    cpOrigen,
                    cpDestino,
                    paquetes
            );

            if (costo.isError()) {
                r.setMensaje(costo.getMensaje());

                return r;
            }

            Map<String, Object> params = new HashMap<>();
            params.put("idEnvio", idEnvio);
            params.put("costo", costo.getCostoTotal());

            int filas = conexion.update(
                    "envio.actualizar-costo-envio",
                    params
            );

            if (filas <= 0) {
                conexion.rollback();
                r.setMensaje("No se actualizó el costo");
                return r;
            }

            conexion.commit();

            r.setError(false);
            r.setMensaje("Costo recalculado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            conexion.rollback();
            r.setMensaje(Mensajes.ERROR_DESCONOCIDO + e.getMessage());
        } finally {
            conexion.close();

        }

        return r;
    }
    public static boolean clienteTieneEnvios(int idCliente) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        boolean tiene = false;

        if (conexionBD != null) {
            try {
                Integer count = conexionBD.selectOne(
                        "envio.contar-envios-por-cliente",
                        idCliente
                );
                tiene = count != null && count > 0;
            } finally {
                conexionBD.close();
            }
        }
        return tiene;
    }

}
