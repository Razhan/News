package com.guanchazhe.news.mvp.model.repository.dataBase;

import android.content.Context;
import android.database.Cursor;

import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.News;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ranzh on 1/25/2016.
 */
public class ObservableRepoDb {
    private final DbOpenHelper dbHelper;
    private final BriteDatabase mBriteDatabase;
    private Constant.NewsType mNewsType;

    public ObservableRepoDb(Context context) {
        dbHelper = new DbOpenHelper(context);
        mBriteDatabase = SqlBrite.create().wrapDatabaseHelper(dbHelper);
    }

    public Observable<List<News>> getCachedNews(int pageIndex) {

        Observable<List<News>> news =
                mBriteDatabase.createQuery(DbOpenHelper.DATABASE_NAME, TITLE_QUERY, listId)
                        .mapToList(TodoItem.MAPPER)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(adapter));
    }


}
