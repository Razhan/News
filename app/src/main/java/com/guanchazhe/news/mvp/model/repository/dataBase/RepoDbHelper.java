package com.guanchazhe.news.mvp.model.repository.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.guanchazhe.news.mvp.Constant;

/**
 * Created by ranzh on 1/25/2016.
 */
public class RepoDbHelper {
    private static final String TAG = "DbHelper";

    private static final String DATABASE_NAME = "NEWS.db";
    private static final int DATABASE_VERSION = 1;


    // Variable to hold the database instance
    protected SQLiteDatabase mDb;
    // Context of the application using the database.
    private final Context mContext;
    // Database open/upgrade helper
    private DbHelper mDbHelper;

    public RepoDbHelper(Context context) {
        mContext = context;
        mDbHelper = new DbHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public RepoDbHelper open() throws SQLException {
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public RepoDbHelper openForRead() throws SQLException {
        mDb = mDbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        mDb.close();
    }

    public static final String ROW_ID = "_id";
    public static final String ROW_TYPE = "type";


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

    public static final String REPO_PAGEINDEX_COLUMN = "pageindex";
    public static final int REPO_PAGEINDEX_COLUMN_POSITION = 12;

    // -------- TABLES CREATION ----------


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
            REPO_REQUESTTIME_COLUMN + " text, " +
            REPO_PAGEINDEX_COLUMN + " integer" +
            ")";


    // ----------------Repo HELPERS --------------------
    public long addRepo (int id, String rowid, String title, String summary, String author, String type,
                         String pic, String horizontalpic, String creationtime, String authortitle,
                         String requesttime, int pageindex) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(REPO_ID_COLUMN, String.valueOf(id));
        contentValues.put(REPO_ROWID_COLUMN, rowid);
        contentValues.put(REPO_TITLE_COLUMN, title);
        contentValues.put(REPO_SUMMARY_COLUMN, summary);
        contentValues.put(REPO_AUTHOR_COLUMN, author);
        contentValues.put(REPO_TYPE_COLUMN, type);
        contentValues.put(REPO_PIC_COLUMN, pic);
        contentValues.put(REPO_HORIZONTALPIC_COLUMN, horizontalpic);
        contentValues.put(REPO_CREATIONTIME_COLUMN, creationtime);
        contentValues.put(REPO_AUTHORTITLE_COLUMN, authortitle);
        contentValues.put(REPO_REQUESTTIME_COLUMN, requesttime);
        contentValues.put(REPO_PAGEINDEX_COLUMN, pageindex);

        return mDb.insert(REPO_TABLE, null, contentValues);
    }

    public boolean removeAllRepo(Constant.NewsType type, int pageindex){

        String whereClause = ROW_TYPE + " = ? AND " + REPO_PAGEINDEX_COLUMN + " = ?";
        String[] whereArgs = new String[] {
                type.toString(),
                String.valueOf(pageindex)
        };

        return mDb.delete(REPO_TABLE, whereClause, whereArgs) > 0;
    }

    public Cursor getAllRepo(Constant.NewsType type, int pageindex){

        String whereClause = ROW_TYPE + " = ? AND " + REPO_PAGEINDEX_COLUMN + " = ?";
        String[] whereArgs = new String[] {
                type.toString(),
                String.valueOf(pageindex)
        };

        return mDb.query(REPO_TABLE, new String[] {
                ROW_ID,
                REPO_ID_COLUMN,
                REPO_ROWID_COLUMN,
                REPO_TITLE_COLUMN,
                REPO_SUMMARY_COLUMN,
                REPO_AUTHOR_COLUMN,
                REPO_TYPE_COLUMN,
                REPO_PIC_COLUMN,
                REPO_HORIZONTALPIC_COLUMN,
                REPO_CREATIONTIME_COLUMN,
                REPO_AUTHORTITLE_COLUMN,
                REPO_REQUESTTIME_COLUMN,
                REPO_PAGEINDEX_COLUMN
        }, whereClause, whereArgs, null, null, null);
    }

    private static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // Called when no database exists in disk and the helper class needs
        // to create a new one.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_REPO_CREATE);

        }

        // Called when there is a database version mismatch meaning that the version
        // of the database on disk needs to be upgraded to the current version.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Log the version upgrade.
            Log.w(TAG, "Upgrading from version " +
                    oldVersion + " to " +
                    newVersion + ", which will destroy all old data");

            // Upgrade the existing database to conform to the new version. Multiple
            // previous versions can be handled by comparing _oldVersion and _newVersion
            // values.

            // The simplest case is to drop the old table and create a new one.
            db.execSQL("DROP TABLE IF EXISTS " + REPO_TABLE + ";");

            // Create a new one.
            onCreate(db);
        }
    }
}
