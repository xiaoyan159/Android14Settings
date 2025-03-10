/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.android.settings.privacy;

import android.app.settings.SettingsEnums;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableResource;

import com.cariad.cea.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.safetycenter.SafetyCenterManagerWrapper;
import com.android.settings.safetycenter.SafetyCenterUtils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.SearchIndexable;

import java.util.List;

@SearchIndexable
public class PrivacyDashboardFragment extends DashboardFragment {
    private static final String TAG = "PrivacyDashboardFrag";
    private static final String KEY_NOTIFICATION_WORK_PROFILE_NOTIFICATIONS =
            "privacy_lock_screen_work_profile_notifications";

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.TOP_LEVEL_PRIVACY;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        SafetyCenterUtils.replaceEnterpriseStringsForPrivacyEntries(this);
    }

    @Override
    public int getHelpResource() {
        return R.string.help_url_privacy_dashboard;
    }

    @Override
    protected List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, getSettingsLifecycle());
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.privacy_dashboard_settings;
    }

    private static List<AbstractPreferenceController> buildPreferenceControllers(
            Context context, Lifecycle lifecycle) {
        return SafetyCenterUtils.getControllersForAdvancedPrivacy(context, lifecycle);
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.privacy_dashboard_settings) {
                /**
                 * If SafetyCenter is enabled, all of these entries will be in the More Settings
                 * page, and we don't want to index these entries.
                 */
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(
                        Context context, boolean enabled) {
                    // NOTE: This check likely should be moved to the super method. This is done
                    // here to avoid potentially undesired side effects for existing implementors.
                    if (!isPageSearchEnabled(context)) {
                        return null;
                    }
                    return super.getXmlResourcesToIndex(context, enabled);
                }

                @Override
                public List<AbstractPreferenceController> createPreferenceControllers(
                        Context context) {
                    return buildPreferenceControllers(context, null);
                }

                @Override
                public List<String> getNonIndexableKeys(Context context) {
                    final List<String> keys = super.getNonIndexableKeys(context);
                    final int profileUserId =
                            Utils.getManagedProfileId(
                                    UserManager.get(context), UserHandle.myUserId());
                    // If work profile is supported, we should keep the search result.
                    if (profileUserId != UserHandle.USER_NULL) {
                        return keys;
                    }

                    // Otherwise, we should hide the search result.
                    keys.add(KEY_NOTIFICATION_WORK_PROFILE_NOTIFICATIONS);
                    return keys;
                }

                @Override
                protected boolean isPageSearchEnabled(Context context) {
                    return !SafetyCenterManagerWrapper.get().isEnabled(context);
                }
            };
}
