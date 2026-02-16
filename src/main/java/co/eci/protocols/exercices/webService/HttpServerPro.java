package co.eci.protocols.exercices.webService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServerPro {
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

        String body =
                "<!DOCTYPE html>"
                        + "<html><head><meta charset=\"UTF-8\"><title>Hello</title></head>"
                        + "<body>"
                        + "<h1>Hello World</h1>"
                        + "<ul>"
                        + "<li><a href=\"/images/0c8513d4db5e903e501f8461d4146345.jpg\" download >0c8513d4db5e903e501f8461d4146345.jpg</a></li>"
                        + "<li><a href=\"/images/5e196899ff4b98d4352d1ba7337db1ee.jpg\">5e196899ff4b98d4352d1ba7337db1ee.jpg</a></li>"
                        + "<li><a href=\"/images/OIP.png\">OIP.png</a></li>"
                        + "</ul>"
                        + "</body>"
                        + "</html>";

        String response =
                "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html; charset=UTF-8\r\n"
                        + "Content-Length: " + body.getBytes("UTF-8").length + "\r\n"
                        + "Connection: close\r\n"
                        + "\r\n"
                        + body;

        while (response != "hola") {
            out.print(response);
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}