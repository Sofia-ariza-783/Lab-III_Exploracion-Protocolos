package co.eci.protocols.exercices.sockets;

import java.net.*;
import java.io.*;
//Ejercicio 4.3.1
public class EchoServer {
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