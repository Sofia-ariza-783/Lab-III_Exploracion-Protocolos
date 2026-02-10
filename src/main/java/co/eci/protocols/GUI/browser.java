package co.eci.protocols.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static co.eci.protocols.exercices.URLInfo.GenerateURLInfo;

public class browser extends JFrame {
    private JTextField urlField;
    private JButton goButton;
    private JEditorPane displayArea;
    private String url;

    public browser() {
        setTitle("Browser");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width/2, screenSize.height/2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        urlField = new JTextField(50);
        goButton = new JButton("Go");
        displayArea = new JEditorPane();

        panel.add(urlField);
        panel.add(goButton);
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        
        goButton.addActionListener(e -> {
            url = urlField.getText();
            try {
                GenerateURLInfo(url);
                String filePath = System.getProperty("user.dir") + "/Archivo.html";
                displayArea.setPage("file:///" + filePath);

            } catch (IOException ex) {
                displayArea.setText("Error loading URL: " + ex.getMessage());
            }
        });

    }



}
