package co.eci.protocols.exercices;

import java.net.MalformedURLException;
import java.net.URL;

public class URLInfo {


    public static void  GenerateURLInfo(String url){
        try {
            URL sampleURL = new URL(url);
            String protocol = sampleURL.getProtocol();
            String authority = sampleURL.getAuthority();
            String host = sampleURL.getHost();
            int getPort = sampleURL.getPort();
            String query = sampleURL.getQuery();
            String file = sampleURL.getFile();
            String ref = sampleURL.getRef();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
