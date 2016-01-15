package com.guanchazhe.news.mvp.model.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ran.zhang on 1/13/16.
 */
public class Author implements Serializable {

    String pic;

    String name;

    @SerializedName("summary")
    String title;

    String spelling;

    public String getPic() {
        return pic;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }
}
