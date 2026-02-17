package co.eci.protocols.exercices.RMI.chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class InternalClient implements ChatUser {
    ChatUser echoServer;
    private static Scanner scanner = new Scanner(System.in);

    public InternalClient(String ipRMIregistry,
                          int puertoRMIregistry, String nombreDePublicacion) {
        try {
            echoServer = (ChatUser) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry(ipRMIregistry, puertoRMIregistry);
            registry.rebind(nombreDePublicacion, echoServer);
            System.out.println("Echo server ready...");
        } catch (Exception e) {
            System.err.println("Echo server exception:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.print("Ingrese la ip del User1: ");
        String ipAddress = scanner.nextLine();
        System.out.print("Ingrese el puerto del User1: ");
        int port = scanner.nextInt();
        InternalClient ec = new InternalClient(ipAddress, port, "internalUser");
        ec.ejecutaServicio(ipAddress, port, "externalUser");
    }

    public void ejecutaServicio(String ipRmiregistry, int puertoRmiRegistry,
                                String nombreServicio) {
        String inputLine = scanner.nextLine();
        while (!inputLine.equals("Salir")) {
            try {
                Registry registry = LocateRegistry.getRegistry(ipRmiregistry, puertoRmiRegistry);
                echoServer = (ChatUser) registry.lookup(nombreServicio);

                echoServer.messaging(inputLine);
            } catch (Exception e) {
                System.err.println("Hay un problema:");
                e.printStackTrace();
            }
            inputLine = scanner.nextLine();
        }
        scanner.close();
    }

    public void messaging(String cadena) throws RemoteException {
        System.out.println("\nUser2: " + cadena);
    }
}