package dominio;

import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.DashboardConteo;
import pojo.DashboardEnvio;
import pojo.DashboardResumen;

/**
 *
 * @authors Ohana & Benito
 */
public class DashboardImp {

    public static DashboardResumen obtenerResumen() {
        DashboardResumen resumen = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                resumen = conexionBD.selectOne("dashboard.obtener-resumen");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return resumen;
    }

    public static List<DashboardConteo> obtenerEnviosPorEstatus() {
        List<DashboardConteo> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("dashboard.envios-por-estatus");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return lista;
    }

    public static List<DashboardConteo> obtenerEnviosPorSucursal() {
        List<DashboardConteo> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("dashboard.envios-por-sucursal");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return lista;
    }

    public static List<DashboardEnvio> obtenerUltimosEnvios() {
        List<DashboardEnvio> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("dashboard.ultimos-envios");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return lista;
    }
}
