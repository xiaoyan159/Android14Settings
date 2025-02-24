/*
 * Copyright (C) 2024 The Android Open Source Project
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

package com.android.settings.connecteddevice.fastpair;

import static com.google.common.truth.Truth.assertThat;

import android.app.settings.SettingsEnums;

import com.cariad.cea.settings.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class FastPairDeviceDashboardFragmentTest {

    @Rule public final MockitoRule mMockitoRule = MockitoJUnit.rule();
    private FastPairDeviceDashboardFragment mFragment;

    @Before
    public void setUp() {
        mFragment = new FastPairDeviceDashboardFragment();
    }

    @Test
    public void getPreferenceScreenResId_returnsCorrectXml() {
        assertThat(mFragment.getPreferenceScreenResId()).isEqualTo(R.xml.fast_pair_devices);
    }

    @Test
    public void getLogTag_returnsCorrectTag() {
        assertThat(mFragment.getLogTag()).isEqualTo("FastPairDeviceFrag");
    }

    @Test
    public void getMetricsCategory_returnsCorrectCategory() {
        assertThat(mFragment.getMetricsCategory()).isEqualTo(SettingsEnums.FAST_PAIR_DEVICES);
    }

    @Test
    public void getHelpResource_returnsCorrectResource() {
        assertThat(mFragment.getHelpResource())
                .isEqualTo(R.string.help_url_connected_devices_fast_pair_devices);
    }
}
