package site.aanrstudio.apps.katalogfilm.model;

import org.json.JSONObject;

public class Tv {

    private String title;
    private String year;
    private String photo;
    private double vote;
    private int id;

    public Tv(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("name");
            String year = object.getString("first_air_date");
            Double vote = object.getDouble("vote_average");
            String photo = "https://image.tmdb.org/t/p/w185"+object.getString("poster_path");

            this.title = title;
            this.year = year;
            this.photo = photo;
            this.vote = vote;
            this.id = id;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getVote() {
        return vote;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
