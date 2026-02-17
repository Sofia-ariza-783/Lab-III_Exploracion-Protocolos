package co.eci.protocols.exercices.RMI.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatUser extends Remote {
    public void messaging(String cadena) throws RemoteException;
}