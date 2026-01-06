package dominio;

import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.ConductorUnidad;
import pojo.Unidad;
import utilidades.Mensajes;

/**
 *
 * @authors Ohana & Benito
 */
public class ConductorUnidadImp {

    // Asignar Unidad a Conductor
    public static Respuesta asignarUnidad(ConductorUnidad cu) {

        Respuesta respuesta = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();

        if (conexion != null) {
            try {
                conexion.update(
                        "conductorUnidad.desasignar-por-conductor",
                        cu.getIdColaborador()
                );

                conexion.update(
                        "conductorUnidad.desasignar-por-unidad",
                        cu.getIdUnidad()
                );

                int filas = conexion.insert(
                        "conductorUnidad.asignar",
                        cu
                );

                conexion.commit();

                respuesta.setError(filas <= 0);
                respuesta.setMensaje(
                        filas > 0
                                ? Mensajes.ASIGNACION_REALIZADA
                                : Mensajes.ASIGNACION_NO_REALIZADA
                );

            } catch (Exception e) {
                conexion.rollback();
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.ASIGNACION_ERROR + e.getMessage());
            } finally {
                conexion.close();
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
                        "conductorUnidad.unidad-actual-conductor",
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
                        "conductorUnidad.conductor-actual-unidad",
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

    // Unidades disponibles
    public static List<Unidad> obtenerUnidadesActivas() {
        SqlSession conexion = MyBatisUtil.getSession();
        try {
            return conexion.selectList("conductorUnidad.unidades-activas");
        } finally {
            conexion.close();
        }
    }

    // Conductores disponibles
    public static List<Colaborador> obtenerConductores() {
        SqlSession conexion = MyBatisUtil.getSession();
        try {
            return conexion.selectList("conductorUnidad.conductores");
        } finally {
            conexion.close();
        }
    }

    // Desasignar por conductor
    public static Respuesta desasignarPorConductor(Integer idColaborador) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();

        if (conexion != null) {
            try {
                int filas = conexion.update(
                        "conductorUnidad.desasignar-por-conductor",
                        idColaborador
                );
                conexion.commit();

                respuesta.setError(filas <= 0);
                respuesta.setMensaje(
                        filas > 0
                                ? Mensajes.DESASIGNACION_REALIZADA
                                : Mensajes.DESASIGNACION_NO_REALIZADA
                );

            } catch (Exception e) {
                conexion.rollback();
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.DESASIGNACION_ERROR);
            } finally {
                conexion.close();
            }
        }
        return respuesta;
    }

    // Desasignar por unidad
    public static Respuesta desasignarPorUnidad(Integer idUnidad) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();

        if (conexion != null) {
            try {
                int filas = conexion.update(
                        "conductorUnidad.desasignar-por-unidad",
                        idUnidad
                );
                conexion.commit();

                respuesta.setError(filas <= 0);
                respuesta.setMensaje(
                        filas > 0
                                ? Mensajes.DESASIGNACION_REALIZADA
                                : Mensajes.DESASIGNACION_NO_REALIZADA
                );

            } catch (Exception e) {
                conexion.rollback();
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.DESASIGNACION_ERROR);
            } finally {
                conexion.close();
            }
        }
        return respuesta;
    }

    public static boolean unidadTieneConductorActivo(int idUnidad) {

        SqlSession conexion = MyBatisUtil.getSession();

        if (conexion != null) {
            try {
                Integer total = conexion.selectOne(
                        "conductorUnidad.existe-conductor-activo",
                        idUnidad
                );
                return total != null && total > 0;
            } finally {
                conexion.close();
            }
        }

        return false;
    }

}
