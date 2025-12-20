package dto;


public class RSCambioPassword {
    private Integer idColaborador;
    private String passwordActual;
    private String passwordNueva;

    public RSCambioPassword() {
    }

    public RSCambioPassword(Integer idColaborador, String passwordActual, String passwordNueva) {
        this.idColaborador = idColaborador;
        this.passwordActual = passwordActual;
        this.passwordNueva = passwordNueva;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public String getPasswordActual() {
        return passwordActual;
    }

    public String getPasswordNueva() {
        return passwordNueva;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setPasswordActual(String passwordActual) {
        this.passwordActual = passwordActual;
    }

    public void setPasswordNueva(String passwordNueva) {
        this.passwordNueva = passwordNueva;
    }
    
    
}
