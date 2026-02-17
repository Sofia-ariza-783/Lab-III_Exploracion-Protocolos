package co.eci.protocols.exercices.RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Cliente RMI que consume un servicio de eco.
 * Se conecta a un servidor remoto para enviar un mensaje y recibir la respuesta.
 */
public class EchoClient {

    /**
     * Metodo principal que inicia el cliente de eco.
     * Establece conexión con el servidor en localhost puerto 23000.
     */
    public static void main(String[] args) {
        EchoClient ec = new EchoClient();
        ec.ejecutaServicio("127.0.0.1", 23000, "echoServer");
    }

    /**
     * Ejecuta el servicio de eco conectándose al servidor RMI.
     * @param ipRmiregistry IP del registro RMI
     * @param puertoRmiRegistry Puerto del registro RMI
     * @param nombreServicio Nombre del servicio a buscar
     */
    public void ejecutaServicio(String ipRmiregistry, int puertoRmiRegistry,
                                String nombreServicio) {
        try {
            Registry registry = LocateRegistry.getRegistry(ipRmiregistry, puertoRmiRegistry);
            EchoServer echoServer = (EchoServer) registry.lookup(nombreServicio);
            System.out.println(echoServer.echo("Hola como estas?"));
        } catch (Exception e) {
            System.err.println("Hay un problema:");
            e.printStackTrace();
        }
    }
} 