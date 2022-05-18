
package JDBC;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieProgram {
    //declare variables 
    private ArrayList movies;
    private Movie movie;
    private Scanner input;
    
     public MovieProgram(){
        //set up keyboard input capture
        input = new Scanner(System.in);
     }
     
    //user menu
    public int displayMenu(){
        int choice = 0;
        do{
            System.out.println("1. Display all movies");
            System.out.println("2. Diplay movie by ID");
            System.out.println("3. Update movies by ID");
            System.out.println("4. Exit");
        
            choice = input.nextInt();
            
            if (choice<1 || choice>4){
                System.out.println("Not a valid choice");
            }
        }
        while(choice<1 || choice>4);
        
        return choice;
    }
    
    //procee the choice options as per method
    public void processChoice(int choice) throws SQLException{
        switch(choice){
                case 1:
                    displayAllMovies();
                    break;
                case 2:
                    getMovieById();
                    break;
                case 3:
                    getMovieId();
            }            
    }
    
    //get all movies for display
    public void displayAllMovies(){
        MovieDAO dbl = new MovieDAO();
        movies = dbl.getMovies();
        for(int i=0; i<movies.size(); i++){
            Movie mov = (Movie)movies.get(i);
            System.out.println("Id: " + mov.getId()); 
            System.out.println("Title: " + mov.getTitle());
            System.out.println("Director: " + mov.getDirector());
            System.out.println("-----------------------------------");
        }               
    }
    
    //get movies by user input ID
    public void getMovieById(){ 
        MovieDAO dbl = new MovieDAO();
        //variables declaration
        int movId = 0;        
        //user input
        System.out.println("Enter movie ID: ");
        movId = input.nextInt();
        //get movie by Id peovided by user
        movie = dbl.getMovieById(movId);
        //check for movie not found
        if(movie == null){
            System.out.println("Movie not found! Wrong Id");
        }
        else{
            System.out.println("Id: " + movie.getId()); 
            System.out.println("Title: " + movie.getTitle());
            System.out.println("Director: " + movie.getDirector());
            System.out.println("Description: " + movie.getDescription());
            System.out.println("-----------------------------------");              
        }
    }
    
    //get all movies for display
    public void getMovieId() throws SQLException{
        Scanner multiLine = new Scanner(System.in);
        MovieDAO dbl = new MovieDAO();
        dbl.getMovieId();
//        String s = "";
//        //user input
//        System.out.println("Enter movie ID: ");
//        s = input.next();
//        //user input
//        System.out.println("Enter movie ID: ");
//        s = input.next();
//        movies = dbl.getMovieByCriteria(s, s);
//        for(int i=0; i<movies.size(); i++){
//            Movie mov = (Movie)movies.get(i);
//            System.out.println("Id: " + mov.getId()); 
//            System.out.println("Title: " + mov.getTitle());
//            System.out.println("Director: " + mov.getDirector());
//            System.out.println("-----------------------------------");
//        }               
    }
    
    //updates movies by user input ID
    public void updateMoviebyId(){    
        Scanner multiLine = new Scanner(System.in);
        MovieDAO dbl = new MovieDAO();
        //variables declaration
        int movId = 0;         
        //user input
        System.out.println("Enter movie ID: ");
        movId = input.nextInt();
        //display movie details
        movie = dbl.getMovieById(movId);
        //display old title and prompt for new title from user
        System.out.println("Old Title: "+movie.getTitle());
        System.out.println("New Title: ");
        String newTit = multiLine.nextLine();
        //display old director and prompt for new director from user
        System.out.println("Old D2"
                + "irector: "+movie.getTitle());
        System.out.println("New Director: ");
        String newDir = multiLine.nextLine();
        //display old description and prompt for new description from user
        System.out.println("Old Description: "+movie.getTitle());
        System.out.println("New Description: ");
        String newDesc = multiLine.nextLine();
        
        //set movie updates
        movie.setTitle(newTit);
        movie.setDirector(newDir);
        movie.setDescription(newDesc);
        //upddate movie with the update method
        dbl.updateMovie(movie);
    }
    
    //read lines
    public String getLine(){
        input = new Scanner(System.in);
        String multiline = input.nextLine();
        return multiline;
    }
    
    //run method
    public void run() throws SQLException{
        int choice = -1;
        do{
            choice = displayMenu();
            processChoice(choice);
        }while(choice != 4);
    }

    
    //main method
    public static void main(String[] args) throws SQLException {
        //program instance
        MovieProgram mp = new MovieProgram();
        //call for run method
        mp.run();
    }
}


