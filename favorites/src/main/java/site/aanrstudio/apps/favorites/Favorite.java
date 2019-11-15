package site.aanrstudio.apps.favorites;

public class Favorite {

    private int id;
    private String TITLE;
    private String YEAR;
    private String PHOTO;
    private double VOTE;
    private String CATEGORY;

    public Favorite(int id, String TITLE, String YEAR, String PHOTO, double VOTE, String CATEGORY) {
        this.id = id;
        this.TITLE = TITLE;
        this.YEAR = YEAR;
        this.PHOTO = PHOTO;
        this.VOTE = VOTE;
        this.CATEGORY = CATEGORY;
    }


    public String getCATEGORY() {
        return CATEGORY;
    }

    public int getId() {
        return id;
    }


    public String getTITLE() {
        return TITLE;
    }


    public String getYEAR() {
        return YEAR;
    }


    public String getPHOTO() {
        return PHOTO;
    }


    public double getVOTE() {
        return VOTE;
    }
}
