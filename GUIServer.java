
package HTTP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GUIServer extends JFrame {

    public static JTextArea textArea;
    private static JButton stopButton;
    private static JButton startButton;
    private JPanel chatInputContainer;
    
    public GUIServer() {
        //set title bar
        super("LOG");
        //set dimensions
        setSize(500, 500);
        //determines the layout
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        //add some controls
        textArea = new JTextArea();
        textArea = new JTextArea(3, 20);
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        //add a input containers
        chatInputContainer = new JPanel();
        chatInputContainer.add(textArea);
        chatInputContainer.add(startButton);
        chatInputContainer.add(stopButton);
        stopButton.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        }
        );
        startButton.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    WebServer ws = new WebServer();
                    ws.start();
                } catch (IOException ex) {
                }
            }
        }
        );
        //add component to the layout
        add(chatInputContainer, BorderLayout.SOUTH);
        add(textArea, BorderLayout.CENTER);

        //to make the GUI appear
        setVisible(true);
    }
  
}
