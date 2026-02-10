package co.eci.protocols;

import co.eci.protocols.GUI.browser;
import co.eci.protocols.exercices.URLInfo;


public class main {
    public static void main(String[] args) {
        browser browser = new browser();
        browser.setVisible(true);
        URLInfo.GenerateURLInfo("https://docs.oracle.com/javase/tutorial/networking/index.html");
    }

}
