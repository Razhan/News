package com.guanchazhe.news.views.widget;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.guanchazhe.news.mvp.model.entities.Author;
import com.guanchazhe.news.views.activity.CommentaryListActivity;
import com.guanchazhe.news.views.activity.ImageDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

/**
 * Created by ranzh on 1/19/2016.
 */
public class JsOperation {
    Activity mActivity;

    public JsOperation(Activity activity) {
        mActivity = activity;
    }

    @JavascriptInterface
    public void showAuthor(String value) {
        if (value != null || !value.equals(XmlPullParser.NO_NAMESPACE)) {
            try {
                JSONObject json = new JSONObject(value);
                Intent intent = new Intent(mActivity, CommentaryListActivity.class);

                Author author = new Author();

                author.setPic(json.getString("AuthorPic"));
                author.setName(json.getString("Author"));
                author.setSpelling(json.getString("AuthorID"));
                author.setTitle(json.getString("AuthorSummary"));

                intent.putExtra("author_name", author);
                mActivity.startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @JavascriptInterface
    public void showImage(String url) {
        if (url != null || !url.equals(XmlPullParser.NO_NAMESPACE)) {
            Intent intent = new Intent(mActivity, ImageDetailActivity.class);
            intent.putExtra("url", url);
            mActivity.startActivity(intent);
        }
    }

}