package co.eci.protocols.exercices.RPC.p2p;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Servidor tracker que gestiona la red P2P.
 * Mantiene el registro de peers activos y facilita la descubrimiento.
 */
public class TrackerServer {
    private final int port;
    private final Map<String, PeerInfo> peers = new ConcurrentHashMap<>();

    /**
     * Constructor que inicializa el servidor tracker.
     * @param port Puerto de escucha del tracker
     */
    public TrackerServer(int port) {
        this.port = port;
    }

    /**
     * Metodo principal que inicia el servidor tracker.
     * @param args Argumentos de linea de comandos (no utilizados)
     * @throws Exception Si ocurre un error al iniciar el servidor
     */
    public static void main(String[] args) throws Exception {
        new TrackerServer(6000).start();
    }

    /**
     * Inicia el servidor y acepta conexiones de clientes.
     * @throws IOException Si ocurre un error de red
     */
    public void start() throws IOException {
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("[TRACKER] Listening on " + port);
            while (true) {
                Socket client = ss.accept();
                new Thread(() -> handle(client)).start();
            }
        }
    }

    /**
     * Maneja la conexion de un cliente.
     * @param client Socket del cliente conectado
     */
    private void handle(Socket client) {
        try (client;
             BufferedReader in = new BufferedReader(new
                     InputStreamReader(client.getInputStream()));
             BufferedWriter out = new BufferedWriter(new
                     OutputStreamWriter(client.getOutputStream()))) {

            String line = in.readLine();
            if (line == null) return;

            String[] parts = line.trim().split("\\s+");
            String cmd = parts[0];

            if ("REGISTER".equalsIgnoreCase(cmd)) {
                // REGISTER peerId port
                if (parts.length != 3) {
                    out.write("ERR Invalid REGISTER");
                } else {
                    String peerId = parts[1];
                    int peerPort = Integer.parseInt(parts[2]);
                    String ip = client.getInetAddress().getHostAddress();
                    peers.put(peerId, new PeerInfo(peerId, ip, peerPort));
                    out.write("OK");
                }
                out.newLine();
                out.flush();
                return;
            }

            if ("LIST".equalsIgnoreCase(cmd)) {
                out.write(buildList());
                out.newLine();
                out.flush();
                return;
            }

            out.write("ERR Unknown command");
            out.newLine();
            out.flush();

        } catch (Exception e) {
            System.out.println("[TRACKER] Error: " + e.getMessage());
        }
    }

    /**
     * Construye la lista de peers en formato string.
     * @return Lista de peers formateada
     */
    private String buildList() {
        // PEERS peer1@ip:port,peer2@ip:port
        StringBuilder sb = new StringBuilder("PEERS ");
        boolean first = true;
        for (PeerInfo p : peers.values()) {
            if (!first) sb.append(",");
            sb.append(p.peerId).append("@").append(p.ip).append(":").append(p.port);
            first = false;
        }
        return sb.toString();
    }

    /**
     * Informacion de un peer registrado.
     */
    private static class PeerInfo {
        final String peerId;
        final String ip;
        final int port;

        PeerInfo(String peerId, String ip, int port) {
            this.peerId = peerId;
            this.ip = ip;
            this.port = port;
        }
    }
} 