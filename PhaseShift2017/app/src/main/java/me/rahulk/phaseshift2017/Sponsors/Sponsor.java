package me.rahulk.phaseshift2017.Sponsors;

/**
 * Created by debugger24 on 12/09/17.
 */

public class Sponsor {
    private String title;
    private String logo;
    private String url;

    public Sponsor(String title, String logo, String url) {
        this.title = title;
        this.logo = logo;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getLogo() {
        return logo;
    }

    public String getUrl() {
        return url;
    }
}
