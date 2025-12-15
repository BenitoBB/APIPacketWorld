package pojo;

/**
 *
 * @authors Ohana & Benito
 */
public class Sucursal {

    private Integer idSucursal;
    private String codigo;
    private String nombre;
    private String estatus;
    // Foreign Key
    private Integer idDireccion;
    // OBJETO COMPLETO
    private Direccion direccion;

    public Sucursal() {
    }

    public Sucursal(Integer idSucursal, String codigo, String nombre, String estatus, Integer idDireccion, Direccion direccion) {
        this.idSucursal = idSucursal;
        this.codigo = codigo;
        this.nombre = nombre;
        this.estatus = estatus;
        this.idDireccion = idDireccion;
        this.direccion = direccion;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstatus() {
        return estatus;
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public Direccion getDireccion() {
        return direccion;
    }
}
