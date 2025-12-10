package dominio;

import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.ConductorUnidad;
import utilidades.Mensajes;

/**
 *
 * @authors Ohana & Benito
 */
public class ConductorUnidadImp {

    // Asignar Unidad a Conductor
    public static Respuesta asignarUnidad(ConductorUnidad cu) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {

                // VALIDAR que el conductor NO tenga asignación activa
                int ocupadas = conexionBD.selectOne(
                        "conductorUnidad.validar-conductor-libre",
                        cu.getIdColaborador()
                );

                if (ocupadas > 0) {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.ASIGNACION_EXISTE);
                    return respuesta;
                }

                // Registrar nueva asignación
                int filas = conexionBD.insert("conductorUnidad.asignar-unidad", cu);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.ASIGNACION_REALIZADA);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.ASIGNACION_NO_REALIZADA);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.ASIGNACION_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Desasignar Unidad (cerrar asignacion activa)
    public static Respuesta desasignarUnidad(Integer idConductorUnidad) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {

                int filas = conexionBD.update("conductorUnidad.desasignar-unidad", idConductorUnidad);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.DESASIGNACION_REALIZADA);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.DESASIGNACION_NO_REALIZADA);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.ASIGNACION_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Obtener Unidad actual asignada a un Conductor 
    public static ConductorUnidad obtenerUnidadActualPorConductor(Integer idColaborador) {
        ConductorUnidad cu = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                cu = conexionBD.selectOne(
                        "conductorUnidad.unidad-actual-de-conductor",
                        idColaborador
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return cu;
    }

    // Obtener Conductor asignado a una Unidad
    public static ConductorUnidad obtenerConductorActualPorUnidad(Integer idUnidad) {
        ConductorUnidad cu = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                cu = conexionBD.selectOne(
                        "conductorUnidad.conductor-actual-de-unidad",
                        idUnidad
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return cu;
    }

    // Historial completo por Conductor
    public static List<ConductorUnidad> historialPorConductor(Integer idColaborador) {
        List<ConductorUnidad> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList(
                        "conductorUnidad.historial-por-conductor",
                        idColaborador
                );
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }

    // Historial completo por Unidad
    public static List<ConductorUnidad> historialPorUnidad(Integer idUnidad) {
        List<ConductorUnidad> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList(
                        "conductorUnidad.historial-por-unidad",
                        idUnidad
                );
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }

    // Validación si el Conductor esta libre (sin asignar)
    public static boolean conductorLibre(Integer idColaborador) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                int contador = conexionBD.selectOne(
                        "conductorUnidad.validar-conductor-libre",
                        idColaborador
                );
                return contador == 0;
            } finally {
                conexionBD.close();
            }
        }
        return false;
    }

}
