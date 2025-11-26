package pojo;

import java.sql.Timestamp;

/**
 *
 * @authors Ohana & Benito
 */
public class EstatusEnvioHistorial {
    
    private Integer idHistorial;
    private String comentario;
    private Timestamp fechaHora; 
    // Foreign Keys
    private Integer idEnvio;
    private Integer idEstatus;
    private Integer idColaborador; 
    
    // falta getters y setters, posible cambio de tipo fechaHora
    
}
