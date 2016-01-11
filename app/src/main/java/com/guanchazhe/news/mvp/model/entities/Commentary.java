package com.guanchazhe.news.mvp.model.entities;

/**
 * Created by ranzh on 1/6/2016.
 */
public class Commentary {

    private String id;
    private String title;
    private String summary;
    private String author;
    private String pic;
    private String creationtime;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setCreationtime(String creationtime) {
        this.creationtime = creationtime;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getAuthor() {
        return author;
    }

    public String getPic() {
        return pic;
    }

    public String getCreationtime() {
        return creationtime;
    }
}
