package co.eci.protocols.exercices.RMI.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz remota para usuarios de chat RMI.
 * Define el contrato para enviar mensajes entre clientes.
 */
public interface ChatUser extends Remote {
    /**
     * Envía un mensaje al usuario remoto.
     * @param cadena Mensaje a enviar
     * @throws RemoteException Si ocurre un error en la comunicación RMI
     */
    public void messaging(String cadena) throws RemoteException;
}