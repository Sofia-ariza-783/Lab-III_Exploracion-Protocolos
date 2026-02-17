package co.eci.protocols.exercices.Datagram;

import java.util.Scanner;

/**
 * UI simple de consola para ejecutar servidor/cliente y facilitar pruebas.
 */
public final class ConsoleUI {

    private final Scanner sc = new Scanner(System.in);

    public void start() {
        System.out.println("=======================================");
        System.out.println(" UDP Time App (Datagramas)");
        System.out.println("=======================================");
        System.out.println("1) Ejecutar SERVIDOR");
        System.out.println("2) Ejecutar CLIENTE");
        System.out.println("3) Ejecutar AMBOS (demo local)");
        System.out.print("Elige una opción (1-3): ");

        String option = sc.nextLine().trim();

        if ("1".equals(option)) {
            runServer();
        } else if ("2".equals(option)) {
            runClient();
        } else if ("3".equals(option)) {
            runBoth();
        } else {
            System.out.println("Opción inválida.");
        }
    }

    private void runServer() {
        int port = readInt("Puerto del servidor", TimeProtocol.DEFAULT_PORT);

        DatagramTimeServer server = new DatagramTimeServer(port);
        Thread t = new Thread(server, "UdpTimeServer");
        t.start();

        System.out.println("\n Servidor ejecutándose. ENTER para detener...");
        sc.nextLine();

        server.stop();
    }

    private void runClient() {
        String host = readString("Host/IP del servidor", TimeProtocol.DEFAULT_HOST);
        int port = readInt("Puerto del servidor", TimeProtocol.DEFAULT_PORT);
        int interval = readInt("Intervalo (ms)", TimeProtocol.DEFAULT_INTERVAL_MS);
        int timeout = readInt("Timeout (ms)", TimeProtocol.DEFAULT_TIMEOUT_MS);

        DatagramTimeClient client = new DatagramTimeClient(host, port, interval, timeout);
        Thread t = new Thread(client, "UdpTimeClient");
        t.start();

        System.out.println("\n Cliente ejecutándose. ENTER para detener...");
        sc.nextLine();

        client.stop();
    }

    private void runBoth() {
        int port = readInt("Puerto (demo local)", TimeProtocol.DEFAULT_PORT);
        int interval = readInt("Intervalo (ms)", TimeProtocol.DEFAULT_INTERVAL_MS);
        int timeout = readInt("Timeout (ms)", TimeProtocol.DEFAULT_TIMEOUT_MS);

        DatagramTimeServer server = new DatagramTimeServer(port);
        DatagramTimeClient client = new DatagramTimeClient(TimeProtocol.DEFAULT_HOST, port, interval, timeout);

        Thread ts = new Thread(server, "UdpTimeServer");
        Thread tc = new Thread(client, "UdpTimeClient");
        ts.start();
        tc.start();

        System.out.println("\n Demo local iniciada.");
        System.out.println("ENTER para simular caída del servidor...");
        sc.nextLine();
        server.stop();

        System.out.println("Servidor detenido. El cliente seguirá intentando...");
        System.out.println("ENTER para detener cliente y finalizar...");
        sc.nextLine();
        client.stop();
    }

    private int readInt(String label, int def) {
        System.out.print(label + " [Enter=" + def + "]: ");
        String s = sc.nextLine().trim();
        if (s.isEmpty()) return def;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println(" Número inválido. Se usará: " + def);
            return def;
        }
    }

    private String readString(String label, String def) {
        System.out.print(label + " [Enter=" + def + "]: ");
        String s = sc.nextLine().trim();
        return s.isEmpty() ? def : s;
    }
}