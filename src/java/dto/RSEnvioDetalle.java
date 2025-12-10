package dto;

import java.util.List;
import pojo.Direccion;
import pojo.Paquete;

public class RSEnvioDetalle {

    private int idEnvio;
    private String numeroGuia;

    private String fechaEnvio;
    private String fechaEntrega;

    private String nombreDestinatario;
    private String apellidoPaternoDestinatario;
    private String apellidoMaternoDestinatario;

    private String clienteNombre;
    private String clienteApellidoPaterno;
    private String clienteApellidoMaterno;
    private String clienteTelefono;
    private String clienteCorreo;

    private String sucursalOrigen;

    private String dirCalle;
    private String dirNumero;
    private String dirColonia;
    private String dirCP;
    private String dirCiudad;
    private String dirEstado;

    private String estatus;
    private List<Paquete> paquetes;



    public RSEnvioDetalle() {}

    public int getIdEnvio() {
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

    public String getClienteNombre() {
        return clienteNombre;
    }

    public String getClienteApellidoPaterno() {
        return clienteApellidoPaterno;
    }

    public String getClienteApellidoMaterno() {
        return clienteApellidoMaterno;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public String getClienteCorreo() {
        return clienteCorreo;
    }

    public String getSucursalOrigen() {
        return sucursalOrigen;
    }

    public String getDirCalle() {
        return dirCalle;
    }

    public String getDirNumero() {
        return dirNumero;
    }

    public String getDirColonia() {
        return dirColonia;
    }

    public String getDirCP() {
        return dirCP;
    }

    public String getDirCiudad() {
        return dirCiudad;
    }

    public String getDirEstado() {
        return dirEstado;
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

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public void setClienteApellidoPaterno(String clienteApellidoPaterno) {
        this.clienteApellidoPaterno = clienteApellidoPaterno;
    }

    public void setClienteApellidoMaterno(String clienteApellidoMaterno) {
        this.clienteApellidoMaterno = clienteApellidoMaterno;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }

    public void setClienteCorreo(String clienteCorreo) {
        this.clienteCorreo = clienteCorreo;
    }

    public void setSucursalOrigen(String sucursalOrigen) {
        this.sucursalOrigen = sucursalOrigen;
    }

    public void setDirCalle(String dirCalle) {
        this.dirCalle = dirCalle;
    }

    public void setDirNumero(String dirNumero) {
        this.dirNumero = dirNumero;
    }

    public void setDirColonia(String dirColonia) {
        this.dirColonia = dirColonia;
    }

    public void setDirCP(String dirCP) {
        this.dirCP = dirCP;
    }

    public void setDirCiudad(String dirCiudad) {
        this.dirCiudad = dirCiudad;
    }

    public void setDirEstado(String dirEstado) {
        this.dirEstado = dirEstado;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    
    public List<Paquete> getPaquetes() {
    return paquetes;
    }

    public void setPaquetes(List<Paquete> paquetes) {
        this.paquetes = paquetes;
    }
    

    
    
}
