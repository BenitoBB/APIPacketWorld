package dominio;

import dto.RSCostoEnvio;
import utilidades.Mensajes;

public class CostoEnvioImp {

    public static RSCostoEnvio calcularCosto(
            String cpOrigen,
            String cpDestino,
            int paquetes) {

        RSCostoEnvio respuesta = new RSCostoEnvio();

        try {
            double distancia = DistanciaImp.obtenerDistancia(cpOrigen, cpDestino);
            double total = calcularCostoEnvio(distancia, paquetes);

            respuesta.setCpOrigen(cpOrigen);
            respuesta.setCpDestino(cpDestino);
            respuesta.setDistanciaKm(distancia);
            respuesta.setPaquetes(paquetes);
            respuesta.setCostoTotal(total);
            respuesta.setError(false);
            respuesta.setMensaje(Mensajes.COSTO_CALCULADO);

        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setMensaje(Mensajes.COSTO_ERROR + e.getMessage());
        }

        return respuesta;
    }

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
