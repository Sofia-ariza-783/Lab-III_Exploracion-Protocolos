package co.eci.protocols.exercices.Datagram;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.ZonedDateTime;

/**
 * Servidor UDP que responde con la hora actual del sistema a cada solicitud recibida.
 */
public final class DatagramTimeServer implements Runnable {

    private final int port;
    private volatile boolean running;
    private DatagramSocket socket;

    public DatagramTimeServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        running = true;

        try (DatagramSocket s = new DatagramSocket(port)) {
            socket = s;
            System.out.println("Servidor UDP de hora escuchando en el puerto " + port + " ...");

            while (running) {
                DatagramPacket request = new DatagramPacket(
                        new byte[TimeProtocol.BUFFER_SIZE],
                        TimeProtocol.BUFFER_SIZE
                );

                try {
                    s.receive(request);

                    String now = ZonedDateTime.now().format(TimeProtocol.FORMATTER);
                    byte[] outBuf = now.getBytes(TimeProtocol.CHARSET);

                    DatagramPacket response = new DatagramPacket(
                            outBuf, outBuf.length,
                            request.getAddress(), request.getPort()
                    );
                    s.send(response);

                } catch (SocketException e) {
                    if (running) System.out.println("Servidor: error de socket: " + e.getMessage());
                    break;
                } catch (IOException e) {
                    System.out.println("Servidor: error E/S: " + e.getMessage());
                }
            }

        } catch (SocketException e) {
            System.err.println("No se pudo abrir el socket en el puerto " + port + ": " + e.getMessage());
        } finally {
            System.out.println("Servidor finalizado.");
        }
    }

    /** Detiene el servidor (cierra socket para salir de receive()). */
    public void stop() {
        running = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}