package com.guanchazhe.news.mvp.model.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.guanchazhe.news.mvp.model.repository.dataBase.DB;

import java.io.Serializable;

import rx.functions.Func1;

/**
 * Created by ranzh on 12/22/2015.
 */
public class News implements Serializable  {

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

    public int getPageindex() {
        return pageindex;
    }

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

    public News() {}

    public News(int id, String rowid, String title, String summary, String author, String type,
                String pic, String horizontalpic, String creationtime, String authortitle, String requesttime, int pageindex) {

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

    private static final long STALE_MS = 30 * 1000; // Data is stale after 5 seconds

    public boolean isUpToDate() {
        if (requesttime == null) {
            return true;
        }

        return System.currentTimeMillis() - Long.parseLong(requesttime) < STALE_MS;
    }

    public static final Func1<Cursor, News> MAPPER = (cursor) -> {
        Log.d("getCachedNews", "getCachedNews");

        String rowid = DB.getString(cursor, "rowid");
        int id = DB.getInt(cursor, "id");
        String title = DB.getString(cursor, "title");
        String summary = DB.getString(cursor, "summary");
        String author = DB.getString(cursor, "author");
        String type = DB.getString(cursor, "type");
        String pic = DB.getString(cursor, "pic");
        String horizontalpic = DB.getString(cursor, "horizontalpic");
        String creationtime = DB.getString(cursor, "creationtime");
        String authortitle = DB.getString(cursor, "authortitle");
        String requesttime = DB.getString(cursor, "requesttime");
        int pageindex = DB.getInt(cursor, "pageindex");

        return new News(id, rowid, title, summary, author, type, pic, horizontalpic, creationtime,
                authortitle, requesttime, pageindex);
        };

    public ContentValues convertToContentValues() {

        ContentValues values = new ContentValues();

        values.put("id", String.valueOf(this.id));
        values.put("rowid", this.rowid);
        values.put("title", this.title);
        values.put("summary", this.summary);
        values.put("author", this.author);
        values.put("type", this.type);
        values.put("pic", this.pic);
        values.put("horizontalpic", this.horizontalpic);
        values.put("creationtime", this.creationtime);
        values.put("authortitle", this.authortitle);
        values.put("requesttime", this.requesttime);
        values.put("pageindex", String.valueOf(this.pageindex));

        return values;
    }
}