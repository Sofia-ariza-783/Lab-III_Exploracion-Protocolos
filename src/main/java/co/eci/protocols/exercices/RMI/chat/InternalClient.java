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
            echoServer = (ChatUser) UnicastRemoteObject.exportObject(this, 23001);
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

    public void messaging(String cadena) throws RemoteException {
        System.out.println("User2: " + cadena);
    }
}