package pojo;

/**
 *
 * @authors Ohana & Benito
 */
public class Envio {
    
    private Integer idEnvio;
    private String numeroGuia;
    private String fechaEnvio;
    private String fechaEntrega;
    private String nombreDestinatario;
    private String apellidoPaternoDestinatario;
    private String apellidoMaternoDestinatario;
    // Foreign Keys
    private Integer idClienteRemitente;
    private Integer idSucursalOrigen;
    private Integer idSucursalDestino;
    private Integer idColaborador;
    private Integer idUnidad;
    private Integer idDireccionDestino;
    private Integer idEstatus;
    
    // falta investigar nuevo atributo COSTO y getters y setters
    
}
