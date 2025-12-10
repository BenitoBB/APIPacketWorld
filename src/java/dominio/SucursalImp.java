package dominio;

import dto.RSSucursal;
import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Sucursal;
import utilidades.Mensajes;

/**
 *
 * @authors Ohana & Benito
 */
public class SucursalImp {

    // Insertar
    public static RSSucursal insertar(Sucursal sucursal) {
        RSSucursal respuesta = new RSSucursal();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.insert("sucursal.insertar-sucursal", sucursal);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.SUCURSAL_REGISTRADA);
                    respuesta.setSucursal(sucursal);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.SUCURSAL_NO_REGISTRADA);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.SUCURSAL_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }

        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Actualizar (excepto codigo y estatus)
    public static Respuesta actualizar(Sucursal sucursal) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.update("sucursal.actualizar-sucursal", sucursal);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.SUCURSAL_ACTUALIZADA);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.SUCURSAL_NO_ACTUALIZADA);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.SUCURSAL_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Dar de baja (estatus = Inactiva)
    public static Respuesta darDeBaja(Integer idSucursal) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {

                int filas = conexionBD.update("sucursal.dar-de-baja-sucursal", idSucursal);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.SUCURSAL_BAJA);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.SUCURSAL_NO_BAJA);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.SUCURSAL_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Obtener por ID
    public static RSSucursal obtenerPorId(Integer idSucursal) {
        RSSucursal respuesta = new RSSucursal();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                Sucursal suc = conexionBD.selectOne("sucursal.obtener-sucursal-por-id", idSucursal);

                if (suc != null) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.SUCURSAL_ENCONTRADA);
                    respuesta.setSucursal(suc);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.SUCURSAL_NO_ENCONTRADA);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.SUCURSAL_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Buscar (por Nombre o Codigo)
    public static List<Sucursal> buscar(String param) {
        List<Sucursal> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("sucursal.buscar-sucursal", param);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }
}
