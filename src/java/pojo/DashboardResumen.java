package pojo;

import java.math.BigDecimal;

/**
 *
 * @authors Ohana & Benito
 */
public class DashboardResumen {

    private int totalEnvios;
    private int totalClientes;
    private int totalColaboradores;
    private int enviosEntregados;
    private BigDecimal ingresosTotales;

    public DashboardResumen() {
    }

    public DashboardResumen(int totalEnvios, int totalClientes, int totalColaboradores, int enviosEntregados, BigDecimal ingresosTotales) {
        this.totalEnvios = totalEnvios;
        this.totalClientes = totalClientes;
        this.totalColaboradores = totalColaboradores;
        this.enviosEntregados = enviosEntregados;
        this.ingresosTotales = ingresosTotales;
    }

    public int getTotalEnvios() {
        return totalEnvios;
    }

    public int getTotalClientes() {
        return totalClientes;
    }

    public int getTotalColaboradores() {
        return totalColaboradores;
    }

    public int getEnviosEntregados() {
        return enviosEntregados;
    }

    public BigDecimal getIngresosTotales() {
        return ingresosTotales;
    }

    public void setTotalEnvios(int totalEnvios) {
        this.totalEnvios = totalEnvios;
    }

    public void setTotalClientes(int totalClientes) {
        this.totalClientes = totalClientes;
    }

    public void setTotalColaboradores(int totalColaboradores) {
        this.totalColaboradores = totalColaboradores;
    }

    public void setEnviosEntregados(int enviosEntregados) {
        this.enviosEntregados = enviosEntregados;
    }

    public void setIngresosTotales(BigDecimal ingresosTotales) {
        this.ingresosTotales = ingresosTotales;
    }
}
