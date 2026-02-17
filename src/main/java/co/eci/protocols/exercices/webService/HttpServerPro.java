package co.eci.protocols.exercices.webService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


//Ejercicio 4.5.1
/**
 * Servidor HTTP mejorado que sirve archivos estaticos.
 * Soporta diferentes tipos de contenido y manejo de errores HTTP.
 */
public class HttpServerPro {

    private static final Path WEB_ROOT = Paths.get(
            "src", "main", "java", "co", "eci", "protocols", "exercices", "webService", "info"
    );

    /**
     * Metodo principal que inicia el servidor HTTP profesional.
     * Escucha peticiones y sirve archivos desde el directorio web.
     * @param args Argumentos de linea de comandos (no utilizados)
     * @throws IOException Si ocurre un error de entrada/salida
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Working dir: " + System.getProperty("user.dir"));

        try (ServerSocket serverSocket = new ServerSocket(35000)) {
            System.out.println("Servidor escuchando en http://localhost:35000");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleRequest(clientSocket);
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Maneja una peticion HTTP entrante.
     * @param clientSocket Socket del cliente conectado
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private static void handleRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();

        String requestLine = in.readLine();
        if (requestLine == null || requestLine.isBlank()) return;

        String[] parts = requestLine.split(" ");
        String method = parts[0];
        String uri = parts.length > 1 ? parts[1] : "/";

        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) { }

        System.out.println("Request: " + requestLine);

        if (!"GET".equals(method)) {
            sendText(out, "405 Method Not Allowed", "text/plain; charset=UTF-8", "Solo GET");
            return;
        }

        if (uri.equals("/")) uri = "/index.html";

        if (uri.contains("..")) {
            sendText(out, "400 Bad Request", "text/plain; charset=UTF-8", "Ruta inválida");
            return;
        }

        Path filePath = WEB_ROOT.resolve(uri.substring(1)).normalize();

        if (!filePath.startsWith(WEB_ROOT)) {
            sendText(out, "403 Forbidden", "text/plain; charset=UTF-8", "Acceso denegado");
            return;
        }

        if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
            sendText(out, "404 Not Found", "text/plain; charset=UTF-8",
                    "No encontrado: " + filePath.toString());
            return;
        }

        byte[] data = Files.readAllBytes(filePath);
        String contentType = guessContentType(filePath);

        String headers =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: " + contentType + "\r\n" +
                        "Content-Length: " + data.length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n";

        out.write(headers.getBytes("UTF-8"));
        out.write(data);
        out.flush();
    }

    /**
     * Envía una respuesta de texto simple con cabeceras HTTP.
     * @param out Stream de salida del cliente
     * @param status Codigo de estado HTTP
     * @param contentType Tipo de contenido
     * @param body Cuerpo de la respuesta
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private static void sendText(OutputStream out, String status, String contentType, String body) throws IOException {
        byte[] bytes = body.getBytes("UTF-8");
        String headers =
                "HTTP/1.1 " + status + "\r\n" +
                        "Content-Type: " + contentType + "\r\n" +
                        "Content-Length: " + bytes.length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n";
        out.write(headers.getBytes("UTF-8"));
        out.write(bytes);
        out.flush();
    }

    /**
     * Determina el tipo de contenido segun la extension del archivo.
     * @param filePath Ruta del archivo a analizar
     * @return Tipo MIME del contenido
     */
    private static String guessContentType(Path filePath) {
        String name = filePath.getFileName().toString().toLowerCase();
        if (name.endsWith(".html") || name.endsWith(".htm")) return "text/html; charset=UTF-8";
        if (name.endsWith(".css")) return "text/css; charset=UTF-8";
        if (name.endsWith(".js")) return "application/javascript; charset=UTF-8";
        if (name.endsWith(".png")) return "image/png";
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) return "image/jpeg";
        if (name.endsWith(".gif")) return "image/gif";
        return "application/octet-stream";
    }
}