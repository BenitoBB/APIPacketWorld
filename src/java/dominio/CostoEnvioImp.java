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

            double costoKm;
            if (distancia <= 200) costoKm = 4.0;
            else if (distancia <= 500) costoKm = 3.0;
            else if (distancia <= 1000) costoKm = 2.0;
            else if (distancia <= 2000) costoKm = 1.0;
            else costoKm = 0.5;

            double costoPaquetes;
            switch (paquetes) {
                case 1: costoPaquetes = 0; break;
                case 2: costoPaquetes = 50; break;
                case 3: costoPaquetes = 80; break;
                case 4: costoPaquetes = 110; break;
                default: costoPaquetes = 150;
            }

            double total = (distancia * costoKm) + costoPaquetes;

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
}
