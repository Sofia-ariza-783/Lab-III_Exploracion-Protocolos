package co.eci.protocols.exercices.Datagram;

import java.io.IOException;
import java.net.*;
import java.time.Instant;

/**
 * Cliente UDP que consulta la hora al servidor cada N ms.
 * Si no recibe respuesta (timeout), mantiene la última hora conocida.
 */
public final class DatagramTimeClient implements Runnable {

    private final String host;
    private final int port;
    private final int intervalMs;
    private final int timeoutMs;

    private volatile boolean running;
    private DatagramSocket socket;

    public DatagramTimeClient(String host, int port, int intervalMs, int timeoutMs) {
        this.host = host;
        this.port = port;
        this.intervalMs = intervalMs;
        this.timeoutMs = timeoutMs;
    }

    @Override
    public void run() {
        running = true;
        String lastKnownTime = "(sin datos aún)";

        try (DatagramSocket s = new DatagramSocket()) {
            socket = s;
            s.setSoTimeout(timeoutMs);

            InetAddress serverAddress = InetAddress.getByName(host);

            System.out.println("Cliente consultando a " + host + ":" + port +
                    " cada " + (intervalMs / 1000.0) + " s (timeout " + timeoutMs + " ms).");

            final byte[] payload = TimeProtocol.REQUEST_MESSAGE.getBytes(TimeProtocol.CHARSET);

            while (running) {
                DatagramPacket request = new DatagramPacket(payload, payload.length, serverAddress, port);
                try {
                    s.send(request);
                } catch (IOException e) {
                    System.out.println("[" + Instant.now() + "] No se pudo enviar: " + e.getMessage());
                }

                try {
                    DatagramPacket response = new DatagramPacket(
                            new byte[TimeProtocol.BUFFER_SIZE],
                            TimeProtocol.BUFFER_SIZE
                    );
                    s.receive(response);

                    String received = new String(
                            response.getData(),
                            response.getOffset(),
                            response.getLength(),
                            TimeProtocol.CHARSET
                    ).trim();

                    lastKnownTime = received;
                    System.out.println("[" + Instant.now() + "] Actualizado: " + lastKnownTime);

                } catch (SocketTimeoutException e) {
                    System.out.println("[" + Instant.now() + "] Sin respuesta. Mantengo: " + lastKnownTime);
                } catch (SocketException e) {
                    if (running) System.out.println("[" + Instant.now() + "] 🟩 Socket: " + e.getMessage());
                    break;
                } catch (IOException e) {
                    System.out.println("[" + Instant.now() + "] Error recibiendo. Mantengo: " + lastKnownTime);
                }

                sleep(intervalMs);
            }

        } catch (UnknownHostException e) {
            System.err.println("Host inválido: " + host);
        } catch (SocketException e) {
            System.err.println("No se pudo abrir socket del cliente: " + e.getMessage());
        } finally {
            System.out.println("Cliente finalizado.");
        }
    }

    /** Detiene el cliente (cierra socket para salir de receive()). */
    public void stop() {
        running = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            running = false;
        }
    }
}