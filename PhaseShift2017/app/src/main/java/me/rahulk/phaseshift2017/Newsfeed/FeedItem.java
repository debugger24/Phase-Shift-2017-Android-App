package me.rahulk.phaseshift2017.Newsfeed;

/**
 * Created by debugger24 on 21/06/17.
 */

public class FeedItem {
    private int id;
    private String name, status, image, profilePic, timeStamp, url, customURL;

    public FeedItem() {

    }

    public FeedItem(int id, String name, String image, String status, String profilePic, String timeStamp, String url, String customURL) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.status = status;
        this.profilePic = profilePic;
        this.timeStamp = timeStamp;
        this.url = url;
        this.customURL = customURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImge() {
        return image;
    }

    public void setImge(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCustomURL() {
        return customURL;
    }

    public void setCustomURL(String customURL) {
        this.customURL = url;
    }
}
