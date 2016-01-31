package com.guanchazhe.news.views.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.R;
import com.guanchazhe.news.injector.components.DaggerVersionInfoComponent;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.VersionInfo;
import com.guanchazhe.news.mvp.presenters.MainPresenter;
import com.guanchazhe.news.mvp.views.MainView;
import com.guanchazhe.news.views.fragment.ListFragment;
import com.guanchazhe.news.views.adapter.ChannelListAdapter;
import com.guanchazhe.news.views.adapter.FragmentAdapter;
import com.guanchazhe.news.views.widget.RecyclerInsetsDecoration;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {

    private final String YAOWEN = "要闻";
    private final String SHIPING = "时评";
    private final String BINFEN = "缤纷";
    private final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    @Bind(R.id.toolbar)             Toolbar toolBar;
    @Bind(R.id.tabs)                TabLayout tabs;
    @Bind(R.id.viewPager)           ViewPager viewPager;
    @Bind(R.id.drawer_layout)       DrawerLayout mDrawerLayout;
    @Bind(R.id.drawer_recycler)     RecyclerView drawerRV;

    @Inject
    MainPresenter mMainPresenter;
    private VersionInfo mVersionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDependencyInjector();
        initPresenter();
        initDrawerLayout();
        initDrawerRecycleView();
        initTabLayout();
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolbar()   {
        toolBar.setTitle(R.string.app_name);
        setSupportActionBar(toolBar);
    }

    private void initDependencyInjector() {
        NewsApplication avengersApplication = (NewsApplication) getApplication();

        DaggerVersionInfoComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(avengersApplication.getAppComponent())
                .build().inject(this);
    }

    private void initPresenter() {
        mMainPresenter.attachView(this);
        mMainPresenter.onCreate();
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolBar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initDrawerRecycleView() {
        String[] rowListItem = getResources().getStringArray(R.array.channel_name);

        drawerRV.setHasFixedSize(true);
        drawerRV.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        drawerRV.addItemDecoration(new RecyclerInsetsDecoration(this));

        ChannelListAdapter mChannelListAdapter = new ChannelListAdapter(drawerRV, Arrays.asList(rowListItem), false,
                (view, data, position) -> FavoriteChannelActivity.start(this, (String)data));

        drawerRV.setAdapter(mChannelListAdapter);
    }

    private void initTabLayout() {
        FragmentAdapter pagerAdapter =
                new FragmentAdapter(getSupportFragmentManager(), 3);

        pagerAdapter.addFragment(ListFragment.newInstance(Constant.NewsType.YAOWEN,
                Constant.getChannelID(YAOWEN), 1, true), YAOWEN);
        pagerAdapter.addFragment(ListFragment.newInstance(Constant.NewsType.SHIPING,
                Constant.getChannelID(SHIPING), 2, false), SHIPING);
        pagerAdapter.addFragment(ListFragment.newInstance(Constant.NewsType.HUABIAN,
                Constant.getChannelID(BINFEN), 3, false), BINFEN);

        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void showUpdateToast(String res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUpdateDialog(VersionInfo info) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("autoUpdate", true)) {
            return;
        }

        mVersionInfo = info;

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.item_custom_dialog, null);

        final CheckBox cbShowPassword = (CheckBox) alertLayout.findViewById(R.id.cb_ShowPassword);
        final TextView versionNameTV = (TextView) alertLayout.findViewById(R.id.dialog_version_name);
        final TextView updateInfoTV = (TextView) alertLayout.findViewById(R.id.dialog_update_info);

        versionNameTV.setText(mVersionInfo.getVersionShort());
        updateInfoTV.setText(mVersionInfo.getChangelog());
        cbShowPassword.setOnCheckedChangeListener((buttonView, isChecked) ->
                    prefs.edit().putBoolean("autoUpdate", !isChecked).commit()
        );

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getResources().getString(R.string.update));
        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setNegativeButton(getResources().getString(R.string.cancel), null);
        alert.setPositiveButton(getResources().getString(R.string.confirm), (dialog, which) -> isStoragePermissionGranted());

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void startDownload(String url) {

        DownloadManager downloadmanager;
        downloadmanager = (DownloadManager) getSystemService(this.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDescription(getResources().getString(R.string.download_title));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "guanchazhe.apk");
        request.allowScanningByMediaScanner();
        request.setVisibleInDownloadsUi(true);

        downloadmanager.enqueue(request);
    }

    private void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            } else {
                startDownload(mVersionInfo.getDirect_install_url());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload(mVersionInfo.getDirect_install_url());
                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.need_permission),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @OnClick(R.id.authors)
    public void OnAuthorsClick() {
        Intent intent = new Intent(this, AuthorListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.channels)
    public void OnChannelsClick() {
        Intent intent = new Intent(this, FavoriteChannelActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.setting)
    public void OnSettingClick() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.about)
    public void OnAboutClick() {
        AboutActivity.start(this);
    }

    @OnClick(R.id.update)
    public void OnUpdateClick() {
        mMainPresenter.checkUpdate();
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("confirmExit", true)) {
            finish();
            return;
        }

        if (doubleBackToExitPressedOnce) {
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.confirm_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

}
