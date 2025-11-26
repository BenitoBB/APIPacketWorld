package dominio;

import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.Estatus;
import pojo.Rol;
import pojo.Sucursal;
import pojo.Unidad;

/**
 *
 * @authors Ohana & Benito
 */
public class CatalogoImp {

    // Roles
    public static List<Rol> obtenerRolesSistema() {
        List<Rol> roles = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                roles = conexionBD.selectList("catalogo.obtener-roles");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return roles;
    }

    // Estatus
    public static List<Estatus> obtenerEstatusEnvio() {
        List<Estatus> lista = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                lista = conexionBD.selectList("catalogo.obtener-estatus");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return lista;
    }
    
    // Sucursales
    public static List<Sucursal> obtenerSucursalesActivas() {
        List<Sucursal> sucursales = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                sucursales = conexionBD.selectList("catalogo.obtener-sucursales");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return sucursales;
    }
    
    // Unidades
    public static List<Unidad> obtenerUnidadesActivas() {
        List<Unidad> unidades = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                unidades = conexionBD.selectList("catalogo.obtener-unidades-activas");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return unidades;
    }
    
    // Conductores
    public static List<Colaborador> obtenerConductores() {
        List<Colaborador> conductores = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                conductores = conexionBD.selectList("catalogo.obtener-conductores");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return conductores;
    }
}
