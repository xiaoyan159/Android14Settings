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

package com.android.settings.accessibility;

import static com.google.common.truth.Truth.assertThat;

import android.app.settings.SettingsEnums;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.cariad.cea.settings.R;
import com.android.settings.testutils.XmlTestUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

/** Tests for {@link ShortcutsSettingsFragment}. */
@RunWith(RobolectricTestRunner.class)
public class ShortcutsSettingsFragmentTest {

    private final Context mContext = ApplicationProvider.getApplicationContext();
    private ShortcutsSettingsFragment mFragment;

    @Before
    public void setUp() {
        mFragment = new ShortcutsSettingsFragment();
    }

    @Test
    public void getMetricsCategory_returnsCorrectCategory() {
        assertThat(mFragment.getMetricsCategory()).isEqualTo(
                SettingsEnums.ACCESSIBILITY_SHORTCUTS_SETTINGS);
    }

    @Test
    public void getPreferenceScreenResId_returnsCorrectXml() {
        assertThat(mFragment.getPreferenceScreenResId()).isEqualTo(
                R.xml.accessibility_shortcuts_settings);
    }

    @Test
    public void getLogTag_returnsCorrectTag() {
        assertThat(mFragment.getLogTag()).isEqualTo("ShortcutsSettingsFragment");
    }

    @Test
    public void getNonIndexableKeys_existInXmlLayout() {
        final List<String> niks = ShortcutsSettingsFragment.SEARCH_INDEX_DATA_PROVIDER
                .getNonIndexableKeys(mContext);
        final List<String> keys =
                XmlTestUtils.getKeysFromPreferenceXml(mContext,
                        R.xml.accessibility_shortcuts_settings);

        assertThat(keys).containsAtLeastElementsIn(niks);
    }
}
