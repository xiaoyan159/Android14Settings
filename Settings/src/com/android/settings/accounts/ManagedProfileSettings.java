/*
 * Copyright (C) 2016 The Android Open Source Project
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

package com.android.settings.accounts;

import static android.app.admin.DevicePolicyResources.Strings.Settings.CROSS_PROFILE_CALENDAR_SUMMARY;
import static android.app.admin.DevicePolicyResources.Strings.Settings.CROSS_PROFILE_CALENDAR_TITLE;
import static android.app.admin.DevicePolicyResources.Strings.Settings.MANAGED_PROFILE_SETTINGS_TITLE;
import static android.app.admin.DevicePolicyResources.Strings.Settings.WORK_PROFILE_CONTACT_SEARCH_SUMMARY;
import static android.app.admin.DevicePolicyResources.Strings.Settings.WORK_PROFILE_CONTACT_SEARCH_TITLE;
import static android.app.admin.DevicePolicyResources.Strings.Settings.WORK_PROFILE_SETTING;

import android.app.settings.SettingsEnums;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableResource;
import android.util.Log;

import com.cariad.cea.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

import java.util.ArrayList;
import java.util.List;

/**
 * Setting page for managed profile.
 * FIXME: It currently assumes there is only one managed profile.
 */
@SearchIndexable
public class ManagedProfileSettings extends DashboardFragment {

    private UserManager mUserManager;
    private UserHandle mManagedUser;

    private ManagedProfileBroadcastReceiver mManagedProfileBroadcastReceiver;

    private static final String TAG = "ManagedProfileSettings";

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.managed_profile_settings;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUserManager = (UserManager) getSystemService(Context.USER_SERVICE);
        mManagedUser = getManagedUserFromArgument();

        if (mManagedUser == null) {
            getActivity().finish();
        }
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mManagedProfileBroadcastReceiver = new ManagedProfileBroadcastReceiver();
        mManagedProfileBroadcastReceiver.register(getActivity());
        replaceEnterprisePreferenceScreenTitle(
                MANAGED_PROFILE_SETTINGS_TITLE, R.string.managed_profile_settings_title);
        replaceEnterpriseStringTitle("work_mode",
                WORK_PROFILE_SETTING, R.string.work_mode_label);
        replaceEnterpriseStringTitle("contacts_search",
                WORK_PROFILE_CONTACT_SEARCH_TITLE, R.string.managed_profile_contact_search_title);
        replaceEnterpriseStringSummary("contacts_search",
                WORK_PROFILE_CONTACT_SEARCH_SUMMARY,
                R.string.managed_profile_contact_search_summary);
        replaceEnterpriseStringTitle("cross_profile_calendar",
                CROSS_PROFILE_CALENDAR_TITLE, R.string.cross_profile_calendar_title);
        replaceEnterpriseStringSummary("cross_profile_calendar",
                CROSS_PROFILE_CALENDAR_SUMMARY, R.string.cross_profile_calendar_summary);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mManagedProfileBroadcastReceiver != null) {
            mManagedProfileBroadcastReceiver.unregister(getActivity());
        }
    }

    private UserHandle getManagedUserFromArgument() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            UserHandle userHandle = arguments.getParcelable(Intent.EXTRA_USER);
            if (userHandle != null) {
                if (mUserManager.isManagedProfile(userHandle.getIdentifier())) {
                    return userHandle;
                }
            }
        }
        // Return default managed profile for the current user.
        return Utils.getManagedProfile(mUserManager);
    }

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.ACCOUNTS_WORK_PROFILE_SETTINGS;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(Context context,
                        boolean enabled) {
                    final ArrayList<SearchIndexableResource> result = new ArrayList<>();
                    final SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.managed_profile_settings;
                    result.add(sir);
                    return result;
                }
                @Override
                protected boolean isPageSearchEnabled(Context context) {
                    UserManager userManager = context.getSystemService(UserManager.class);
                    UserHandle managedUser = Utils.getManagedProfile(userManager);
                    return managedUser != null;
                }

            };

    private class ManagedProfileBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            final String action = intent.getAction();
            Log.v(TAG, "Received broadcast: " + action);
            if (Intent.ACTION_MANAGED_PROFILE_REMOVED.equals(action)) {
                if (intent.getIntExtra(Intent.EXTRA_USER_HANDLE,
                        UserHandle.USER_NULL) == mManagedUser.getIdentifier()) {
                    getActivity().finish();
                }
                return;
            }

            Log.w(TAG, "Cannot handle received broadcast: " + intent.getAction());
        }

        public void register(Context context) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_MANAGED_PROFILE_REMOVED);
            context.registerReceiver(this, intentFilter);
        }

        public void unregister(Context context) {
            context.unregisterReceiver(this);
        }
    }
}
