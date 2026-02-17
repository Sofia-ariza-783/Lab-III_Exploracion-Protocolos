package co.eci.protocols.exercices.RPC;

import java.util.*;

/**
 * Utilidades para el protocolo de comunicacion RPC.
 * Proporciona metodos para parsear y construir mensajes RPC.
 */
public final class RpcProtocol {
    private RpcProtocol() {
    }

    /**
     * Parsea una linea de mensaje RPC en un mapa de parametros.
     * @param line Linea de mensaje RPC (formato: key=value;key=value)
     * @return Mapa con los parametros parseados
     */
    public static Map<String, String> parseLine(String line) {
        Map<String, String> map = new HashMap<>();
        String[] parts = line.split(";");
        for (String p : parts) {
            int eq = p.indexOf('=');
            if (eq > 0) {
                String k = p.substring(0, eq).trim();
                String v = p.substring(eq + 1).trim();
                map.put(k, v);
            }
        }
        return map;
    }

    /**
     * Construye una respuesta RPC formateada.
     * @param id ID de la peticion
     * @param ok Indica si la operacion fue exitosa
     * @param result Resultado de la operacion (si ok=true)
     * @param error Mensaje de error (si ok=false)
     * @return Linea de respuesta RPC formateada
     */
    public static String buildResponse(String id, boolean ok, String result, String error) {
        if (ok) {
            return "id=" + id + ";ok=true;result=" + result;
        }
        return "id=" + id + ";ok=false;error=" + sanitize(error);
    }

    /**
     * Elimina caracteres problematicos de un mensaje de error.
     * @param s Mensaje a sanitizar
     * @return Mensaje sanitizado
     */
    private static String sanitize(String s) {
        if (s == null) return "";
        return s.replace(";", ",");
    }

}