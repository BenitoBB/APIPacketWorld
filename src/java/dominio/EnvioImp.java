
package dominio;

import dto.RSEnvioLista;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author ohana
 */
public class EnvioImp {
    public static List<RSEnvioLista> obtenerEnviosPorConductor(int idColaborador){
    List<RSEnvioLista> lista = null;
    SqlSession conexionBD = MyBatisUtil.getSession();

    if (conexionBD != null) {
        try {
            lista = conexionBD.selectList("envio.obtener-envios-por-conductor",idColaborador);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return lista;
}

    
    
    
    
}
