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

import static com.android.settings.core.BasePreferenceController.AVAILABLE;
import static com.android.settings.core.BasePreferenceController.UNSUPPORTED_ON_DEVICE;

import static com.google.common.truth.Truth.assertThat;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.MatrixCursor;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import com.cariad.cea.settings.R;
import com.android.settings.testutils.shadow.ShadowThreadUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

@SmallTest
@RunWith(RobolectricTestRunner.class)
public class CustomizableLockScreenQuickAffordancesPreferenceControllerTest {

    private static final String KEY = "key";

    @Mock private Context mContext;
    @Mock private ContentResolver mContentResolver;
    @Mock private PackageManager mPackageManager;

    private CustomizableLockScreenQuickAffordancesPreferenceController mUnderTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mContext.getContentResolver()).thenReturn(mContentResolver);
        when(mContext.getResources())
                .thenReturn(ApplicationProvider.getApplicationContext().getResources());
        when(mContext.getPackageManager()).thenReturn(mPackageManager);
        ShadowThreadUtils.setIsMainThread(true);

        mUnderTest = new CustomizableLockScreenQuickAffordancesPreferenceController(mContext, KEY);
    }

    @Test
    public void getAvailabilityStatus_whenFeatureEnabled() {
        setEnabled(/* isWallpaperPickerInstalled= */ true, /* isFeatureEnabled = */ true);

        assertThat(mUnderTest.getAvailabilityStatus()).isEqualTo(AVAILABLE);
    }

    @Test
    public void getAvailabilityStatus_whenWallpaperPickerNotInstalledEnabled() {
        setEnabled(/* isWallpaperPickerInstalled= */ false, /* isFeatureEnabled = */ true);

        assertThat(mUnderTest.getAvailabilityStatus()).isEqualTo(UNSUPPORTED_ON_DEVICE);
    }

    @Test
    public void getAvailabilityStatus_whenFeatureNotEnabled() {
        setEnabled(/* isWallpaperPickerInstalled= */ true, /* isFeatureEnabled = */ false);

        assertThat(mUnderTest.getAvailabilityStatus()).isEqualTo(UNSUPPORTED_ON_DEVICE);
    }

    @Test
    public void displayPreference_click() {
        setSelectedAffordanceNames("one", "two");
        final Preference preference = invokeDisplayPreference();

        final ArgumentCaptor<Preference.OnPreferenceClickListener> clickCaptor =
                ArgumentCaptor.forClass(Preference.OnPreferenceClickListener.class);
        verify(preference).setOnPreferenceClickListener(clickCaptor.capture());

        clickCaptor.getValue().onPreferenceClick(preference);

        final ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify(preference).setOnPreferenceClickListener(clickCaptor.capture());
        verify(mContext).startActivity(intentCaptor.capture());
        assertThat(intentCaptor.getValue().getPackage()).isEqualTo(
                mContext.getString(R.string.config_wallpaper_picker_package));
        assertThat(intentCaptor.getValue().getAction()).isEqualTo(Intent.ACTION_SET_WALLPAPER);
        assertThat(intentCaptor.getValue().getStringExtra(
                CustomizableLockScreenUtils.WALLPAPER_LAUNCH_SOURCE)).isEqualTo(
                        CustomizableLockScreenUtils.LAUNCH_SOURCE_SETTINGS);
        assertThat(intentCaptor.getValue().getStringExtra("destination"))
                .isEqualTo("quick_affordances");
    }

    @Test
    public void getSummary_whenNoneAreSelected() {
        setSelectedAffordanceNames();
        final Preference preference = invokeDisplayPreference();

        verify(preference).setSummary(null);
    }

    @Test
    public void getSummary_whenOneIsSelected() {
        setSelectedAffordanceNames("one");
        final Preference preference = invokeDisplayPreference();

        verify(preference).setSummary("one");
    }

    @Test
    public void getSummary_whenTwoAreSelected() {
        setSelectedAffordanceNames("one", "two");
        final Preference preference = invokeDisplayPreference();

        verify(preference).setSummary("one, two");
    }

    private void setEnabled(boolean isWallpaperPickerInstalled, boolean isFeatureEnabled) {
        if (isWallpaperPickerInstalled) {
            final ResolveInfo resolveInfo = new ResolveInfo();
            final ActivityInfo activityInfo = new ActivityInfo();
            final ApplicationInfo applicationInfo = new ApplicationInfo();
            applicationInfo.packageName = "com.fake.name";
            activityInfo.applicationInfo = applicationInfo;
            activityInfo.name = "someName";
            resolveInfo.activityInfo = activityInfo;
            when(mPackageManager.resolveActivity(any(), anyInt())).thenReturn(resolveInfo);
        } else {
            when(mPackageManager.resolveActivity(any(), anyInt())).thenReturn(null);
        }

        final MatrixCursor cursor = new MatrixCursor(
                new String[] {
                        CustomizableLockScreenUtils.NAME,
                        CustomizableLockScreenUtils.VALUE
                });
        cursor.addRow(
                new Object[] {
                    CustomizableLockScreenUtils.ENABLED_FLAG, isFeatureEnabled ? 1 : 0
                });
        when(
                mContentResolver.query(
                        CustomizableLockScreenUtils.FLAGS_URI, null, null, null))
                .thenReturn(cursor);
    }

    private void setSelectedAffordanceNames(String... affordanceNames) {
        final MatrixCursor cursor = new MatrixCursor(
                new String[] { CustomizableLockScreenUtils.AFFORDANCE_NAME });
        for (final String name : affordanceNames) {
            cursor.addRow(new Object[] { name });
        }

        when(
                mContentResolver.query(
                        CustomizableLockScreenUtils.SELECTIONS_URI, null, null, null))
                .thenReturn(cursor);
    }

    private Preference invokeDisplayPreference() {
        final PreferenceScreen screen = mock(PreferenceScreen.class);
        final Preference preference = mock(Preference.class);
        when(screen.findPreference(KEY)).thenReturn(preference);
        mUnderTest.displayPreference(screen);
        return preference;
    }
}
