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

package com.android.settings.notification;

import static com.google.common.truth.Truth.assertThat;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.TelephonyManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.DefaultRingtonePreference;
import com.cariad.cea.settings.R;
import com.cariad.cea.settings.RingtonePreference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SoundWorkSettingsControllerTest {

    private static final String KEY_WORK_USE_PERSONAL_SOUNDS = "work_use_personal_sounds";
    private static final String KEY_WORK_PHONE_RINGTONE = "work_ringtone";
    private static final String KEY_WORK_NOTIFICATION_RINGTONE = "work_notification_ringtone";
    private static final String KEY_WORK_ALARM_RINGTONE = "work_alarm_ringtone";

    @Mock
    private Context mContext;
    @Mock
    private PreferenceScreen mScreen;
    @Mock
    private TelephonyManager mTelephonyManager;
    @Mock
    private AudioHelper mAudioHelper;
    @Mock
    private SoundWorkSettings mFragment;

    private SoundWorkSettingsController mController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mContext.getSystemService(Context.TELEPHONY_SERVICE)).thenReturn(mTelephonyManager);
        when(mTelephonyManager.isVoiceCapable()).thenReturn(true);
        when(mFragment.getPreferenceScreen()).thenReturn(mScreen);
        when(mScreen.findPreference(KEY_WORK_USE_PERSONAL_SOUNDS))
                .thenReturn(mock(TwoStatePreference.class));
        when(mScreen.findPreference(KEY_WORK_PHONE_RINGTONE))
                .thenReturn(mock(DefaultRingtonePreference.class));
        when(mScreen.findPreference(KEY_WORK_NOTIFICATION_RINGTONE))
                .thenReturn(mock(DefaultRingtonePreference.class));
        when(mScreen.findPreference(KEY_WORK_ALARM_RINGTONE))
                .thenReturn(mock(DefaultRingtonePreference.class));

        mController = new SoundWorkSettingsController(mContext, mFragment, null, mAudioHelper);
    }

    @Test
    public void isAvailable_managedProfileAndNotSingleVolume_shouldReturnTrue() {
        when(mAudioHelper.getManagedProfileId(nullable(UserManager.class)))
                .thenReturn(UserHandle.myUserId());
        when(mAudioHelper.isSingleVolume()).thenReturn(false);

        assertThat(mController.isAvailable()).isTrue();
    }

    @Test
    public void isAvailable_noManagedProfile_shouldReturnFalse() {
        when(mAudioHelper.getManagedProfileId(nullable(UserManager.class)))
                .thenReturn(UserHandle.USER_NULL);
        when(mAudioHelper.isSingleVolume()).thenReturn(false);

        assertThat(mController.isAvailable()).isFalse();
    }

    @Test
    public void isAvailable_singleVolume_shouldReturnFalse() {
        when(mAudioHelper.getManagedProfileId(nullable(UserManager.class)))
                .thenReturn(UserHandle.myUserId());
        when(mAudioHelper.isSingleVolume()).thenReturn(true);

        assertThat(mController.isAvailable()).isFalse();
    }

    @Test
    public void onPreferenceChange_shouldUpdateSummary() {
        final Preference preference = mock(Preference.class);
        when(preference.getKey()).thenReturn(KEY_WORK_PHONE_RINGTONE);

        mController.onPreferenceChange(preference, "hello");

        verify(preference).setSummary(nullable(String.class));
    }

    @Test
    public void onResume_noVoiceCapability_shouldHidePhoneRingtone() {
        when(mTelephonyManager.isVoiceCapable()).thenReturn(false);
        mController = new SoundWorkSettingsController(mContext, mFragment, null, mAudioHelper);

        when(mAudioHelper.getManagedProfileId(nullable(UserManager.class)))
                .thenReturn(UserHandle.myUserId());
        when(mAudioHelper.isUserUnlocked(nullable(UserManager.class), anyInt())).thenReturn(true);
        when(mAudioHelper.isSingleVolume()).thenReturn(false);
        when(mAudioHelper.createPackageContextAsUser(anyInt())).thenReturn(mContext);

        // Precondition: work profile is available.
        assertThat(mController.isAvailable()).isTrue();

        mController.displayPreference(mScreen);
        mController.onResume();

        verify((Preference) mScreen.findPreference(KEY_WORK_PHONE_RINGTONE)).setVisible(false);
    }

    @Test
    public void onResume_availableButLocked_shouldRedactPreferences() {
        final String notAvailable = "(not available)";
        when(mContext.getString(R.string.managed_profile_not_available_label))
                .thenReturn(notAvailable);

        // Given a device with a managed profile:
        when(mAudioHelper.isSingleVolume()).thenReturn(false);
        when(mAudioHelper.createPackageContextAsUser(anyInt())).thenReturn(mContext);
        when(mAudioHelper.getManagedProfileId(nullable(UserManager.class)))
                .thenReturn(UserHandle.myUserId());
        when(mAudioHelper.isUserUnlocked(nullable(UserManager.class), anyInt())).thenReturn(false);

        // When resumed:
        mController.displayPreference(mScreen);
        mController.onResume();

        // Sound preferences should explain that the profile isn't available yet.
        verify((Preference) mScreen.findPreference(KEY_WORK_PHONE_RINGTONE))
                .setSummary(eq(notAvailable));
        verify((Preference) mScreen.findPreference(KEY_WORK_NOTIFICATION_RINGTONE))
                .setSummary(eq(notAvailable));
        verify((Preference) mScreen.findPreference(KEY_WORK_ALARM_RINGTONE))
                .setSummary(eq(notAvailable));
    }

    @Test
    public void onResume_shouldSetUserIdToPreference() {
        final int managedProfileUserId = 10;
        when(mAudioHelper.getManagedProfileId(nullable(UserManager.class)))
                .thenReturn(managedProfileUserId);
        when(mAudioHelper.isUserUnlocked(nullable(UserManager.class), anyInt())).thenReturn(true);
        when(mAudioHelper.isSingleVolume()).thenReturn(false);
        when(mAudioHelper.createPackageContextAsUser(anyInt())).thenReturn(mContext);

        mController.displayPreference(mScreen);
        mController.onResume();

        verify((RingtonePreference) mScreen.findPreference(KEY_WORK_PHONE_RINGTONE))
                .setUserId(managedProfileUserId);
        verify((RingtonePreference) mScreen.findPreference(KEY_WORK_NOTIFICATION_RINGTONE))
                .setUserId(managedProfileUserId);
        verify((RingtonePreference) mScreen.findPreference(KEY_WORK_ALARM_RINGTONE))
                .setUserId(managedProfileUserId);
    }
}
