package co.eci.protocols.exercices.webService;

import java.net.*;
import java.io.*;

//Ejercicio 4.4

/**
 * Servidor HTTP basico que responde con una pagina HTML simple.
 * Escucha en el puerto 35000 y sirve un contenido estatico.
 */
public class HttpServer {

    /**
     * Metodo principal que inicia el servidor HTTP basico.
     * Escucha peticiones y responde con una pagina HTML.
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
            System.out.println("Listo para recibir ...");
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
            System.out.println("Received: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }
        String body = "<!DOCTYPE html>"
                + "<html><head><meta charset=\"UTF-8\"><title>My Web Site</title></head>"
                + "<body>My Web Site</body></html>";

        String response =
                "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html; charset=UTF-8\r\n"
                        + "Content-Length: " + body.getBytes("UTF-8").length + "\r\n"
                        + "Connection: close\r\n"
                        + "\r\n"
                        + body;

        out.print(response);
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}