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

package com.android.settings.display;

import static android.provider.Settings.Secure.DEVICE_STATE_ROTATION_LOCK_LOCKED;

import static com.google.common.truth.Truth.assertThat;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import android.app.settings.SettingsEnums;
import android.content.Context;
import android.content.res.Resources;

import com.cariad.cea.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class DeviceStateAutoRotateDetailsFragmentTest {
    private static final int FOLDED_STATE = 0;
    private static final int HALF_FOLDED_STATE = 1;
    private static final int UNFOLDED_STATE = 2;
    private static final int REAR_DISPLAY_STATE = 3;

    private final DeviceStateAutoRotateDetailsFragment mFragment =
            spy(new DeviceStateAutoRotateDetailsFragment());
    private final Context mContext = spy(RuntimeEnvironment.application);
    private final Resources mResources = spy(mContext.getResources());

    @Before
    public void setUp() throws Exception {
        when(mContext.getResources()).thenReturn(mResources);
        when(mContext.getApplicationContext()).thenReturn(mContext);
        when(mFragment.getContext()).thenReturn(mContext);
        when(mFragment.getResources()).thenReturn(mResources);
        setUpPostureMappings();
    }

    @Test
    public void getMetricsCategory_returnsAutoRotateSettings() {
        assertThat(mFragment.getMetricsCategory()).isEqualTo(
                SettingsEnums.DISPLAY_DEVICE_STATE_AUTO_ROTATE_SETTINGS);
    }

    @Test
    public void getPreferenceScreenResId_returnsDeviceStateAutoRotationSettings() {
        assertThat(mFragment.getPreferenceScreenResId()).isEqualTo(
                R.xml.device_state_auto_rotate_settings);
    }

    @Test
    public void createPreferenceControllers_settableDeviceStates_returnsDeviceStateControllers() {
        enableDeviceStateSettableRotationStates(
                new String[]{FOLDED_STATE + ":" + DEVICE_STATE_ROTATION_LOCK_LOCKED,
                        UNFOLDED_STATE + ":" + DEVICE_STATE_ROTATION_LOCK_LOCKED},
                new String[]{"Folded", "Unfolded"});

        List<AbstractPreferenceController> preferenceControllers =
                mFragment.createPreferenceControllers(mContext);

        assertThat(preferenceControllers).hasSize(2);
        assertThat(preferenceControllers.get(0)).isInstanceOf(
                DeviceStateAutoRotateSettingController.class);
        assertThat(preferenceControllers.get(1)).isInstanceOf(
                DeviceStateAutoRotateSettingController.class);
    }

    @Test
    public void createPreferenceControllers_noSettableDeviceStates_returnsEmptyList() {
        enableDeviceStateSettableRotationStates(new String[]{}, new String[]{});

        List<AbstractPreferenceController> preferenceControllers =
                mFragment.createPreferenceControllers(mContext);

        assertThat(preferenceControllers).isEmpty();
    }

    private void enableDeviceStateSettableRotationStates(String[] settableStates,
            String[] settableStatesDescriptions) {
        when(mResources.getStringArray(
                com.android.internal.R.array.config_perDeviceStateRotationLockDefaults)).thenReturn(
                settableStates);
        when(mResources.getStringArray(
                R.array.config_settableAutoRotationDeviceStatesDescriptions)).thenReturn(
                settableStatesDescriptions);
        DeviceStateRotationLockSettingsManager.resetInstance();
        DeviceStateRotationLockSettingsManager.getInstance(mContext)
                .resetStateForTesting(mResources);
    }

    private void setUpPostureMappings() {
        when(mResources.getIntArray(
                com.android.internal.R.array.config_foldedDeviceStates)).thenReturn(
                new int[]{FOLDED_STATE});
        when(mResources.getIntArray(
                com.android.internal.R.array.config_halfFoldedDeviceStates)).thenReturn(
                new int[]{HALF_FOLDED_STATE});
        when(mResources.getIntArray(
                com.android.internal.R.array.config_openDeviceStates)).thenReturn(
                new int[]{UNFOLDED_STATE});
        when(mResources.getIntArray(
                com.android.internal.R.array.config_rearDisplayDeviceStates)).thenReturn(
                new int[]{REAR_DISPLAY_STATE});
    }
}
