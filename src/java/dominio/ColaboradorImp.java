package dominio;

import dto.ColaboradorTablaDTO;
import dto.RSCambioPassword;
import dto.RSColaborador;
import dto.Respuesta;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import utilidades.Mensajes;

/**
 *
 * @authors Ohana & Benito
 */
public class ColaboradorImp {

    // Obtener todos
    public static List<ColaboradorTablaDTO> obtenerColaboradoresTabla() {
        List<ColaboradorTablaDTO> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("colaborador.obtener-todos");
            } finally {
                conexionBD.close();
            }
        }
        return lista;
    }

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
                    respuesta.setMensaje(Mensajes.COLABORADOR_LOGUEADO + colaborador.getNombre());
                    respuesta.setColaborador(colaborador);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.COLABORADOR_ERROR_CREDENCIALES);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.COLABORADOR_ERROR + ex.getMessage());
            } finally {
                conexionBD.close(); // Cerramos la sesión siempre
            }

        } else {
            // Sin conexión
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    // Insertar 
    public static RSColaborador insertarColaborador(Colaborador colaborador) {
        RSColaborador respuesta = new RSColaborador();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                // VALIDACIÓN DE NEGOCIO
                if (existeNoPersonal(colaborador.getNoPersonal())) {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.COLABORADOR_DUP_NO_PERSONAL);
                    return respuesta;
                }

                if (existeCampo("curp", colaborador.getCurp())) {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.COLABORADOR_DUP_CURP);
                    return respuesta;
                }

                if (existeCampo("correo", colaborador.getCorreo())) {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.COLABORADOR_DUP_CORREO);
                    return respuesta;
                }

                if (colaborador.getNumeroLicencia() != null
                        && existeCampo("numeroLicencia", colaborador.getNumeroLicencia())) {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.COLABORADOR_DUP_NUM_LICENCIA);
                    return respuesta;
                }

                int filas = conexionBD.insert("colaborador.insertar-colaborador", colaborador);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.COLABORADOR_REGISTRADO);
                    respuesta.setColaborador(colaborador);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.COLABORADOR_NO_REGISTRADO);
                }
            } catch (Exception ex) {

                String msg = ex.getMessage().toLowerCase();

                respuesta.setError(true);

                if (msg.contains("nopersonal")) {
                    respuesta.setMensaje(Mensajes.COLABORADOR_DUP_NO_PERSONAL);
                } else if (msg.contains("curp")) {
                    respuesta.setMensaje(Mensajes.COLABORADOR_DUP_CURP);
                } else if (msg.contains("correo")) {
                    respuesta.setMensaje(Mensajes.COLABORADOR_DUP_CORREO);
                } else if (msg.contains("numeroLicencia")) {
                    respuesta.setMensaje(Mensajes.COLABORADOR_DUP_NUM_LICENCIA);
                } else {
                    respuesta.setMensaje(Mensajes.COLABORADOR_ERROR + ex.getMessage());
                }
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
    public static RSColaborador actualizarColaborador(Colaborador colaborador) {
        RSColaborador respuesta = new RSColaborador();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // VALIDACIONES UNIQUE (UPDATE)
                if (existeCampoExcluyendoId(
                        "curp",
                        colaborador.getCurp(),
                        colaborador.getIdColaborador()
                )) {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.COLABORADOR_DUP_CURP);
                    return respuesta;
                }

                if (existeCampoExcluyendoId(
                        "correo",
                        colaborador.getCorreo(),
                        colaborador.getIdColaborador()
                )) {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.COLABORADOR_DUP_CORREO);
                    return respuesta;
                }
                if (colaborador.getNumeroLicencia() != null
                        && existeCampoExcluyendoId(
                                "numeroLicencia",
                                colaborador.getNumeroLicencia(),
                                colaborador.getIdColaborador()
                        )) {

                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.COLABORADOR_DUP_NUM_LICENCIA);
                    return respuesta;
                }

                int filas = conexionBD.update("colaborador.actualizar-colaborador", colaborador);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.COLABORADOR_ACTUALIZADO);
                    respuesta.setColaborador(colaborador);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.COLABORADOR_NO_ACTUALIZADO);
                }
            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.COLABORADOR_ERROR + ex.getMessage());
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
    public static Respuesta eliminarColaborador(int idColaborador) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filas = conexionBD.delete("colaborador.eliminar-colaborador", idColaborador);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje(Mensajes.COLABORADOR_ELIMINADO);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.COLABORADOR_NO_ELIMINADO);
                }
            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.COLABORADOR_ERROR + ex.getMessage());
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
                    respuesta.setMensaje(Mensajes.COLABORADOR_PERFIL_ACTUALIZADO);
                    respuesta.setColaborador(colaborador);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.COLABORADOR_PERFIL_NO_ACTUALIZADO);
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.COLABORADOR_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }

        return respuesta;
    }

    public static RSColaborador buscarPerfil(int idColaborador) {
        RSColaborador respuesta = new RSColaborador();
        SqlSession conexionBD = MyBatisUtil.getSession();
        Colaborador colaboradorEncontrado = null;

        if (conexionBD != null) {
            try {
                colaboradorEncontrado = conexionBD.selectOne("colaborador.buscar-datos-perfil", idColaborador);

                if (colaboradorEncontrado != null) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Datos de perfil obtenidos correctamente.");
                    respuesta.setColaborador(colaboradorEncontrado);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("Error: Colaborador no encontrado.");
                }

            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje("Error interno del servidor al buscar el perfil: " + e.getMessage());
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error: No se pudo establecer la conexión con la base de datos.");
        }
        return respuesta;
    }

    public static Respuesta cambiarPassword(RSCambioPassword datosCambio) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // 1. Obtener la contraseña ACTUAL de la DB 
                // Usamos pojo.Colaborador como resultType para obtener solo el campo password
                Colaborador colaboradorDB = conexionBD.selectOne(
                        "colaborador.obtener-password-actual",
                        datosCambio.getIdColaborador()
                );

                if (colaboradorDB == null || colaboradorDB.getPassword() == null) {
                    respuesta.setError(true);
                    respuesta.setMensaje("Error: Usuario no encontrado o sin contraseña registrada.");
                    return respuesta;
                }

                String passwordActualDB = colaboradorDB.getPassword();

                // 2. VALIDACIÓN DE LA CONTRASEÑA ACTUAL (Texto plano)
                if (!passwordActualDB.equals(datosCambio.getPasswordActual())) {
                    respuesta.setError(true);
                    respuesta.setMensaje("Contraseña actual incorrecta. Verifique sus datos.");
                    return respuesta;
                }

                // 3. Llamar al mapper para actualizar 
                // Pasamos el DTO directamente. El mapper usa datosCambio.getIdColaborador() y datosCambio.getPasswordNueva()
                int filasAfectadas = conexionBD.update("colaborador.cambiar-password", datosCambio);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Contraseña actualizada con éxito.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo completar la operación de cambio.");
                }

            } catch (Exception ex) {
                conexionBD.rollback();
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.COLABORADOR_ERROR + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.SIN_CONEXION);
        }
        return respuesta;
    }

