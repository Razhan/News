package com.guanchazhe.news.mvp.model.entities;

import java.io.Serializable;

/**
 * Created by ranzh on 12/22/2015.
 */
public class News implements Serializable {

    private String rowid;
    private String id;
    private String title;
    private String summary;
    private String author;
    private String type;
    private String pic;
    private String horizontalpic;
    private String creationtime;
    private String content;

    public String getRowid() {
        return rowid;
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

    public String getType() {
        return type;
    }

    public String getPic() {
        return pic;
    }

    public String getHorizontalpic() {
        return horizontalpic;
    }

    public String getCreationtime() {
        return creationtime;
    }

    public String getContent() {
        return content;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

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

    public void setType(String type) {
        this.type = type;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setHorizontalpic(String horizontalpic) {
        this.horizontalpic = horizontalpic;
    }

    public void setCreationtime(String creationtime) {
        this.creationtime = creationtime;
    }

    public void setContent(String content) {
        this.content = content;
    }
}