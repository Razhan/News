package com.guanchazhe.news.mvp.model.entities;

import java.io.Serializable;

/**
 * Created by ranzh on 12/22/2015.
 */
public class News implements Serializable  {
    public News() {}

    public News(int id, String rowid, String title, String summary, String author, String type,
                String pic, String horizontalpic, String creationtime, String authortitle, String requesttime,
                int pageindex) {

        this.rowid = rowid;
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.author = author;
        this.type = type;
        this.pic = pic;
        this.horizontalpic = horizontalpic;
        this.creationtime = creationtime;
        this.authortitle = authortitle;
        this.requesttime = requesttime;
        this.pageindex = pageindex;
    }

    private int id;
    private String rowid;
    private String title;
    private String summary;
    private String author;
    private String type;
    private String pic;
    private String horizontalpic;
    private String creationtime;
    private String authortitle;
    private String requesttime;
    private int pageindex;

    public String getRequesttime() {
        return requesttime;
    }

    public String getAuthortitle() {
        return authortitle;
    }

    public String getRowid() {
        return rowid;
    }

    public int getId() {
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

    public int getPageindex() {
        return pageindex;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public void setId(int id) {
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

    public void setAuthortitle(String authortitle) {
        this.authortitle = authortitle;
    }

    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime;
    }

    public void setPageindex(int pageindex) {
        this.pageindex = pageindex;
    }

    private static final long STALE_MS = 30 * 1000;

    public boolean isUpToDate() {
        if (requesttime == null) {
            return true;
        }

        return System.currentTimeMillis() - Long.parseLong(requesttime) < STALE_MS;
    }

}