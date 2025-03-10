/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.wifi.dpp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.cariad.cea.settings.R;
import com.android.settings.SetupWizardUtils;
import com.android.settings.core.InstrumentedActivity;

import com.google.android.setupdesign.util.ThemeHelper;

public abstract class WifiDppBaseActivity extends InstrumentedActivity {
    protected FragmentManager mFragmentManager;

    protected abstract void handleIntent(Intent intent);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        applyTheme();

        setContentView(R.layout.wifi_dpp_activity);
        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        theme.applyStyle(R.style.SetupWizardPartnerResource, true);
        super.onApplyThemeResource(theme, resid, first);
    }

    private void applyTheme() {
        setTheme(SetupWizardUtils.getTheme(this, getIntent()));
        setTheme(R.style.SettingsPreferenceTheme_SetupWizard);
        ThemeHelper.trySetDynamicColor(this);
    }
}
