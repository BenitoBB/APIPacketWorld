package dto;

import pojo.Unidad;

/**
 *
 * @authors Ohana & Benito
 */
public class RSUnidad {

    private boolean error;
    private String mensaje;
    private Unidad unidad;

    public RSUnidad() {
    }

    public RSUnidad(boolean error, String mensaje, Unidad unidad) {
        this.error = error;
        this.mensaje = mensaje;
        this.unidad = unidad;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public boolean isError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Unidad getUnidad() {
        return unidad;
    }
}