// ⭐⭐ TEMPORAL: Método para probar el mapper de obtención de contraseña ⭐⭐
    public static Respuesta ObtenerPassword(int idColaborador) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                Colaborador colaboradorDB = conexionBD.selectOne(
                        "colaborador.obtener-password-actual",
                        idColaborador
                );

                if (colaboradorDB != null && colaboradorDB.getPassword() != null) {
                    // NO HAGAS ESTO EN PRODUCCIÓN: Exponer la contraseña es un riesgo.
                    respuesta.setError(false);
                    respuesta.setMensaje("Contraseña obtenida: " + colaboradorDB.getPassword());
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("Colaborador no encontrado o sin contraseña.");
                }

            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje("Error de DB: " + ex.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a DB.");
        }
        return respuesta;
    }

    public static Respuesta guardarFoto(int idColaborador, byte[] foto) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                Colaborador colaborador = new Colaborador();
                colaborador.setIdColaborador(idColaborador);
                colaborador.setFoto(foto);

                int filasAfectadas = conexionBD.update("colaborador.guardar-foto", colaborador);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("La foto del colaborador se guardó correctamente");
                } else {
                    respuesta.setMensaje("No se pudo guardar la foto, intente nuevamente");
                }

            } catch (Exception e) {
                respuesta.setMensaje(e.getMessage());
            } finally {
                conexionBD.close();
            }
        }
        return respuesta;
    }

    public static Colaborador obtenerFoto(int idColaborador) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        Colaborador colaborador = null;

        if (conexionBD != null) {
            try {
                colaborador = conexionBD.selectOne("colaborador.obtener-foto", idColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return colaborador;
    }

    // GUARDAR FOTO ESCRITORIO
    public static Respuesta guardarFotoEscritorio(int idColaborador, byte[] foto) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                Colaborador c = new Colaborador();
                c.setIdColaborador(idColaborador);
                c.setFoto(foto);

                int filas = conexionBD.update(
                        "colaborador.guardar-foto-escritorio", c
                );
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Foto guardada correctamente (FX)");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo guardar la foto");
                }
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            } finally {
                conexionBD.close();
            }
        }
        return respuesta;
    }

    // OBTENER FOTO ESCRITORIO
    public static byte[] obtenerFotoEscritorio(int idColaborador) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        byte[] foto = null;

        if (conexionBD != null) {
            try {
                Colaborador c = conexionBD.selectOne(
                        "colaborador.obtener-foto-escritorio",
                        idColaborador
                );
                if (c != null) {
                    foto = c.getFoto();
                }
            } finally {
                conexionBD.close();
            }
        }
        return foto;
    }

    // Validar NoPersonal Existente
    public static boolean existeNoPersonal(String noPersonal) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        boolean existe = false;

        if (conexionBD != null) {
            try {
                Integer conteo = conexionBD.selectOne(
                        "colaborador.existe-noPersonal",
                        noPersonal
                );
                existe = conteo != null && conteo > 0;
            } finally {
                conexionBD.close();
            }
        }
        return existe;
    }

    // Validar si un campo UNIQUE ya existe (curp, correo, etc.)
    public static boolean existeCampo(String campo, String valor) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        boolean existe = false;

        if (conexionBD != null) {
            try {
                HashMap<String, Object> parametros = new HashMap<>();
                parametros.put("campo", campo);
                parametros.put("valor", valor);

                Integer conteo = conexionBD.selectOne(
                        "colaborador.existe-campo",
                        parametros
                );

                existe = conteo != null && conteo > 0;

            } finally {
                conexionBD.close();
            }
        }
        return existe;
    }

    // Validar UNIQUE en actualización (excluyendo el mismo id)
    public static boolean existeCampoExcluyendoId(
            String campo,
            String valor,
            int idColaborador
    ) {
        SqlSession conexionBD = MyBatisUtil.getSession();
        boolean existe = false;

        if (conexionBD != null) {
            try {
                HashMap<String, Object> parametros = new HashMap<>();
                parametros.put("campo", campo);
                parametros.put("valor", valor);
                parametros.put("idColaborador", idColaborador);

                Integer conteo = conexionBD.selectOne(
                        "colaborador.existe-campo-excluyendo-id",
                        parametros
                );

                existe = conteo != null && conteo > 0;

            } finally {
                conexionBD.close();
            }
        }
        return existe;
    }

}
