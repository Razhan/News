package com.guanchazhe.news.mvp.model.repository.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;

/**
 * Created by ranzh on 2/4/2016.
 */
public final class DbOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    public static final String DATABASE_NAME = "news.db";

    // -------------- REPO DEFINITIONS ------------
    public static final String REPO_TABLE = "news";

    public static final String REPO_ID_COLUMN = "id";
    public static final int REPO_ID_COLUMN_POSITION = 1;

    public static final String REPO_ROWID_COLUMN = "rowid";
    public static final int REPO_ROWID_COLUMN_POSITION = 2;

    public static final String REPO_TITLE_COLUMN = "title";
    public static final int REPO_TITLE_COLUMN_POSITION = 3;

    public static final String REPO_SUMMARY_COLUMN = "summary";
    public static final int REPO_SUMMARY_COLUMN_POSITION = 4;

    public static final String REPO_AUTHOR_COLUMN = "author";
    public static final int REPO_AUTHOR_COLUMN_POSITION = 5;

    public static final String REPO_TYPE_COLUMN = "type";
    public static final int REPO_TYPE_COLUMN_POSITION = 6;

    public static final String REPO_PIC_COLUMN = "pic";
    public static final int REPO_PIC_COLUMN_POSITION = 7;

    public static final String REPO_HORIZONTALPIC_COLUMN = "horizontalpic";
    public static final int REPO_HORIZONTALPIC_COLUMN_POSITION = 8;

    public static final String REPO_CREATIONTIME_COLUMN = "creationtime";
    public static final int REPO_CREATIONTIME_COLUMN_POSITION = 9;

    public static final String REPO_AUTHORTITLE_COLUMN = "authortitle";
    public static final int REPO_AUTHORTITLE_COLUMN_POSITION = 10;

    public static final String REPO_REQUESTTIME_COLUMN = "requestTime";
    public static final int REPO_REQUESTTIME_COLUMN_POSITION = 11;

    // Repo CREATION
    private static final String DATABASE_REPO_CREATE = "create table " + REPO_TABLE + " (" +
            "_id integer primary key autoincrement, " +
            REPO_ID_COLUMN + " text, " +
            REPO_ROWID_COLUMN + " text, " +
            REPO_TITLE_COLUMN + " text, " +
            REPO_SUMMARY_COLUMN + " text, " +
            REPO_AUTHOR_COLUMN + " text, " +
            REPO_TYPE_COLUMN + " text, " +
            REPO_PIC_COLUMN + " text, " +
            REPO_HORIZONTALPIC_COLUMN + " text, " +
            REPO_CREATIONTIME_COLUMN + " text, " +
            REPO_AUTHORTITLE_COLUMN + " text, " +
            REPO_REQUESTTIME_COLUMN + " text" +
            ")";


    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_REPO_CREATE);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
