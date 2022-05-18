
package JDBC;

public class Movie {
    //declare variables
    private int id;
    private String title; 
    private String director; 
    private String description;
    
    //contructors
    public Movie(){}
    public Movie(int id, String title, String director){
        this.id = id;
        this.title = title;
        this.director = director;
    }
    public Movie(int id, String title, String director, String description){
        this.id = id;
        this.title = title;
        this.director = director;
        this.description = description;
    }
    
    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setDescription(String description) {    
        this.description = description;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getDescription() {
        return description;
    }
}
