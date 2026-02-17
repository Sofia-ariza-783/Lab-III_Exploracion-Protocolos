package co.eci.protocols.exercices.RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementación del servidor de eco RMI.
 * Proporciona el servicio de eco que devuelve mensajes con un prefijo.
 */
public class EchoServerImpl implements EchoServer {

    /**
     * Constructor que inicializa el servidor de eco y lo registra en RMI.
     * @param ipRMIregistry IP del registro RMI
     * @param puertoRMIregistry Puerto del registro RMI
     * @param nombreDePublicacion Nombre con el que se publica el servicio
     */
    public EchoServerImpl(String ipRMIregistry,
                          int puertoRMIregistry, String nombreDePublicacion) {
        try {
            EchoServer echoServer =
                    (EchoServer) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry(ipRMIregistry, puertoRMIregistry);
            registry.rebind(nombreDePublicacion, echoServer);
            System.out.println("Echo server ready...");
        } catch (Exception e) {
            System.err.println("Echo server exception:");
            e.printStackTrace();
        }
    }

    /**
     * Metodo principal que inicia el servidor de eco.
     * Establece el servidor en localhost puerto 23000.
     */
    public static void main(String[] args) {
        EchoServerImpl ec = new EchoServerImpl("127.0.0.1", 23000, "echoServer");
    }

    /**
     * Devuelve el mensaje recibido con un prefijo del servidor.
     * @param cadena Mensaje a procesar
     * @return Mensaje con prefijo "desde el servidor: "
     * @throws RemoteException Si ocurre un error en la comunicación RMI
     */
    public String echo(String cadena) throws RemoteException {
        return "desde el servidor: " + cadena;
    }
}