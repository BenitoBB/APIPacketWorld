package modelo.mybatis;

import java.io.IOException;
import java.io.Reader; // para leer el archivo de configuracion "mybatis-confing.xml"
import org.apache.ibatis.io.Resources; // clase de MyBatis para cargar recursos (archivos)
import org.apache.ibatis.session.SqlSession; // la conexion activa de MyBatis, abre hacia la BD 
import org.apache.ibatis.session.SqlSessionFactory; // fabrica que genera sesiones
import org.apache.ibatis.session.SqlSessionFactoryBuilder; // objeto para construir la fabrica a partir de la configuracion XML

/**
 *
 * @authors Ohana & Benito
 */
public class MyBatisUtil {

    private static final String RESOURCE = "modelo/mybatis/mybatis-config.xml";
    private static final String ENVIROMENT = "desarrollo";

    public static SqlSession getSession() {
        SqlSession session = null;
        try {
            Reader reader = Resources.getResourceAsReader(RESOURCE);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader, ENVIROMENT);
            session = sqlMapper.openSession();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return session;
    }
}
