package com.desire.android.deskalauncher;

import android.support.v4.app.Fragment;

public class DeskaLauncherActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return DeskaLauncherFragment.newInstance();
    }
}
