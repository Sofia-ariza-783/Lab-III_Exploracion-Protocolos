package co.eci.protocols;

import co.eci.protocols.GUI.browser;

import static co.eci.protocols.exercices.URLInfo.GenerateURLInfo;


public class main {
    public static void main(String[] args) {
        browser browser = new browser();
        browser.setVisible(true);
        GenerateURLInfo("https://docs.oracle.com/javase/tutorial/networking/index.html");
    }

}
