package co.eci.protocols.GUI;

import javax.swing.*;
import java.awt.*;

public class browser extends JFrame {
    private JTextField urlField;
    private JButton goButton;
    private JEditorPane displayArea;
    public browser() {
        setTitle("Browser");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        urlField = new JTextField(20);
        goButton = new JButton("Go");
        displayArea = new JEditorPane();

    }

}
