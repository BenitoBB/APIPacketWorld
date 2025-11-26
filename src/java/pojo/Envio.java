package pojo;

import java.math.BigDecimal;

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
    private BigDecimal costo;
    // Foreign Keys
    private Integer idClienteRemitente;
    private Integer idSucursalOrigen;
    private Integer idSucursalDestino;
    private Integer idColaborador;
    private Integer idUnidad;
    private Integer idDireccionDestino;
    private Integer idEstatus;

    public Envio() {
    }

    public Envio(Integer idEnvio, String numeroGuia, String fechaEnvio, String fechaEntrega, String nombreDestinatario, String apellidoPaternoDestinatario, String apellidoMaternoDestinatario, BigDecimal costo, Integer idClienteRemitente, Integer idSucursalOrigen, Integer idSucursalDestino, Integer idColaborador, Integer idUnidad, Integer idDireccionDestino, Integer idEstatus) {
        this.idEnvio = idEnvio;
        this.numeroGuia = numeroGuia;
        this.fechaEnvio = fechaEnvio;
        this.fechaEntrega = fechaEntrega;
        this.nombreDestinatario = nombreDestinatario;
        this.apellidoPaternoDestinatario = apellidoPaternoDestinatario;
        this.apellidoMaternoDestinatario = apellidoMaternoDestinatario;
        this.costo = costo;
        this.idClienteRemitente = idClienteRemitente;
        this.idSucursalOrigen = idSucursalOrigen;
        this.idSucursalDestino = idSucursalDestino;
        this.idColaborador = idColaborador;
        this.idUnidad = idUnidad;
        this.idDireccionDestino = idDireccionDestino;
        this.idEstatus = idEstatus;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }

    public void setApellidoPaternoDestinatario(String apellidoPaternoDestinatario) {
        this.apellidoPaternoDestinatario = apellidoPaternoDestinatario;
    }

    public void setApellidoMaternoDestinatario(String apellidoMaternoDestinatario) {
        this.apellidoMaternoDestinatario = apellidoMaternoDestinatario;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public void setIdClienteRemitente(Integer idClienteRemitente) {
        this.idClienteRemitente = idClienteRemitente;
    }

    public void setIdSucursalOrigen(Integer idSucursalOrigen) {
        this.idSucursalOrigen = idSucursalOrigen;
    }

    public void setIdSucursalDestino(Integer idSucursalDestino) {
        this.idSucursalDestino = idSucursalDestino;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public void setIdDireccionDestino(Integer idDireccionDestino) {
        this.idDireccionDestino = idDireccionDestino;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    public String getApellidoPaternoDestinatario() {
        return apellidoPaternoDestinatario;
    }

    public String getApellidoMaternoDestinatario() {
        return apellidoMaternoDestinatario;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public Integer getIdClienteRemitente() {
        return idClienteRemitente;
    }

    public Integer getIdSucursalOrigen() {
        return idSucursalOrigen;
    }

    public Integer getIdSucursalDestino() {
        return idSucursalDestino;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public Integer getIdDireccionDestino() {
        return idDireccionDestino;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }
}
