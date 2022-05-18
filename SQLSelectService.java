
package JDBC;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SQLSelectService extends Service {
    //variable declaration
    private String requestString;
    private MovieDAO dbLayer;
    private ArrayList<Movie> movies;
    private PrintWriter printWriter; 
    
    //This construcotr will be called from the run method of a Responder.  
    //It passes the HTTP request info, and the output object to the service. 
    public SQLSelectService(DataOutputStream responseWriter, String requestString) {
        super(responseWriter);
        this.requestString = requestString;
    }

    public void setRequestString(String requestString) {
        this.requestString = requestString;
    } 
   
    public String getRequestString() {
        return requestString;
    }

        
    @Override
    public void doWork(){
        try {            
            //Creates an instance of MoviesDAO for dbLayer
            MovieDAO dbLayer = new MovieDAO();
            //Executes the query to get an ArrayList of Movies
            String criteria = requestString.substring(0, requestString.indexOf("Field") + 1);
            String fieldName = requestString.substring(requestString.indexOf("Field") + 1, requestString.indexOf("Submit") + 1);
            ArrayList<Movie> movies = dbLayer.getMovieByCriteria(criteria, fieldName);
            //Set up the Web page:
            String htmlResponse = ("<html><head><title>Comp 233, Query");
            htmlResponse += ("</title ></head><body>");
            //Loops through the arrayList writing it to IE using the
            //responseWriter.  You will have to format the Strings with
            //a little HTML.
            responseWriter.flush();
            for (Movie m : movies){
                responseWriter.flush();
                htmlResponse += "ID: " + m.getId();
                responseWriter.flush();
                htmlResponse += "Title: " + m.getTitle();
                responseWriter.flush();
                htmlResponse += "Director: " + m.getDirector();
                responseWriter.flush();
                htmlResponse += "--------------------------";
            }
            responseWriter.flush();
            htmlResponse+= "</body></html>";
            responseWriter.writeBytes(htmlResponse);
        }//Then catch exceptions and close class.
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}



