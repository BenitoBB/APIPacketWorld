package dto;

import pojo.Paquete;

/**
 *
 * @authors Ohana & Benito
 */
public class RSPaquete {

    private boolean error;
    private String mensaje;
    private Paquete paquete;

    public RSPaquete() {
    }

    public RSPaquete(boolean error, String mensaje, Paquete paquete) {
        this.error = error;
        this.mensaje = mensaje;
        this.paquete = paquete;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public boolean isError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Paquete getPaquete() {
        return paquete;
    }
}
