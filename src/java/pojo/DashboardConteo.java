package pojo;

/**
 *
 * @authors Ohana & Benito
 */
public class DashboardConteo {

    private String etiqueta;
    private int total;

    public DashboardConteo() {
    }

    public DashboardConteo(String etiqueta, int total) {
        this.etiqueta = etiqueta;
        this.total = total;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public int getTotal() {
        return total;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
