package pojo;

import java.math.BigDecimal;

/**
 *
 * @authors Ohana & Benito
 */
public class Paquete {
    
    private Integer idPaquete;
    private String descripcion;
    private BigDecimal peso;
    private BigDecimal alto;
    private BigDecimal ancho;
    private BigDecimal profundidad;
    // Foreign Key
    private Integer idEnvio;
    
    // falta getters y setters posible cambio de tipo a dimensiones
}
