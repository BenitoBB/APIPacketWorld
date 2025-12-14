package dominio;

import dto.RSUnidad;
import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Unidad;
import utilidades.Mensajes;

/**
 *
 * @authors Ohana & Benito
 */
public class UnidadImp {

    // Obtener todas las unidades
    public static List<Unidad> obtenerTodas() {
        List<Unidad> lista = null; 
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("unidad.obtener-todas");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return lista; 
    }

    // Insertar Unidad
    public static RSUnidad insertarUnidad(Unidad unidad) {
        RSUnidad respuesta = new RSUnidad();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.insert("unidad.insertar-unidad", unidad);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.UNIDAD_REGISTRADA);
                    respuesta.setUnidad(unidad);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.UNIDAD_NO_REGISTRADA);
                }
            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.UNIDAD_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Actualizar Unidad (excepto VIN)
    public static RSUnidad actualizarUnidad(Unidad unidad) {
        RSUnidad respuesta = new RSUnidad();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.update("unidad.actualizar-unidad", unidad);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.UNIDAD_ACTUALIZADA);
                    respuesta.setUnidad(unidad);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.UNIDAD_NO_ACTUALIZADA);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.UNIDAD_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Dar de baja Unidad
    public static Respuesta darDeBaja(Integer idUnidad, String motivo) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {

                Unidad unidad = new Unidad();
                unidad.setIdUnidad(idUnidad);
                unidad.setMotivoBaja(motivo);

                int filas = conexionBD.update("unidad.dar-baja-unidad", unidad);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.UNIDAD_BAJA);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.UNIDAD_NO_BAJA);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.UNIDAD_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Buscar por VIN
    public static Unidad buscarPorVIN(String vin) {
        Unidad unidad = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                unidad = conexionBD.selectOne("unidad.buscar-por-vin", vin);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return unidad;
    }

    // Buscar por marca
    public static List<Unidad> buscarPorMarca(String marca) {
        List<Unidad> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("unidad.buscar-por-marca", marca);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }

    // Buscar por Número de Identificación Interno NII
    public static Unidad buscarPorNII(String nii) {
        Unidad unidad = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                unidad = conexionBD.selectOne("unidad.buscar-por-nii", nii);
            } finally {
                conexionBD.close();
            }
        }

        return unidad;
    }

}
