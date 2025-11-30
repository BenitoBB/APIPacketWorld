package dominio;

import dto.RSDireccion;
import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Direccion;

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
                    respuesta.setMensaje("Dirección registrada correctamente.");
                    respuesta.setDireccion(direccion); // contiene idDireccion generado
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo registrar la dirección.");
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
                    respuesta.setMensaje("Dirección actualizada correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se encontró la dirección a actualizar.");
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

    // Obtener Direccion por ID
    public static RSDireccion obtenerPorId(Integer idDireccion) {
        RSDireccion respuesta = new RSDireccion();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                Direccion dir = conexionBD.selectOne("direccion.obtener-por-id", idDireccion);

                if (dir != null) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Dirección encontrada.");
                    respuesta.setDireccion(dir);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No existe una dirección con ese ID.");
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
