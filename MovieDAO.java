
package JDBC;

import java.sql.*;
import java.util.*;

public class MovieDAO {
    //get connection method
    public Connection getConnection(){        
        Connection conn = null;       
        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Movs;","javaApps","java");
            

            //conn = DriverManager.getConnection(connectionUrl);
            return conn;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList getMovies(){ 
        ArrayList movies = new ArrayList();
        Connection conn = getConnection();
        
        ResultSet rs = null;
        try{
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("Select id, title, director from Movies");
            while (rs.next()) {
                Movie m = new Movie(Integer.parseInt(rs.getString(1)),
                                        rs.getString(2),rs.getString(3));
                    movies.add(m);
              }
            conn.close();
        }
        catch(Exception e){
        e.printStackTrace();
        }
        return movies;   
    }
    
    public Movie getMovieById(int id){
        Movie m = new Movie();
        Connection conn = getConnection();
        
        ResultSet rs = null;
        try{
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("Select id, title, director, description from Movies where id="+id);
            
            if(rs.next()){
                m = new Movie(Integer.parseInt(rs.getString(1)),
                        rs.getString(2),rs.getString(3),   
                        rs.getString(4));               
            }
            conn.close();
        }
        catch(Exception e){
        e.printStackTrace();
        }
        return m;
    }
    
     public void updateMovie(Movie m) {
        // Create connection
        Connection conn = getConnection();

        // Sending and receiving sql queries
        try {
            Statement stmt = conn.createStatement();
            String update = "UPDATE Movies SET Title= '"+m.getTitle()+
                    "',Director = '"+m.getDirector()+"', Description ='"+
                    m.getDescription()+ "' Where Id = "+m.getId();
            stmt.executeUpdate(update);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
    public ArrayList<Movie> getMovieByCriteria(String field, String criteria){
        // Setup
        ArrayList<Movie> movies = new ArrayList<>();

        // Create connection
        Connection conn = getConnection();

        // Sending and receiving sql queries
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM MOVIES WHERE "+ field +" = ‘" + criteria + "'");
            while (rs.next()) {
                Movie m = new Movie(Integer.parseInt(rs.getString(1)),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4));
                movies.add(m);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
    
    
    public void getMovieId() throws SQLException{
        // Create a result set
        Connection conn = getConnection();
 
        Statement statement = conn.createStatement();

        ResultSet results = statement.executeQuery("SELECT * FROM Movies");


        // Get resultset metadata

        ResultSetMetaData metadata = results.getMetaData();

        int columnCount = metadata.getColumnCount();


        System.out.println("test_table columns : ");


        // Get the column names; column indices start from 1

        for (int i=1; i<=columnCount; i++) {

          String columnName = metadata.getColumnName(i);

          System.out.println(columnName);
        }
    }
    
}
