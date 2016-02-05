package com.guanchazhe.news.mvp.model.repository.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.utils.Utils;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ranzh on 1/25/2016.
 */
public class ObservableRepoDb {

    private static final String LIST_QUERY = "SELECT * FROM "
            + DbOpenHelper.REPO_TABLE
            + " WHERE "
            + DbOpenHelper.REPO_PAGEINDEX_COLUMN
            + " = ?";


    private final DbOpenHelper dbHelper;
    private final BriteDatabase mBriteDatabase;

    public ObservableRepoDb(Context context) {
        dbHelper = new DbOpenHelper(context);
        mBriteDatabase = SqlBrite.create().wrapDatabaseHelper(dbHelper);
    }

    public Observable<List<News>> getCachedNews(String pageIndex) {
        Observable<List<News>> newslist =
                mBriteDatabase.createQuery(DbOpenHelper.DATABASE_NAME, LIST_QUERY, pageIndex)
//                        .mapToList(News.MAPPER)
                        .map(new Func1<SqlBrite.Query, List<News>>() {
                            @Override
                            public List<News> call(SqlBrite.Query query) {
                                Cursor cursor = query.run();
                                try {

                                    List<String> l= new ArrayList<>();
                                    if (!cursor.moveToNext()) {
                                        throw new AssertionError("No rows");
                                    }

                                    while (cursor.moveToNext()) {
                                        l.add(cursor.getString(3));
                                    }
                                    return null;
                                } finally {
                                    cursor.close();
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

        return newslist;
    }

    public void insertRepoListWithType(List<News> newslist, Constant.NewsType type, int currentpage) {

        for (News news : newslist) {
            news.setPageindex(currentpage);
            mBriteDatabase.insert(DbOpenHelper.REPO_TABLE, news.convertToContentValues());
        }
    }

}
