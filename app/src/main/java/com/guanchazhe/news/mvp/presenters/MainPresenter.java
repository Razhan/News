package com.guanchazhe.news.mvp.presenters;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.guanchazhe.news.R;
import com.guanchazhe.news.domain.GetVersionInfoUseCase;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.VersionInfo;
import com.guanchazhe.news.mvp.views.MainView;
import com.guanchazhe.news.mvp.views.Views;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by ran.zhang on 1/30/16.
 */
public class MainPresenter implements Presenter{

    private final GetVersionInfoUseCase mGetVersionInfoUseCase;
    private Context mContext;
    private Subscription mVersionInfoSubscription;
    private MainView mMainView;

    private String appID;
    private String appToken;
    private int VersionCode;

    @Inject
    public MainPresenter(Context context, GetVersionInfoUseCase versionInfoUseCase) {
        mGetVersionInfoUseCase = versionInfoUseCase;
        mContext = context;
    }

    @Override
    public void onCreate() {
        getAppInfo();
        sendRequest(Constant.CheckUpdateType.AUTO, appID, appToken);
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop() {}

    @Override
    public void onPause() {
        mVersionInfoSubscription.unsubscribe();
    }

    @Override
    public void attachView(Views v) {
        mMainView = (MainView) v;
    }

    public void checkUpdate() {
        sendRequest(Constant.CheckUpdateType.MANUAL, appID, appToken);
    }

    private void sendRequest(Constant.CheckUpdateType type, String id, String token) {
        if (id == null || id == "" || token == null || token == "") {
            return;
        }

        mVersionInfoSubscription = mGetVersionInfoUseCase.execute(id, token)
                .subscribe(
                        versionInfo -> resultArrived(type, versionInfo),
                        error -> resultError(error)
                );
    }

    private void resultArrived(Constant.CheckUpdateType type, VersionInfo info) {
        getVersion();

        if (info.getVersion() > VersionCode) {
            mMainView.showUpdateDialog(info);
        } else if (type == Constant.CheckUpdateType.MANUAL) {
            mMainView.showUpdateToast(mContext.getResources().getString(R.string.update_success));
        }
    }

    private void resultError(Throwable error) {
        mMainView.showUpdateToast(mContext.getResources().getString(R.string.update_fail));
    }

    private void getAppInfo() {
        try {
            ApplicationInfo appInfo = mContext.getPackageManager()
                    .getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);

            appID = appInfo.metaData.getString("appID");
            appToken = appInfo.metaData.getString("appToken");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getVersion() {
        try {
            PackageInfo packageInfo = mContext.getPackageManager()
                    .getPackageInfo(mContext.getPackageName(), 0);
            VersionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            VersionCode = 100;
        }
    }

}
