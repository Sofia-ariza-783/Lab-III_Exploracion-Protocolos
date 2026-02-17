package co.eci.protocols.exercices.url;

import java.net.MalformedURLException;
import java.net.URL;

//Ejercicio 3.1

/**
 * Demostracion de metodos para extraer componentes de una URL.
 * Muestra por consola la informacion desglosada de una URL de ejemplo.
 */
public class URLs {

    /**
     * Demuestra los metodos de extraccion de componentes de URL.
     * Parsea una URL de Oracle y muestra sus partes por consola.
     */
    public static void methods(){
        try {
            URL sampleURL = new URL("https://docs.oracle.com/javase/tutorial/networking/index.html");

            String protocol = sampleURL.getProtocol();
            String authority = sampleURL.getAuthority();
            String host = sampleURL.getHost();
            int getPort = sampleURL.getPort();
            String query = sampleURL.getQuery();
            String file = sampleURL.getFile();
            String ref = sampleURL.getRef();

            System.out.println("Reporte URL \n"+
                    "Protocolo: " + protocol+
                    "\nAutoridad: " + authority+
                    "\nHost: "+ host +
                    "\nPuerto: "+getPort+
                    "\nQuery: " + query+
                    "\nArchivo: " + file+
                    "\nReferencia: " + ref);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }


}