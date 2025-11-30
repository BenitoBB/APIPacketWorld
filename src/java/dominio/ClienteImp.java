package dominio;

import dto.RSCliente;
import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;

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
                    respuesta.setMensaje("Cliente registrado correctamente.");
                    respuesta.setCliente(cliente);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo registrar el cliente.");
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje("Error al registrar cliente: " + ex.getMessage());
            } finally {
                conexionBD.close();
            }

        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a la base de datos.");
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
                    respuesta.setMensaje("Cliente actualizado correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No existe el cliente a actualizar.");
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje("Error al actualizar cliente: " + ex.getMessage());
            } finally {
                conexionBD.close();
            }

        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a la base de datos.");
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
                    respuesta.setMensaje("Cliente eliminado correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No existe un cliente con ese ID.");
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje("Error al eliminar cliente: " + ex.getMessage());
            } finally {
                conexionBD.close();
            }

        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a la base de datos.");
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
