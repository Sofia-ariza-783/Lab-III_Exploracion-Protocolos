package co.eci.protocols.exercices.RPC;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Servidor RPC que procesa invocaciones remotas de metodos.
 * Maneja conexiones de clientes y despacha peticiones al servicio de calculadora.
 */
public class RpcServer {
    private final int port;
    private final CalculatorService service;

    /**
     * Constructor que inicializa el servidor RPC.
     * @param port Puerto de escucha
     * @param service Servicio de calculadora a invocar
     */
    public RpcServer(int port, CalculatorService service) {
        this.port = port;
        this.service = service;
    }

    /**
     * Metodo principal que inicia el servidor RPC.
     * @param args Argumentos de linea de comandos (no utilizados)
     * @throws Exception Si ocurre un error al iniciar el servidor
     */
    public static void main(String[] args) throws Exception {
        new RpcServer(5000, new CalculatorServiceImpl()).start();
    }

    /**
     * Inicia el servidor y acepta conexiones de clientes.
     * @throws IOException Si ocurre un error de red
     */
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[RPC] Server listening on port " + port);
            while (true) {
                Socket client = serverSocket.accept();
                new Thread(() -> handleClient(client)).start();
            }
        }
    }

    /**
     * Maneja la conexion de un cliente individual.
     * @param client Socket del cliente conectado
     */
    private void handleClient(Socket client) {
        try (client;
             BufferedReader in = new BufferedReader(new
                     InputStreamReader(client.getInputStream()));
             BufferedWriter out = new BufferedWriter(new
                     OutputStreamWriter(client.getOutputStream()))) {

            String line = in.readLine();
            if (line == null || line.isBlank()) return;

            Map<String, String> req = RpcProtocol.parseLine(line);
            String id = req.getOrDefault("id", "no-id");

            String method = req.get("method");
            String params = req.getOrDefault("params", "");

            String response;
            try {
                int result = dispatch(method, params);
                response = RpcProtocol.buildResponse(id, true, String.valueOf(result), null);
            } catch (Exception e) {
                response = RpcProtocol.buildResponse(id, false, null, e.getMessage());
            }

            out.write(response);
            out.newLine();
            out.flush();

        } catch (IOException e) {
            System.out.println("[RPC] Client error: " + e.getMessage());
        }
    }

    /**
     * Despacha una peticion RPC al metodo correspondiente.
     * @param method Nombre del metodo a invocar
     * @param params Parametros del metodo en formato CSV
     * @return Resultado de la operacion
     */
    private int dispatch(String method, String params) {
        if (method == null) throw new IllegalArgumentException("Missing method");

        switch (method) {
            case "add": {
                int[] p = parseInts(params, 2);
                return service.add(p[0], p[1]);
            }
            case "square": {
                int[] p = parseInts(params, 1);
                return service.square(p[0]);
            }
            default:
                throw new IllegalArgumentException("Unknown method: " + method);
        }
    }

    /**
     * Convierte una cadena CSV en un array de enteros.
     * @param csv Cadena con numeros separados por comas
     * @param expected Cantidad de numeros esperados
     * @return Array de enteros parseados
     */
    private int[] parseInts(String csv, int expected) {
        String[] parts = csv.split(",");
        if (parts.length != expected) throw new IllegalArgumentException("Expected " + expected + "params");
        int[] out = new int[expected];
        for (int i = 0; i < expected; i++) out[i] = Integer.parseInt(parts[i].trim());
        return out;
    }
}