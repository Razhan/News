package com.guanchazhe.news.views.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.guanchazhe.news.R;
import com.guanchazhe.news.views.activity.ChannelManageActivity;

/**
 * Created by ran.zhang on 1/16/16.
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private Preference mClearCache ;
    private Preference mchannelSetting ;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        mClearCache = findPreference("clearCache");
        mchannelSetting = findPreference("channelSetting");

        mClearCache.setOnPreferenceClickListener(this);
        mchannelSetting.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference == mClearCache) {
            Log.d("清除缓存", "清除缓存");
            return true;
        } else if (preference == mchannelSetting) {
            Intent i = new Intent(getActivity(), ChannelManageActivity.class);
            getActivity().startActivity(i);
            return true;
        }

        return false;
    }

}