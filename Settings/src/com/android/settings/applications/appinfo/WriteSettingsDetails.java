/*
 * Copyright (C) 2015 The Android Open Source Project
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
package com.android.settings.applications.appinfo;

import android.app.AppOpsManager;
import android.app.settings.SettingsEnums;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.Preference.OnPreferenceClickListener;
import androidx.preference.TwoStatePreference;

import com.cariad.cea.settings.R;
import com.android.settings.applications.AppInfoWithHeader;
import com.android.settings.applications.AppStateAppOpsBridge.PermissionState;
import com.android.settings.applications.AppStateWriteSettingsBridge;
import com.android.settings.applications.AppStateWriteSettingsBridge.WriteSettingsState;
import com.android.settings.overlay.FeatureFactory;
import com.android.settingslib.applications.ApplicationsState.AppEntry;

public class WriteSettingsDetails extends AppInfoWithHeader implements OnPreferenceChangeListener,
        OnPreferenceClickListener {

    private static final String KEY_APP_OPS_SETTINGS_SWITCH = "app_ops_settings_switch";

    // Use a bridge to get the overlay details but don't initialize it to connect with all state.
    // TODO: Break out this functionality into its own class.
    private AppStateWriteSettingsBridge mAppBridge;
    private AppOpsManager mAppOpsManager;
    private TwoStatePreference mSwitchPref;
    private WriteSettingsState mWriteSettingsState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getActivity();
        mAppBridge = new AppStateWriteSettingsBridge(context, mState, null);
        mAppOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        addPreferencesFromResource(R.xml.write_system_settings_permissions_details);
        mSwitchPref = findPreference(KEY_APP_OPS_SETTINGS_SWITCH);

        mSwitchPref.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mSwitchPref) {
            if (mWriteSettingsState != null && (Boolean) newValue != mWriteSettingsState
                    .isPermissible()) {
                setCanWriteSettings(!mWriteSettingsState.isPermissible());
                refreshUi();
            }
            return true;
        }
        return false;
    }

    private void setCanWriteSettings(boolean newState) {
        logSpecialPermissionChange(newState, mPackageName);
        mAppOpsManager.setMode(AppOpsManager.OP_WRITE_SETTINGS,
                mPackageInfo.applicationInfo.uid, mPackageName, newState
                ? AppOpsManager.MODE_ALLOWED : AppOpsManager.MODE_ERRORED);
    }

    void logSpecialPermissionChange(boolean newState, String packageName) {
        int logCategory = newState ? SettingsEnums.APP_SPECIAL_PERMISSION_SETTINGS_CHANGE_ALLOW
                : SettingsEnums.APP_SPECIAL_PERMISSION_SETTINGS_CHANGE_DENY;
        FeatureFactory.getFeatureFactory().getMetricsFeatureProvider().action(getContext(),
                logCategory, packageName);
    }

    @Override
    protected boolean refreshUi() {
        mWriteSettingsState = mAppBridge.getWriteSettingsInfo(mPackageName,
                mPackageInfo.applicationInfo.uid);

        boolean canWrite = mWriteSettingsState.isPermissible();
        mSwitchPref.setChecked(canWrite);
        // you can't ask a user for a permission you didn't even declare!
        mSwitchPref.setEnabled(mWriteSettingsState.permissionDeclared);

        return true;
    }

    @Override
    protected AlertDialog createDialog(int id, int errorCode) {
        return null;
    }

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.MODIFY_SYSTEM_SETTINGS;
    }

    public static CharSequence getSummary(Context context, AppEntry entry) {
        WriteSettingsState state;
        if (entry.extraInfo instanceof WriteSettingsState) {
            state = (WriteSettingsState) entry.extraInfo;
        } else if (entry.extraInfo instanceof PermissionState) {
            state = new WriteSettingsState((PermissionState) entry.extraInfo);
        } else {
            state = new AppStateWriteSettingsBridge(context, null, null).getWriteSettingsInfo(
                    entry.info.packageName, entry.info.uid);
        }

        return getSummary(context, state);
    }

    public static CharSequence getSummary(Context context, WriteSettingsState writeSettingsState) {
        return context.getString(writeSettingsState.isPermissible()
                ? R.string.app_permission_summary_allowed
                : R.string.app_permission_summary_not_allowed);
    }
}
