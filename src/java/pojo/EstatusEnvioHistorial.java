package pojo;

import java.sql.Timestamp;

/**
 *
 * @authors Ohana & Benito
 */
public class EstatusEnvioHistorial {

    private Integer idHistorial;
    private String comentario;
    private String fechaHora;
    // Foreign Keys
    private Integer idEnvio;
    private Integer idEstatus;
    private Integer idColaborador;

    public EstatusEnvioHistorial() {
    }

    public EstatusEnvioHistorial(Integer idHistorial, String comentario, String fechaHora, Integer idEnvio, Integer idEstatus, Integer idColaborador) {
        this.idHistorial = idHistorial;
        this.comentario = comentario;
        this.fechaHora = fechaHora;
        this.idEnvio = idEnvio;
        this.idEstatus = idEstatus;
        this.idColaborador = idColaborador;
    }

    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdHistorial() {
        return idHistorial;
    }

    public String getComentario() {
        return comentario;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }
}
