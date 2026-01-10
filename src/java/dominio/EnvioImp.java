package dominio;

import dto.RSActualizarEstatus;
import dto.RSEnvio;
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
import utilidades.Validaciones;

/**
 *
 * @author Ohana & Benito
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
                            Mensajes.ENVIO_ERROR_VALIDACION + Mensajes.ENVIO_COMENTARIO_OBLIGATORIO);
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
                respuesta.setMensaje(Mensajes.DIRECCION_NO_REGISTRADA);
                return respuesta;
            }

            // Registrar envío con costo inicial 0
            envio.setCosto(BigDecimal.ZERO);
            conexionBD.insert("envio.registrar-envio", envio);
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
            r.setMensaje(Mensajes.CONDUCTOR_ERROR_ASIGNACION);
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
                r.setMensaje(Mensajes.CONDUCTOR_DESASIGNADO);
            } else {
                r.setMensaje(Mensajes.CONDUCTOR_NO_DESASIGNADO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            conexion.rollback();
            r.setMensaje(Mensajes.CONDUCTOR_ERROR_DESASIGNACION);
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

    public static boolean clienteTieneEnviosActivos(int idCliente) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        boolean tiene = false;

        if (conexionBD != null) {
            try {
                Integer count = conexionBD.selectOne(
                        "envio.contar-envios-activos-por-cliente",
                        idCliente
                );
                tiene = count != null && count > 0;
            } finally {
                conexionBD.close();
            }
        }
        return tiene;
    }

    public static Respuesta recalcularCostoEnvio(int idEnvio) {
        Respuesta r = new Respuesta();
        SqlSession session = MyBatisUtil.getSession();

        try {
            // 1. Obtener CPs
            HashMap<String, String> cps
                    = session.selectOne("envio.obtener-cps", idEnvio);

            // 2. Contar paquetes
            Integer paquetes
                    = session.selectOne("paquete.contar-por-envio", idEnvio);

            if (paquetes == null || paquetes == 0) {
                session.update("envio.actualizar-costo",
                        new HashMap<String, Object>() {
                    {
                        put("idEnvio", idEnvio);
                        put("costo", 0.0);
                    }
                });
                session.commit();
                r.setError(false);
                return r;
            }

            // 3. Distancia
            double km = DistanciaImp.obtenerDistancia(
                    cps.get("cpOrigen"),
                    cps.get("cpDestino")
            );

            // 4. Cálculo
            double costo = Validaciones.calcularCostoEnvio(km, paquetes);

            // 5. Actualizar envío
            HashMap<String, Object> params = new HashMap<>();
            params.put("idEnvio", idEnvio);
            params.put("costo", costo);

            session.update("envio.actualizar-costo", params);
            session.commit();

            r.setError(false);
        } catch (Exception e) {
            r.setError(true);
            r.setMensaje("Error al recalcular costo: " + e.getMessage());
        } finally {
            session.close();
        }

        return r;
    }

    public static Integer obtenerIdEnvioPorGuia(String numeroGuia) {
        try (SqlSession s = MyBatisUtil.getSession()) {
            return s.selectOne("envio.obtener-idEnvio-por-guia", numeroGuia);
        }
    }

    public static boolean sucursalTieneEnviosActivos(int idSucursal) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        boolean tiene = false;

        if (conexionBD != null) {
            try {
                Integer total = conexionBD.selectOne(
                        "envio.contar-envios-activos-por-sucursal",
                        idSucursal
                );
                tiene = total != null && total > 0;
            } finally {
                conexionBD.close();
            }
        }
        return tiene;
    }

}
