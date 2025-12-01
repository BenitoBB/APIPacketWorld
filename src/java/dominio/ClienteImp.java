package dominio;

import dto.RSCliente;
import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;
import utilidades.Mensajes;

/**
 *
 * @authors Ohana & Benito
 */
public class ClienteImp {

    // Insertar
    public static RSCliente insertar(Cliente cliente) {
        RSCliente respuesta = new RSCliente();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.insert("cliente.insertar-cliente", cliente);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.CLIENTE_REGISTRADO);
                    respuesta.setCliente(cliente);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.CLIENTE_NO_REGISTRADO);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.CLIENTE_ERROR_REGISTRAR + ex.getMessage());
            } finally {
                conexionBD.close();
            }

        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Actualizar
    public static Respuesta actualizar(Cliente cliente) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {

                int filas = conexionBD.update("cliente.actualizar-cliente", cliente);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.CLIENTE_ACTUALIZADO);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.CLIENTE_NO_ACTUALIZADO);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.CLIENTE_ERROR_ACTUALIZAR + ex.getMessage());
            } finally {
                conexionBD.close();
            }

        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Eliminar 
    public static Respuesta eliminar(Integer idCliente) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {

            try {
                int filas = conexionBD.delete("cliente.eliminar-cliente", idCliente);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.CLIENTE_ELIMINADO);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.CLIENTE_NO_ELIMINADO);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.CLIENTE_ERROR_ELIMINAR + ex.getMessage());
            } finally {
                conexionBD.close();
            }

        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Buscar por Nombre
    public static List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> lista = null;
        SqlSession conexion = MyBatisUtil.getSession();

        if (conexion != null) {
            try {
                lista = conexion.selectList("cliente.buscar-por-nombre", nombre);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexion.close();
            }
        }

        return lista;
    }

    // Buscar por Telefono
    public static List<Cliente> buscarPorTelefono(String telefono) {
        List<Cliente> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("cliente.buscar-por-telefono", telefono);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }

    // Buscar por Correo
    public static List<Cliente> buscarPorCorreo(String correo) {
        List<Cliente> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("cliente.buscar-por-correo", correo);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }
}
