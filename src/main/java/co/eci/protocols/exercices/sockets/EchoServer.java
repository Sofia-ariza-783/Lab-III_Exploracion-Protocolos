package co.eci.protocols.exercices.sockets;

import java.net.*;
import java.io.*;
//Ejercicio 4.3.1

/**
 * Servidor de eco que procesa mensajes de clientes mediante sockets.
 * Calcula el cuadrado de numeros o responde mensajes no numericos.
 */
public class EchoServer {

    /**
     * Metodo principal que inicia el servidor de eco.
     * Escucha en el puerto 35000 y procesa un cliente a la vez.
     * @param args Argumentos de linea de comandos (no utilizados)
     * @throws IOException Si ocurre un error de entrada/salida
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null) {
            try{
                int number = Integer.parseInt(inputLine);
                System.out.println("Numero original:" + inputLine);
                outputLine = "Respuesta numero al cuadrado: " + (number * number) ;
            } catch (NumberFormatException e){
                System.out.println("Mensaje original:" + inputLine);
                outputLine = "Respuesta: no es un numero" ;
            }

            out.println(outputLine);
            if (outputLine.equals("Respuesta numero al cuadrado: 1"))
                break;
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
} 