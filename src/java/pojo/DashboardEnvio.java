package pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @authors Ohana & Benito
 */
public class DashboardEnvio {

    private String numeroGuia;
    private Date fechaEnvio;
    private BigDecimal costo;

    public DashboardEnvio() {
    }

    public DashboardEnvio(String numeroGuia, Date fechaEnvio, BigDecimal costo) {
        this.numeroGuia = numeroGuia;
        this.fechaEnvio = fechaEnvio;
        this.costo = costo;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
}
