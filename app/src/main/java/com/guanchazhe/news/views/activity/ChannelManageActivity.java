package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.guanchazhe.news.R;
import com.guanchazhe.news.views.adapter.ChannelListAdapter;
import com.guanchazhe.news.views.listener.ItemTouchHelperListener;
import com.guanchazhe.news.views.listener.OnStartDragListener;
import com.guanchazhe.news.views.widget.RecyclerInsetsDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChannelManageActivity extends AppCompatActivity implements OnStartDragListener {

    @Bind(R.id.toolbar)                 Toolbar toolbar;
    @Bind(R.id.favorite_recycleview)    RecyclerView favoriteRecycleview;
    @Bind(R.id.other_recycleview)       RecyclerView otherRecycleview;

    private ItemTouchHelper mItemTouchHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initToolbar();

        initRecycleView(favoriteRecycleview);
        initRecycleView(otherRecycleview);

    }

    private void initUi() {
        setContentView(R.layout.activity_channel_manage);
        ButterKnife.bind(this);
    }

    private void initToolbar() {
        toolbar.setTitle("栏目设置");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initRecycleView(RecyclerView recyclerView) {

        String[] rowListItem = getResources().getStringArray(R.array.column_name1);

        recyclerView.setLayoutManager(new LinearLayoutManager(ChannelManageActivity.this));
        recyclerView.addItemDecoration(new RecyclerInsetsDecoration(this));

        List<String> list = new ArrayList(Arrays.asList(rowListItem));
        ChannelListAdapter mChannelListAdapter = new ChannelListAdapter(recyclerView, list, false,
                (view, data, position) -> Log.d("ColumnList", (String) data));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mChannelListAdapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelperListener(mChannelListAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

}
