package dominio;

import dto.RSDireccion;
import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Direccion;
import utilidades.Mensajes;

/**
 *
 * @authors Ohana & Benito
 */
public class DireccionImp {

    // Insertar Direccion
    public static RSDireccion insertar(Direccion direccion) {
        RSDireccion respuesta = new RSDireccion();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.insert("direccion.insertar", direccion);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.DIRECCION_REGISTRADA);
                    respuesta.setDireccion(direccion); // contiene idDireccion generado
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.DIRECCION_NO_REGISTRADA);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje("Error: " + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a la base de datos.");
        }

        return respuesta;
    }

    // Actualizar Direccion
    public static Respuesta actualizar(Direccion direccion) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.update("direccion.actualizar", direccion);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.DIRECCION_ACTUALIZADA);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.DIRECCION_NO_ACTUALIZADA);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.DIRECCION_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Obtener Direccion por ID
    public static RSDireccion obtenerPorId(Integer idDireccion) {
        RSDireccion respuesta = new RSDireccion();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                Direccion dir = conexionBD.selectOne("direccion.obtener-por-id", idDireccion);

                if (dir != null) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.DIRECCION_ENCONTRADA);
                    respuesta.setDireccion(dir);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.DIRECCION_NO_ENCONTRADA);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.DIRECCION_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Buscar Direccion por palabra clave
    public static List<Direccion> buscar(String param) {
        List<Direccion> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("direccion.buscar", param);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }
}
