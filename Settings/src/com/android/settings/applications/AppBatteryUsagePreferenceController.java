/*
 * Copyright (C) 2021 The Android Open Source Project
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

package com.android.settings.applications;


import android.content.Context;
import android.permission.PermissionControllerManager;
import android.provider.DeviceConfig;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.cariad.cea.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.google.common.annotations.VisibleForTesting;

import java.util.concurrent.Executor;

/**
 * Preference controller for handling the app battery usage list preference.
 */
public final class AppBatteryUsagePreferenceController extends BasePreferenceController
        implements LifecycleObserver {
    private static final String TAG = "AppBatteryUsagePreferenceController";
    private boolean mEnableAppBatteryUsagePage;

    public AppBatteryUsagePreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        mEnableAppBatteryUsagePage =
                mContext.getResources().getBoolean(R.bool.config_app_battery_usage_list_enabled);
    }

    @Override
    public int getAvailabilityStatus() {
        return mEnableAppBatteryUsagePage ? AVAILABLE : CONDITIONALLY_UNAVAILABLE;
    }
}