/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.android.settings.display;

import android.content.Context;

import com.cariad.cea.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.dream.DreamSettings;

public class ScreenSaverPreferenceController extends BasePreferenceController implements
        PreferenceControllerMixin {

    private final boolean mDreamsDisabledByAmbientModeSuppression;

    public ScreenSaverPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);

        mDreamsDisabledByAmbientModeSuppression = context.getResources().getBoolean(
                com.android.internal.R.bool.config_dreamsDisabledByAmbientModeSuppressionConfig);
    }

    @Override
    public int getAvailabilityStatus() {
        return Utils.areDreamsAvailableToCurrentUser(mContext) ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }

    @Override
    public CharSequence getSummary() {
        if (mDreamsDisabledByAmbientModeSuppression
                && AmbientDisplayAlwaysOnPreferenceController.isAodSuppressedByBedtime(mContext)) {
            return mContext.getString(R.string.screensaver_settings_when_to_dream_bedtime);
        } else {
            return DreamSettings.getSummaryTextWithDreamName(mContext);
        }
    }
}
