package HTTP;

import AEP.Fetch;
import JDBC.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Responder implements Runnable {
    // Variable declaration
    private Socket requestHandler;
    private Scanner requestReader;
    private Scanner pageReader;
    private DataOutputStream pageWriter;
    private PrintWriter printWriter;
    private File web_root = new File(".");
    private String notFound = "WebRoot\\Util\\Error404.html";
    private String HTTPMessage;
    private String requestedURL;
    private String requestedFile;
    
    
    //verbose mode
    boolean verbose = true;

    public Responder(Socket requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try {
            if (verbose) {
                System.out.println("Connecton opened. (" + new Date() + ")");
            }
            //create Scanner to read the file of html
            requestReader = new Scanner(
                    new InputStreamReader(requestHandler.getInputStream()));
            //create file writer to write log file
            File file;
            FileWriter fw = new FileWriter("Logs\\MyLogFile.log", true);
            // create timeStamp
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

            int lineCount = 0;
            // Loop through each line of the HTTP messages to get the HTTP request
            do {
                lineCount++; //This will be used later
                HTTPMessage = requestReader.nextLine();
                if (HTTPMessage.equals("GET / HTTP/1.1") || HTTPMessage.equals("GET /subdir HTTP/1.1")) {
                    HTTPMessage = "GET /Default.html HTTP/1.1";
                } else {
                }

                // Take the 1st line which contains GET /testpage.htm HTTP/1.1
                if (lineCount == 1) {
                    if (HTTPMessage.equals("GET /doSERVICE?Field=1&Field=id&Submit=Run+Service HTTP/1.1")) {
                        requestedFile = HTTPMessage.substring(4, HTTPMessage.indexOf("HTTP/1.1") - 1);
                    } else {
                        requestedFile = "WebRoot\\"
                                + HTTPMessage.substring(5, HTTPMessage.indexOf("HTTP/1.1") - 1); 
                    }
                }
                //trim the gap of string in the requestedFile
                requestedFile = requestedFile.trim(); 
                try {
                    pageReader = new Scanner(new File(requestedFile));
                } catch (FileNotFoundException e) {
                    // if there is a wrong/un-existing website -> go to this error site
                    fileNotFound(printWriter, pageWriter, requestedFile);
                }
            } while (HTTPMessage.length() != 0);
            // write log file
            fw.write(requestedFile + ": " + timeStamp);
            fw.write(System.lineSeparator());
            fw.close();
            //reads the requested file. Loops through the file and outputs it to the console.
            pageReader = new Scanner(new File(requestedFile));
            pageWriter = new DataOutputStream(requestHandler.getOutputStream());
            File filew = new File(web_root, requestedFile);
            int fileLength = (int) filew.length();
            String content = getContentType(requestedFile);
            byte[] fileData = readFileData(filew, fileLength);
            printWriter = new PrintWriter(requestHandler.getOutputStream());
            pageWriter.flush();              
                // send HTTP Headers
                printWriter.println("HTTP/1.1 200 OK");
                printWriter.println("Date: " + new Date());
                printWriter.println("Content-type: " + content);
                printWriter.println("Content-length: " + fileLength);
                printWriter.println();
                // flush character output stream buffer
                printWriter.flush(); 

                pageWriter.write(fileData, 0, fileLength);
                pageWriter.flush();
                
                // if a request string contains the line “doService”
                if(requestedFile.contains("doSERVICE")){
                                        
                    Service s = new SQLSelectService(pageWriter, requestedFile);
                    s.doWork();
                
                }
                       
            
        }catch (FileNotFoundException fnfe) {
            try {
                fileNotFound(printWriter, pageWriter, requestedFile);
            } catch (IOException ioe) {
                System.err.println("Error with file not found exception : " + ioe.getMessage());
            }
            finally { 
                try { pageReader = new Scanner(new File(requestedFile));
                        //Tells the Browser we’re done sending
                        pageReader.close();
                        pageWriter.close();
                        printWriter.close();
                        requestHandler.close();				
                    } catch (Exception e) {
                            System.err.println("Error closing stream : " + e.getMessage());
                    } 
            }
        } catch(IOException e){
            System.out.println(e.toString());
            System.out.println("\n");
            e.printStackTrace();
        } 
        
    }    
    private byte[] readFileData(File file, int fileLength) throws IOException {
            FileInputStream fileIn = null;
            byte[] fileData = new byte[fileLength];

            try {
                    fileIn = new FileInputStream(file);
                    fileIn.read(fileData);
            } finally {
                    if (fileIn != null) 
                            fileIn.close();
            }		
            return fileData;
	}
        // return supported MIME Types
	private String getContentType(String fileRequested) {
		if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
			return "text/html";
		else
			return "text/plain";
	}
        private void fileNotFound(PrintWriter printWriter, DataOutputStream pageWriter, 
            String requestedFile) throws IOException {
            File filew = new File(web_root, notFound);
            pageReader = new Scanner(new File(notFound));
            pageWriter = new DataOutputStream(requestHandler.getOutputStream());
            int fileLength = (int) filew.length();
            String content = "text/html";
            byte[] fileData = readFileData(filew, fileLength);
            printWriter = new PrintWriter(requestHandler.getOutputStream());
            pageWriter.flush();  

            printWriter.println("HTTP/1.1 404 File Not Found");
            printWriter.println("Date: " + new Date());
            printWriter.println("Content-type: " + content);
            printWriter.println("Content-length: " + fileLength);
            printWriter.println();
            // flush character output stream buffer
            printWriter.flush(); 
            
            pageWriter.write(fileData, 0, fileLength);
            pageWriter.flush();

            if (verbose) {
                    System.out.println("File " + requestedFile + " not found");
            }
        }        
}