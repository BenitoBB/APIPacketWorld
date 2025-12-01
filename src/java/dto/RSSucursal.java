package dto;

import pojo.Sucursal;

/**
 *
 * @authors Ohana & Benito
 */
public class RSSucursal {

    private boolean error;
    private String mensaje;
    private Sucursal sucursal;

    public RSSucursal() {
    }

    public RSSucursal(boolean error, String mensaje, Sucursal sucursal) {
        this.error = error;
        this.mensaje = mensaje;
        this.sucursal = sucursal;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public boolean isError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }
}
