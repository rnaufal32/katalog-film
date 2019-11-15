package site.aanrstudio.apps.katalogfilm.model;

import org.json.JSONObject;

public class Detail {
    private String title;
    private String year;
    private String synopsis;
    private String score;
    private Double vote;
    private String photo;
    private String bg_poster;
    private boolean favorite;
    private String cat;

    public Detail(JSONObject object, String category, boolean favorite) {

        try {
            String title = null, year = null, synopsis = null, score = null, photo = null, bg_poster = null;
            Double vote = 0.0;

            if (category.equals("movie")) {
                title = object.getString("original_title");
                year = object.getString("release_date");
                synopsis = object.getString("overview");
                score = object.getString("popularity");
                vote = object.getDouble("vote_average");
                photo = "https://image.tmdb.org/t/p/w185"+object.getString("poster_path");
                bg_poster = "https://image.tmdb.org/t/p/w500"+object.getString("poster_path");
            } else if (category.equals("tv")) {
                title = object.getString("name");
                year = object.getString("first_air_date");
                synopsis = object.getString("overview");
                score = object.getString("popularity");
                vote = object.getDouble("vote_average");
                photo = "https://image.tmdb.org/t/p/w185"+object.getString("poster_path");
                bg_poster = "https://image.tmdb.org/t/p/w500"+object.getString("backdrop_path");
            }

            this.title = title;
            this.year = year;
            this.synopsis = synopsis;
            this.score = score;
            this.vote = vote;
            this.photo = photo;
            this.bg_poster = bg_poster;
            this.favorite = favorite;
            this.cat = category;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCat() {
        return cat;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getScore() {
        return score;
    }

    public Double getVote() {
        return vote;
    }

    public String getPhoto() {
        return photo;
    }

    public String getBg_poster() {
        return bg_poster;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
