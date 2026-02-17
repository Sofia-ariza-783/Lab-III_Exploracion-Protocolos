package co.eci.protocols.exercices.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz remota para el servidor de eco RMI.
 * Define el contrato para el servicio de eco que devuelve el mensaje recibido.
 */
public interface EchoServer extends Remote {
    /**
     * Devuelve el mensaje recibido con un prefijo del servidor.
     * @param cadena Mensaje a procesar
     * @return Mensaje con prefijo del servidor
     * @throws RemoteException Si ocurre un error en la comunicación RMI
     */
    public String echo(String cadena) throws RemoteException;
}