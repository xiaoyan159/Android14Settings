/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.android.settings.enterprise;

import android.content.Context;

import androidx.preference.Preference;

import com.cariad.cea.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactory;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.utils.StringUtil;

public abstract class FailedPasswordWipePreferenceControllerBase
        extends AbstractPreferenceController implements PreferenceControllerMixin {

    protected final EnterprisePrivacyFeatureProvider mFeatureProvider;

    public FailedPasswordWipePreferenceControllerBase(Context context) {
        super(context);
        mFeatureProvider = FeatureFactory.getFeatureFactory()
                .getEnterprisePrivacyFeatureProvider();
    }

    protected abstract int getMaximumFailedPasswordsBeforeWipe();

    @Override
    public void updateState(Preference preference) {
        final int failedPasswordsBeforeWipe = getMaximumFailedPasswordsBeforeWipe();
        preference.setSummary(StringUtil.getIcuPluralsString(mContext, failedPasswordsBeforeWipe,
                R.string.enterprise_privacy_number_failed_password_wipe));
    }

    @Override
    public boolean isAvailable() {
        return getMaximumFailedPasswordsBeforeWipe() > 0;
    }
}
