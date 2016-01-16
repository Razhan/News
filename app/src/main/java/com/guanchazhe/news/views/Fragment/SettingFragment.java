package com.guanchazhe.news.views.Fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.guanchazhe.news.R;

/**
 * Created by ran.zhang on 1/16/16.
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private Preference mClearCache ;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        mClearCache = findPreference("clearCache");
        mClearCache.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference == findPreference("clearCache")) {
            Log.d("清除缓存", "清除缓存");
            return true;
        }
        return false;
    }

}