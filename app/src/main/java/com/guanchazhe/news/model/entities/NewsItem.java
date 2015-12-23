package com.guanchazhe.news.model.entities;

/**
 * Created by ranzh on 12/22/2015.
 */
public class NewsItem {
    private String rowid;

    private String id;

    private String title;

    private String summary;

    private String author;

    private String type;

    private String pic;

    private String horizontalpic;

    private String creationtime;

    public void setRowid(String rowid){
        this.rowid = rowid;
    }

    public String getRowid(){
        return this.rowid;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public String getSummary(){
        return this.summary;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setType(String type){ this.type = type; }

    public String getType(){
        return this.type;
    }

    public void setPic(String pic){
        this.pic = pic;
    }

    public String getPic(){
        return this.pic;
    }

    public void setHorizontalpic(String horizontalpic){
        this.horizontalpic = horizontalpic;
    }

    public String getHorizontalpic(){
        return this.horizontalpic;
    }

    public void setCreationtime(String creationtime){
        this.creationtime = creationtime;
    }

    public String getCreationtime(){
        return this.creationtime;
    }

}