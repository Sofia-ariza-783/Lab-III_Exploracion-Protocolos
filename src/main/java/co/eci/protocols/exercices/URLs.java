package co.eci.protocols.exercices;

import java.net.MalformedURLException;
import java.net.URL;

public class URLs {

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