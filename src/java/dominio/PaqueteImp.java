package dominio;

import dto.RSPaquete;
import dto.Respuesta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                    respuesta.setMensaje(Mensajes.PAQUETE_REGISTRADO);
                    respuesta.setPaquete(paquete);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.PAQUETE_NO_REGISTRADO);
                }
            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.PAQUETE_ERROR_REGISTRAR + ex.getMessage());
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
                    respuesta.setMensaje(Mensajes.PAQUETE_ACTUALIZADO);
                    respuesta.setPaquete(paquete);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje(Mensajes.PAQUETE_NO_ACTUALIZADO);
                }
            } catch (Exception ex) {
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.PAQUETE_ERROR_ACTUALIZAR + ex.getMessage());
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
        SqlSession sesion = MyBatisUtil.getSession();

        if (sesion != null) {
            try {
                Integer idEnvio = sesion.selectOne(
                        "paquete.obtener-id-envio-por-paquete",
                        idPaquete
                );

                int filas = sesion.delete("paquete.eliminar-paquete", idPaquete);

                if (filas > 0 && idEnvio != null) {
                    EnvioImp.recalcularCostoEnvio(idEnvio);
                }

                sesion.commit();

                respuesta.setError(false);
                respuesta.setMensaje(Mensajes.PAQUETE_ELIMINADO);

            } catch (Exception e) {
                sesion.rollback();
                respuesta.setError(true);
                respuesta.setMensaje(Mensajes.PAQUETE_ERROR_ELIMINAR + e.getMessage());
            } finally {
                sesion.close();
            }
        }

        return respuesta;
    }

    // Asignar Paquete a un Envío
    public static Respuesta asignarEnvio(Integer idPaquete, Integer idEnvio) {

        Respuesta r = new Respuesta();
        SqlSession sesion = MyBatisUtil.getSession();

        if (sesion != null) {
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("idPaquete", idPaquete);
                params.put("idEnvio", idEnvio);

                // CANTIDAD DE PAQUETES ASIGNADAS AL ENVIO
                Integer totalAntes = sesion.selectOne(
                        "paquete.contar-por-envio",
                        idEnvio
                );

                int filas = sesion.update("paquete.asignar-envio", params);

                if (filas > 0) {
                    EnvioImp.recalcularCostoEnvio(idEnvio);
                    sesion.commit();

                    r.setError(false);

                    if (totalAntes != null && totalAntes == 0) {
                        r.setMensaje(
                                "Primer paquete asignado. El costo del envío se calcula automáticamente con base en la distancia."
                        );
                    } else {
                        r.setMensaje(Mensajes.PAQUETE_ASIGNADO);
                    }
                } else {
                    r.setError(true);
                    r.setMensaje(Mensajes.PAQUETE_N0_ASIGNADO);
                }

            } catch (Exception e) {
                sesion.rollback();
                r.setError(true);
                r.setMensaje(Mensajes.PAQUERE_ERROR_ASIGNAR + e.getMessage());
            } finally {
                sesion.close();
            }
        } else {
            r.setError(true);
            r.setMensaje(Mensajes.SIN_CONEXION);
        }

        return r;
    }

    // Dessignar Paquete a un Envío
    public static Respuesta desasignarEnvio(Integer idPaquete) {

        Respuesta r = new Respuesta();
        SqlSession sesion = MyBatisUtil.getSession();

        if (sesion != null) {
            try {
                Integer idEnvio = sesion.selectOne(
                        "paquete.obtener-id-envio-por-paquete",
                        idPaquete
                );

                int filas = sesion.update(
                        "paquete.desasignar-envio",
                        idPaquete
                );
                if (idEnvio != null) {
                    EnvioImp.recalcularCostoEnvio(idEnvio);
                }

                sesion.commit();

                if (filas > 0) {
                    r.setError(false);
                    r.setMensaje(Mensajes.PAQUETE_DESASIGNADO);
                } else {
                    r.setError(true);
                    r.setMensaje(Mensajes.PAQUETE_NO_DESASIGNADO);
                }

            } catch (Exception e) {
                r.setError(true);
                r.setMensaje(Mensajes.PAQUETE_ERROR_ASIGNAR + e.getMessage());
            } finally {
                sesion.close();
            }
        } else {
            r.setError(true);
            r.setMensaje(Mensajes.SIN_CONEXION);
        }

        return r;
    }

    // Obtener paquetes disponibles (sin envío)
    public static List<Paquete> obtenerDisponibles() {

        List<Paquete> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("paquete.obtener-disponibles");
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }

    // Obtener Paquetes asociados a Numero Guia
    public static List<Paquete> obtenerPorGuia(String guia) {

        List<Paquete> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList(
                        "paquete.obtener-por-guia",
                        guia
                );
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }

}
