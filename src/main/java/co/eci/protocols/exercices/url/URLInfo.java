package co.eci.protocols.exercices.url;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Utilidad para extraer y generar reportes de informacion de URLs.
 * Parsea una URL y genera un archivo HTML con sus componentes.
 */
public class URLInfo {

    //Ejercicio 3.2
    /**
     * Genera un reporte HTML con la informacion desglosada de una URL.
     * Extrae protocolo, host, puerto, query y otros componentes.
     * @param url URL a analizar y procesar
     */
    public static void GenerateURLInfo(String url){
        try {
            URL sampleURL = new URL(url);
            String protocol = sampleURL.getProtocol();
            String authority = sampleURL.getAuthority();
            String host = sampleURL.getHost();
            int getPort = sampleURL.getPort();
            String query = sampleURL.getQuery();
            String file = sampleURL.getFile();
            String ref = sampleURL.getRef();

            FileWriter escritor = new FileWriter("Archivo.html");
            escritor.write("<html>\n" +
                    "<head>\n" +
                    "    <title>Reporte URL</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h2>Reporte de URL</h2>\n" +
                    "    <p><strong>Protocolo:</strong> " + protocol + "</p>\n" +
                    "    <p><strong>Autoridad:</strong> " + authority + "</p>\n" +
                    "    <p><strong>Host:</strong> " + host + "</p>\n" +
                    "    <p><strong>Puerto:</strong> " + getPort + "</p>\n" +
                    "    <p><strong>Query:</strong> " + query + "</p>\n" +
                    "    <p><strong>Archivo:</strong> " + file + "</p>\n" +
                    "    <p><strong>Referencia:</strong> " + ref + "</p>\n" +
                    "</body>\n" +
                    "</html>");
            escritor.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
