package dominio;

import dto.RSCliente;
import dto.Respuesta;
import java.util.HashMap;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;
import pojo.Direccion;
import utilidades.Mensajes;

/**
 *
 * @authors Ohana & Benito
 */
public class ClienteImp {

    // Obtener todos
    public static List<Cliente> obtenerTodos() {
        List<Cliente> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("cliente.obtener-todos");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }

    // Insertar
    public static RSCliente insertar(Cliente cliente) {
        RSCliente respuesta = new RSCliente();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // VALIDACIÓN UNIQUE CORREO
                if (existeCorreo(cliente.getCorreo())) {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.CLIENTE_CORREO_DUPLICADO);
                    return respuesta;
                }
                // VALIDACIÓN UNIQUE TELEFONO
                if (existeTelefono(cliente.getTelefono())) {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.CLIENTE_TELEFONO_DUPLICADO);
                    return respuesta;
                }
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
                // VALIDACIÓN UNIQUE CORREO (UPDATE)
                if (existeCorreoExcluyendoId(
                        cliente.getCorreo(),
                        cliente.getIdCliente()
                )) {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.CLIENTE_CORREO_DUPLICADO);
                    return respuesta;
                }
                // VALIDACIÓN UNIQUE TELEFONO (UPDATE)
                if (existeTelefonoExcluyendoId(
                        cliente.getTelefono(),
                        cliente.getIdCliente()
                )) {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.CLIENTE_TELEFONO_DUPLICADO);
                    return respuesta;
                }

                // OBTENER DIRECCIÓN ACTUAL
                Direccion direccionActual = conexionBD.selectOne(
                        "direccion.obtener-por-id",
                        cliente.getDireccion().getIdDireccion()
                );

                // VALIDAR CAMBIOS EN DIRECCIÓN
                boolean cambiosDireccion = huboCambiosDireccion(
                        cliente.getDireccion(),
                        direccionActual
                );
                // ACTUALIZAR DIRECCIÓN SOLO SI CAMBIÓ
                if (cambiosDireccion) {
                    int filasDireccion = conexionBD.update("direccion.actualizar", cliente.getDireccion());

                    if (filasDireccion == 0) {
                        conexionBD.rollback();
                        respuesta.setError(true);
                        respuesta.setMensaje(Mensajes.DIRECCION_NO_ACTUALIZADA);
                        return respuesta;
                    }
                }
                // ASIGNAR FK
                cliente.setIdDireccion(cliente.getDireccion().getIdDireccion());

                // ACTUALIZAR CLIENTE
                int filasCliente = conexionBD.update("cliente.actualizar-cliente", cliente);
                if (filasCliente > 0) {
                    conexionBD.commit(); 
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

    public static boolean existeCorreo(String correo) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        boolean existe = false;

        if (conexionBD != null) {
            try {
                Integer count = conexionBD.selectOne(
                        "cliente.existe-correo",
                        correo
                );
                existe = count != null && count > 0;
            } finally {
                conexionBD.close();
            }
        }

        return existe;
    }

    public static boolean existeCorreoExcluyendoId(String correo, int idCliente) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        boolean existe = false;

        if (conexionBD != null) {
            try {
                HashMap<String, Object> params = new HashMap<>();
                params.put("correo", correo);
                params.put("idCliente", idCliente);

                Integer count = conexionBD.selectOne(
                        "cliente.existe-correo-excluyendo-id",
                        params
                );

                existe = count != null && count > 0;
            } finally {
                conexionBD.close();
            }
        }

        return existe;
    }

    public static boolean existeTelefono(String telefono) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        boolean existe = false;

        if (conexionBD != null) {
            try {
                Integer count = conexionBD.selectOne(
                        "cliente.existe-telefono",
                        telefono
                );
                existe = count != null && count > 0;
            } finally {
                conexionBD.close();
            }
        }

        return existe;
    }

    public static boolean existeTelefonoExcluyendoId(String telefono, int idCliente) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        boolean existe = false;

        if (conexionBD != null) {
            try {
                HashMap<String, Object> params = new HashMap<>();
                params.put("telefono", telefono);
                params.put("idCliente", idCliente);

                Integer count = conexionBD.selectOne(
                        "cliente.existe-telefono-excluyendo-id",
                        params
                );

                existe = count != null && count > 0;
            } finally {
                conexionBD.close();
            }
        }

        return existe;
    }

    private static boolean huboCambiosDireccion(
            Direccion nueva,
            Direccion actual
    ) {
        if (nueva == null || actual == null) {
            return false;
        }

        return !String.valueOf(nueva.getCalle())
                .equals(String.valueOf(actual.getCalle()))
                || !String.valueOf(nueva.getNumero())
                        .equals(String.valueOf(actual.getNumero()))
                || !String.valueOf(nueva.getCodigoPostal())
                        .equals(String.valueOf(actual.getCodigoPostal()))
                || !String.valueOf(nueva.getColonia())
                        .equals(String.valueOf(actual.getColonia()));
    }

}
