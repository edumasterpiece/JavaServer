
package HTTP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JTextArea;
public class Listener implements Runnable {

    private JTextArea textArea;
    private Scanner fileInput;
    ExecutorService respon = Executors.newFixedThreadPool(100); // create a pool for it

    public Listener(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void getData(String aPath) {
        try {
            fileInput = new Scanner(new File(aPath));
            //int index = 0;
            while (fileInput.hasNextLine()) {
                textArea.append(fileInput.nextLine()
                );
                textArea.append("\n");
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("File Not Found!!");
            //fnfe.printStackTrace();
        } catch (Exception e) {
            System.out.println("An unknowm error has occurred:" + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Runnable RunLOG = () -> { // this thread is for runnign the LOG
            while (true) {
                getData("Logs\\MyLogFile.log"); // got my log and read the log
                try {
                    // it will print the record every 10 seconds about the status of the website
                    Thread.sleep(1000); 
                    textArea.setText("");
                } catch (InterruptedException ex) {
                }
            }
        };
        respon.execute(RunLOG); // execute the tasktoRunLOG
    }
}
