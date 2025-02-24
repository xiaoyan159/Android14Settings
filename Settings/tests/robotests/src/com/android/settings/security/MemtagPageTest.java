/*
 * Copyright (C) 2022 The Android Open Source Project
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

package com.android.settings.security;

import static com.google.common.truth.Truth.assertThat;

import android.app.settings.SettingsEnums;
import android.content.Context;

import com.cariad.cea.settings.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class MemtagPageTest {

    private MemtagPage mMemtagPage;
    private Context mContext;

    @Before
    public void setUp() {
        mMemtagPage = new MemtagPage();
        mContext = RuntimeEnvironment.application;
    }

    @Test
    public void getMetricsCategory_isSETTINGS_MEMTAG_CATEGORY() {
        assertThat(mMemtagPage.getMetricsCategory())
                .isEqualTo(SettingsEnums.SETTINGS_MEMTAG_CATEGORY);
    }

    @Test
    public void getPreferenceScreenResId_isMemtag_page() {
        assertThat(mMemtagPage.getPreferenceScreenResId()).isEqualTo(R.xml.memtag_page);
    }

    @Test
    public void SEARCH_INDEX_DATA_PROVIDERgetPreferenceControllers_isNotEmpty() {
        assertThat(MemtagPage.SEARCH_INDEX_DATA_PROVIDER.getPreferenceControllers(mContext))
                .isNotEmpty();
    }
}
