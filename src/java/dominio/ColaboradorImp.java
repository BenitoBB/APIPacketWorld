package dominio;

import dto.RSColaborador;
import java.util.HashMap;
import java.util.LinkedHashMap;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;

/**
 *
 * @authors Ohana & Benito
 */
public class ColaboradorImp {

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
}
