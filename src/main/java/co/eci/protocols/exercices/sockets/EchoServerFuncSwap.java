package co.eci.protocols.exercices.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//Ejercicio 4.3.2
public class EchoServerFuncSwap {
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

        String fun = "cos";
        Map<String, Function<Double, Double>> functions = new HashMap<>();
        functions.put("cos", x -> (double) Math.cos(x));
        functions.put("sin", x -> (double) Math.sin(x));
        functions.put("tan", x -> (double) Math.tan(x));

        while ((inputLine = in.readLine()) != null) {
            String entry = inputLine.split(":")[0];

            if (entry.equals("fun")) {
                if (!functions.containsKey(inputLine.split(":")[1])) {
                    out.println("Operacion no valida");
                    continue;
                }
                fun = inputLine.split(":")[1];
                out.println("Operacion cambiada");
                continue;
            }

            outputLine = String.valueOf(functions.get(fun).apply(Double.parseDouble(inputLine)));
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