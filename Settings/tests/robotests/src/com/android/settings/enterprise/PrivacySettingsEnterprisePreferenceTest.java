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

package com.android.settings.enterprise;

import static com.google.common.truth.Truth.assertThat;

import android.content.Context;
import android.provider.SearchIndexableResource;

import androidx.test.core.app.ApplicationProvider;

import com.cariad.cea.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class PrivacySettingsEnterprisePreferenceTest extends AbsBasePrivacySettingsPreference {

    private Context mContext;
    private PrivacySettingsEnterprisePreference mPrivacySettingsEnterprisePreference;

    @Before
    public void setUp() {
        mContext = ApplicationProvider.getApplicationContext();
        mPrivacySettingsEnterprisePreference = new PrivacySettingsEnterprisePreference(mContext);
    }

    @Test
    public void getPreferenceScreenResId() {
        assertThat(mPrivacySettingsEnterprisePreference.getPreferenceScreenResId())
                .isEqualTo(R.xml.enterprise_privacy_settings);
    }

    @Test
    public void getXmlResourcesToIndex() {
        final List<SearchIndexableResource> searchIndexableResources =
                mPrivacySettingsEnterprisePreference.getXmlResourcesToIndex();

        verifyEnterpriseSearchIndexableResources(searchIndexableResources);
    }

    @Test
    public void getPreferenceControllers() {
        final List<AbstractPreferenceController> controllers =
                mPrivacySettingsEnterprisePreference.createPreferenceControllers(true);

        verifyEnterprisePreferenceControllers(controllers);
    }
}
