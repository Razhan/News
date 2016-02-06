package com.guanchazhe.news.mvp.model.repository.dataBase;

import android.content.Context;
import android.database.Cursor;

import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.News;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ranzh on 1/25/2016.
 */
public class ObservableRepoDb {
    private RepoDbHelper mDbHelper;
    private Constant.NewsType mNewsType;
    private int mPageIndex;

    public ObservableRepoDb(Context c) {
        mDbHelper = new RepoDbHelper(c);
    }

    public Observable<List<News>> getObservable(Constant.NewsType type, int index) {
        mNewsType = type;
        mPageIndex = index;

        return Observable.fromCallable(() -> getAllReposFromDbWithType())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<News> getAllReposFromDbWithType() {
        mDbHelper.openForRead();
        List<News> repos = new ArrayList<>();

        Cursor c = mDbHelper.getAllRepo(mNewsType, mPageIndex);

        if (!c.moveToFirst()) {
            return repos;
        }
        do {
            repos.add(new News(
                    Integer.valueOf(c.getString(RepoDbHelper.REPO_ID_COLUMN_POSITION)),
                    c.getString(RepoDbHelper.REPO_ROWID_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_TITLE_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_SUMMARY_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_AUTHOR_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_TYPE_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_PIC_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_HORIZONTALPIC_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_CREATIONTIME_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_AUTHORTITLE_COLUMN_POSITION),
                    c.getString(RepoDbHelper.REPO_REQUESTTIME_COLUMN_POSITION),
                    Integer.valueOf(c.getString(RepoDbHelper.REPO_PAGEINDEX_COLUMN_POSITION))));
        } while (c.moveToNext());
        c.close();
        mDbHelper.close();
        return repos;
    }

    public void insertRepoList(List<News> repos, Constant.NewsType type, int pageindex) {
        mDbHelper.open();
        mDbHelper.removeAllRepo(type, pageindex);

        String requestTime = Long.toString(System.currentTimeMillis());
        for (News r : repos) {
            mDbHelper.addRepo(
                    r.getId(),
                    r.getRowid(),
                    r.getTitle(),
                    r.getSummary(),
                    r.getAuthor(),
                    type.toString(),
                    r.getPic(),
                    r.getHorizontalpic(),
                    r.getCreationtime(),
                    r.getAuthortitle(),
                    requestTime,
                    pageindex);
        }
        mDbHelper.close();
    }
}
