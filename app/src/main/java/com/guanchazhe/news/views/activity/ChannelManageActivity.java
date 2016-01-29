package com.guanchazhe.news.views.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.views.adapter.ChannelListAdapter;
import com.guanchazhe.news.views.listener.ItemTouchHelperListener;
import com.guanchazhe.news.views.listener.RecycleViewAdapterListener;
import com.guanchazhe.news.views.widget.MyCustomLayoutManager;
import com.guanchazhe.news.views.widget.RecyclerInsetsDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChannelManageActivity extends BaseActivity implements RecycleViewAdapterListener<String> {

    @Bind(R.id.toolbar)                     Toolbar toolBar;
    @Bind(R.id.favorite_recycleview)        RecyclerView favoriteRV;
    @Bind(R.id.other_recycleview)           RecyclerView otherRV;

    private ItemTouchHelper mItemTouchHelper;

    private List<String> favoriteChannels;
    private List<String> otherChannels;

    private ChannelListAdapter favoriteAdapter;
    private ChannelListAdapter otherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadChannels();
        favoriteAdapter = initRecycleView(favoriteRV, favoriteChannels);
        otherAdapter = initRecycleView(otherRV, otherChannels);
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_channel_manage);
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolbar() {
        toolBar.setTitle(R.string.channel_manage);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private ChannelListAdapter initRecycleView(RecyclerView recyclerView, List<String> dataList) {

        recyclerView.setLayoutManager(new MyCustomLayoutManager(ChannelManageActivity.this));
        recyclerView.addItemDecoration(new RecyclerInsetsDecoration(this));

        ChannelListAdapter adapter = new ChannelListAdapter(recyclerView, dataList, false,
                (view, data, position) -> Log.d("ColumnList", (String) data));
        adapter.setListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelperListener(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        return adapter;
    }

    private void loadChannels() {

        String[] channels = getResources().getStringArray(R.array.channel_name);
        List<String> allChannels = new ArrayList(Arrays.asList(channels));

        SharedPreferences prefs = getSharedPreferences(Constant.SHAREDPREFERENCE, Context.MODE_PRIVATE);
        String myChannels = prefs.getString(Constant.FAVORITECHANNELS, null);

        if (myChannels == null) {
            favoriteChannels = Constant.getDefaultChannels();
        } else {
            favoriteChannels = new Gson().fromJson(myChannels,
                    new TypeToken<List<String>>() {}.getType());
        }

        if (allChannels.removeAll(favoriteChannels)) {
            otherChannels = allChannels;
        }
    }

    @Override
    public void afterItemRemoved(String item) {

        if (favoriteAdapter.getItems().contains(item)) {
            otherAdapter.getItems().add(0, item);
            otherAdapter.notifyItemInserted(0);
            otherRV.smoothScrollToPosition(0);

        } else {
            favoriteAdapter.getItems().add(0, item);
            favoriteAdapter.notifyItemInserted(0);
            favoriteRV.smoothScrollToPosition(0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences(Constant.SHAREDPREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(Constant.FAVORITECHANNELS, new Gson().toJson(favoriteChannels));
        editor.commit();
    }
}
