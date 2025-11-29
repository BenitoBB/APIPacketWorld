package dominio;

import dto.RSColaborador;
import dto.Respuesta;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;

/**
 *
 * @authors Ohana & Benito
 */
public class ColaboradorImp {

    // Login
    public static RSColaborador autenticarAdministracion(String noPersonal, String password) {
        RSColaborador respuesta = new RSColaborador();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // Creamos los parámetros para el mapper
                HashMap<String, String> parametros = new LinkedHashMap<>();
                parametros.put("noPersonal", noPersonal);
                parametros.put("password", password);

                // Llamada al mapper usando la variable colaborador
                Colaborador colaborador = conexionBD.selectOne("colaborador.login-colaborador", parametros);

                if (colaborador != null) {
                    // Si se encontró el colaborador, asignamos los datos a la respuesta
                    respuesta.setError(false);
                    respuesta.setMensaje("Autenticación correcta" + " " + colaborador.getNombre());
                    respuesta.setColaborador(colaborador);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("Usuario o contraseña incorrectos.");
                }

            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            } finally {
                conexionBD.close(); // Cerramos la sesión siempre
            }

        } else {
            // Sin conexión
            respuesta.setError(true);
            respuesta.setMensaje("Lo sentimos, por el momento no hay conexión a los datos...");
        }

        return respuesta;
    }

    // Insertar 
    public static RSColaborador insertarColaborador(Colaborador colaborador) {
        RSColaborador respuesta = new RSColaborador();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                int filas = conexionBD.insert("colaborador.insertar-colaborador", colaborador);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Colaborador registrado correctamente.");
                    respuesta.setColaborador(colaborador);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo registrar el Colaborador.");
                }
            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexion.");
        }
        return respuesta;
    }

    // Actualizar 
    public static RSColaborador actualizarColaborador(Colaborador colaborador) {
        RSColaborador respuesta = new RSColaborador();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.update("colaborador.actualizar-colaborador", colaborador);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Colaborador actualizado.");
                    respuesta.setColaborador(colaborador);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se encontró el Colaborador.");
                }
            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexion");
        }
        return respuesta;
    }

    // Eliminar
    public static Respuesta eliminarColaborador(int idColaborador) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.delete("colaborador.eliminar-colaborador", idColaborador);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Colaborador eliminado correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se encontro el Colaborador.");
                }
            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin Conexion.");
        }
        return respuesta;
    }

    // Buscar por Nombre 
    public static List<Colaborador> buscarPorNombre(String nombre) {
        List<Colaborador> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("colaborador.buscar-por-nombre", nombre);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return lista;
    }

    // Buscar por NoPersonal
    public static Colaborador buscarPorNoPersonal(String noPersonal) {
        Colaborador col = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                col = conexionBD.selectOne("colaborador.buscar-por-noPersonal", noPersonal);
            } finally {
                conexionBD.close();
            }
        }

        return col;
    }

    // Buscar por Rol
    public static List<Colaborador> buscarPorRol(int idRol) {
        List<Colaborador> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("colaborador.buscar-por-rol", idRol);
            } finally {
                conexionBD.close();
            }
        }
        return lista;
    }

    // Actualizar Perfil (Móvil)
    public static RSColaborador actualizarPerfil(Colaborador colaborador) {
        RSColaborador respuesta = new RSColaborador();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {

            try {
                int filas = conexionBD.update("colaborador.actualizar-perfil", colaborador);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Perfil actualizado.");
                    respuesta.setColaborador(colaborador);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se encontró el colaborador.");
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión.");
        }

        return respuesta;
    }
}
