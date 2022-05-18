package HTTP;

import static HTTP.GUIServer.textArea;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import javax.swing.JOptionPane;
import JDBC.MovieDAO;
import JDBC.MovieProgram;
import java.sql.SQLException;

public class WebServer {
    // Variables
    private ServerSocket requestListener;
    private static int HTTP_PORT;
    private ExecutorService responses;

    // Constructor
    public WebServer() throws IOException {
        HTTP_PORT = Integer.parseInt(JOptionPane.showInputDialog("Enter Server HTTP Port"));
        // create an instance of RequestListener (open a port)
        requestListener = new ServerSocket(HTTP_PORT);
        // create thread pool size 100
        responses = Executors.newFixedThreadPool(100);
    }

    public void start() throws IOException, SQLException {
        GUIServer server = new GUIServer();
        Listener listener = new Listener(textArea);
        responses.execute(listener);
        
        while (true) {            
            System.out.println("Waiting For IE to request a page:");
            // Accept connection and create new responder
            Responder r = new Responder(requestListener.accept());
            System.out.println("Page Requested: Request Header: \n");
            //execute the Responder class
            responses.execute(r);           
            
            //MovieProgram mp = new MovieProgram();
            //mp.run();   
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        // creates instance of Webserver and starts it
        WebServer ws = new WebServer();
        ws.start();
    }
}