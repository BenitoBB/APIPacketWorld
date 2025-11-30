package dto;

import pojo.Cliente;

/**
 *
 * @authors Ohana & Benito
 */
public class RSCliente {
    
    private boolean error;
    private String mensaje;
    private Cliente cliente;

    public RSCliente() {
    }

    public RSCliente(boolean error, String mensaje, Cliente cliente) {
        this.error = error;
        this.mensaje = mensaje;
        this.cliente = cliente;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
