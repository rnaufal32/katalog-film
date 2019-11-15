package site.aanrstudio.apps.katalogfilm.model;

import org.json.JSONObject;

public class Film {

    private String title;
    private String year;
    private String photo;
    private double vote;
    private int id;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getPhoto() {
        return photo;
    }

    public double getVote() {
        return vote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Film(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String year = object.getString("release_date");
            Double vote = object.getDouble("vote_average");
            String photo = "https://image.tmdb.org/t/p/w185"+object.getString("poster_path");

            this.title = title;
            this.year = year;
            this.photo = photo;
            this.id = id;
            this.vote = vote;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
