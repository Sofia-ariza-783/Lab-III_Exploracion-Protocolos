package co.eci.protocols.exercices.RMI.chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ExternalClient implements ChatUser {
    ChatUser echoServer;

    public ExternalClient(String ipRMIregistry,
                          int puertoRMIregistry, String nombreDePublicacion) {
        try {
            ChatUser echoServer =
                    (ChatUser) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry(ipRMIregistry, puertoRMIregistry);
            registry.rebind(nombreDePublicacion, echoServer);
            System.out.println("Echo server ready...");
        } catch (Exception e) {
            System.err.println("Echo server exception:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExternalClient ec = new ExternalClient("127.0.0.2", 23001, "echoServer");
        ec.ejecutaServicio("127.0.0.1", 23000, "echoServer");
    }

    public void ejecutaServicio(String ipRmiregistry, int puertoRmiRegistry,
                                String nombreServicio) {
        Scanner scanner = new Scanner(System.in);
        String inputLine = " ";
        while (!inputLine.equals("Salir")) {
            inputLine = scanner.nextLine();
            try {
                Registry registry = LocateRegistry.getRegistry(ipRmiregistry, puertoRmiRegistry);
                echoServer = (ChatUser) registry.lookup(nombreServicio);

                System.out.println("Tu: " + inputLine);
                echoServer.messaging(inputLine);
            } catch (Exception e) {
                System.err.println("Hay un problema:");
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    public void messaging(String cadena) throws RemoteException {
        System.out.println("User1: " + cadena);
    }
}