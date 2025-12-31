package dominio;

import dto.RSSucursal;
import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Direccion;
import pojo.Sucursal;
import utilidades.Mensajes;

/**
 *
 * @authors Ohana & Benito
 */
public class SucursalImp {

    // Obtener todas
    public static List<Sucursal> obtenerTodas() {
        List<Sucursal> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("sucursal.obtener-todas");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }

    // Insertar
    public static RSSucursal insertar(Sucursal sucursal) {
        RSSucursal respuesta = new RSSucursal();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {

                // VALIDACIÓN DE NEGOCIO
                if (existeCodigo(sucursal.getCodigo())) {
                    respuesta.setError(true);
                    respuesta.setMensaje("El código de la sucursal ya está en uso. Intenta con otro.");
                    return respuesta;
                }
                
                Direccion direccion = sucursal.getDireccion();
                conexionBD.insert("direccion.insertar", direccion);

                sucursal.setIdDireccion(direccion.getIdDireccion());

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
                conexionBD.rollback();
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

        try (SqlSession sesion = MyBatisUtil.getSession()) {

            // Obtener sucursal actual desde BD
            Sucursal sucursalBD = sesion.selectOne(
                    "sucursal.obtener-sucursal-por-id",
                    sucursal.getIdSucursal()
            );

            Direccion dirBD = sucursalBD.getDireccion();
            Direccion dirNueva = sucursal.getDireccion();

            boolean actualizarDireccion = !dirBD.equals(dirNueva);

            if (actualizarDireccion) {
                // Asegurar ID
                dirNueva.setIdDireccion(dirBD.getIdDireccion());
                sesion.update("direccion.actualizar", dirNueva);
            }

            sesion.update("sucursal.actualizar", sucursal);
            sesion.commit();

            respuesta.setError(false);

        } catch (Exception e) {
            e.printStackTrace();
            respuesta.setError(true);
            respuesta.setMensaje("Error en la operación de sucursal");
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

    // Validar Código En Existencia 
    public static boolean existeCodigo(String codigo) {
        SqlSession sesion = MyBatisUtil.getSession();
        try {
            int total = sesion.selectOne("sucursal.existe-codigo", codigo);
            return total > 0;
        } finally {
            sesion.close();
        }
    }

}
