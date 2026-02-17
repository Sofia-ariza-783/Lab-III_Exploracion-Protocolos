package co.eci.protocols.exercices.RMI.chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Cliente interno de chat RMI que actúa como servidor y cliente.
 * Permite enviar mensajes a un cliente externo y recibir mensajes de él.
 */
public class InternalClient implements ChatUser {
    ChatUser echoServer;
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Constructor que inicializa el cliente interno y lo registra en RMI.
     * @param ipRMIregistry IP del registro RMI
     * @param puertoRMIregistry Puerto del registro RMI
     * @param nombreDePublicacion Nombre con el que se publica el servicio
     */
    public InternalClient(String ipRMIregistry,
                          int puertoRMIregistry, String nombreDePublicacion) {
        try {
            echoServer = (ChatUser) UnicastRemoteObject.exportObject(this, 23001);
            Registry registry = LocateRegistry.getRegistry(ipRMIregistry, puertoRMIregistry);
            registry.rebind(nombreDePublicacion, echoServer);
            System.out.println("Echo server ready...");
        } catch (Exception e) {
            System.err.println("Echo server exception:");
            e.printStackTrace();
        }
    }

    /**
     * Metodo principal que inicia el cliente interno.
     * Solicita IP y puerto del usuario externo para establecer conexión.
     */
    public static void main(String[] args) {
        System.out.print("Ingrese la ip del User1: ");
        String ipAddress = scanner.nextLine();
        System.out.print("Ingrese el puerto del User1: ");
        int port = scanner.nextInt();
        InternalClient ec = new InternalClient(ipAddress, port, "internalUser");
        ec.ejecutaServicio(ipAddress, port, "externalUser");
    }

    /**
     * Ejecuta el servicio de chat permitiendo enviar mensajes al usuario externo.
     * @param ipRmiregistry IP del registro RMI del usuario externo
     * @param puertoRmiRegistry Puerto del registro RMI del usuario externo
     * @param nombreServicio Nombre del servicio a buscar
     */
    public void ejecutaServicio(String ipRmiregistry, int puertoRmiRegistry,
                                String nombreServicio) {
        System.out.println("Inicio de la conversación");
        String inputLine = "";
        while (!inputLine.equals("Salir")) {
            try {
                inputLine = scanner.nextLine();
                Registry registry = LocateRegistry.getRegistry(ipRmiregistry, puertoRmiRegistry);
                echoServer = (ChatUser) registry.lookup(nombreServicio);
                System.out.print("\033[1A");
                System.out.print("\033[2K");
                System.out.print("\r");

                System.out.println("tu: " + inputLine);
                echoServer.messaging(inputLine);
            } catch (Exception e) {
                System.err.println("Hay un problema:");
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    /**
     * Recibe y muestra un mensaje del usuario externo.
     * @param cadena Mensaje recibido
     * @throws RemoteException Si ocurre un error en la comunicación RMI
     */
    public void messaging(String cadena) throws RemoteException {
        System.out.println("User2: " + cadena);
    }
}