package com.guanchazhe.news.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

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
            Toast.makeText(getActivity(), R.string.clear_cache,
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (preference == mchannelSetting) {
            Intent i = new Intent(getActivity(), ChannelManageActivity.class);
            getActivity().startActivity(i);
            return true;
        }
        return false;
    }

}