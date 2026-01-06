package utilidades;

/**
 *
 * @author benit
 */
public class Validaciones {

    // Costo por kilómetro según distancia
    public static double obtenerCostoPorKm(double km) {
        if (km <= 200) {
            return 4.0;
        }
        if (km <= 500) {
            return 3.0;
        }
        if (km <= 1000) {
            return 2.0;
        }
        if (km <= 2000) {
            return 1.0;
        }
        return 0.5;
    }

// Costo adicional por paquetes
    public static double obtenerCostoPorPaquetes(int paquetes) {
        switch (paquetes) {
            case 1:
                return 0;
            case 2:
                return 50;
            case 3:
                return 80;
            case 4:
                return 110;
            default:
                return paquetes >= 5 ? 150 : 0;
        }
    }

// Fórmula final
    public static double calcularCostoEnvio(double distanciaKm, int paquetes) {
        double costoKm = obtenerCostoPorKm(distanciaKm);
        double total = (distanciaKm * costoKm) + obtenerCostoPorPaquetes(paquetes);
        return Math.round(total * 100.0) / 100.0;
    }

}
