package co.eci.protocols.exercices.RPC.p2p;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Cliente para comunicarse con el servidor tracker P2P.
 * Permite registrar peers y obtener la lista de peers activos.
 */
public class TrackerClient {
    private final String host;
    private final int port;

    /**
     * Constructor que inicializa el cliente del tracker.
     * @param host Dirección del servidor tracker
     * @param port Puerto del servidor tracker
     */
    public TrackerClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Registra un peer en el tracker.
     * @param peerId Identificador del peer
     * @param peerPort Puerto del peer
     */
    public void register(String peerId, int peerPort) {
        String resp = request("REGISTER " + peerId + " " + peerPort);
        if (!resp.startsWith("OK")) throw new RuntimeException("Register failed: " + resp);
    }

    /**
     * Obtiene la lista de peers registrados.
     * @return Mapa de peers con sus direcciones
     */
    public Map<String, HostPort> listPeers() {
        String resp = request("LIST");
        if (!resp.startsWith("PEERS")) throw new RuntimeException("LIST failed: " + resp);

        String payload = resp.substring("PEERS".length()).trim();
        Map<String, HostPort> out = new HashMap<>();
        if (payload.isBlank()) return out;

        String[] entries = payload.split(",");
        for (String e : entries) {
            // peerId@ip:port
            String[] a = e.split("@");
            String peerId = a[0];
            String[] hp = a[1].split(":");
            out.put(peerId, new HostPort(hp[0], Integer.parseInt(hp[1])));
        }
        return out;
    }

    /**
     * Envía una peticion al tracker y recibe respuesta.
     * @param line Linea de comando a enviar
     * @return Respuesta del tracker
     */
    private String request(String line) {
        try (Socket s = new Socket(host, port);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            out.write(line);
            out.newLine();
            out.flush();
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Tracker unreachable: " + e.getMessage(), e);
        }
    }

    /**
     * Clase que representa una dirección host:puerto.
     */
    public static class HostPort {
        public final String host;
        public final int port;

        /**
         * Constructor que inicializa host y puerto.
         * @param host Dirección host
         * @param port Numero de puerto
         */
        public HostPort(String host, int port) {
            this.host = host;
            this.port = port;
        }
    }
}
