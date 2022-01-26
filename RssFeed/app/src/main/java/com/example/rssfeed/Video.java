package com.example.rssfeed;

public class Video {

    private String title;
    private String picture;
    private String DatePublished;
    private String Description;
    private String link;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDatePublished() {
        return DatePublished;
    }

    public void setDatePublished(String datePublished) {
        DatePublished = datePublished;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
