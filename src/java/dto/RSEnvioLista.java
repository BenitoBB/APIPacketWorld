
package dto;

/**
 *
 * @author ohana
 */
public class RSEnvioLista {
    private int idEnvio;
    private String numeroGuia;
    private String direccionDestino;
    private String estatus;

    public RSEnvioLista() {
    }

    public RSEnvioLista(int idEnvio, String numeroGuia, String direccionDestino, String estatus) {
        this.idEnvio = idEnvio;
        this.numeroGuia = numeroGuia;
        this.direccionDestino = direccionDestino;
        this.estatus = estatus;
    }

    public int getIdEnvio() {
        return idEnvio;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setIdEnvio(int idEnvio) {
        this.idEnvio = idEnvio;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    
    
}
