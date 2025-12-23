package dominio;

import dto.RSPaquete;
import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Paquete;
import utilidades.Mensajes;

/**
 *
 * @authors Ohana & Benito
 */
public class PaqueteImp {

    // Obtener todos los paquetes
    public static List<Paquete> obtenerTodos() {

        List<Paquete> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("paquete.obtener-todos");
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }

    // Registrar paquete
    public static RSPaquete insertar(Paquete paquete) {

        RSPaquete respuesta = new RSPaquete();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.insert("paquete.insertar-paquete", paquete);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Paquete registrado correctamente.");
                    respuesta.setPaquete(paquete);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo registrar el paquete.");
                }
            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje("Error al registrar paquete: " + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Actualizar paquete
    public static RSPaquete actualizar(Paquete paquete) {

        RSPaquete respuesta = new RSPaquete();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.update("paquete.actualizar-paquete", paquete);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Paquete actualizado correctamente.");
                    respuesta.setPaquete(paquete);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo actualizar el paquete.");
                }
            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje("Error al actualizar paquete: " + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Obtener paquetes por envío
    public static List<Paquete> obtenerPorEnvio(Integer idEnvio) {

        List<Paquete> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("paquete.obtener-por-envio", idEnvio);
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }

    // Eliminar paquete
    public static Respuesta eliminar(Integer idPaquete) {

        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.delete("paquete.eliminar-paquete", idPaquete);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Paquete eliminado correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo eliminar el paquete.");
                }
            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje("Error al eliminar paquete: " + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }
}
