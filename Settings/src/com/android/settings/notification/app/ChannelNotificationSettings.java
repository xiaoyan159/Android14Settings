/*
 * Copyright (C) 2016 The Android Open Source Project
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

package com.android.settings.notification.app;

import static com.android.settings.notification.app.ChannelListPreferenceController.ARG_FROM_SETTINGS;

import android.app.settings.SettingsEnums;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.cariad.cea.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.List;

public class ChannelNotificationSettings extends NotificationSettings {
    private static final String TAG = "ChannelSettings";

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.NOTIFICATION_TOPIC_NOTIFICATION;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final PreferenceScreen screen = getPreferenceScreen();
        Bundle args = getArguments();
        // If linking to this screen from an external app, expand settings
        if (screen != null && args != null) {
            if (!args.getBoolean(ARG_FROM_SETTINGS, false)) {
                screen.setInitialExpandedChildrenCount(Integer.MAX_VALUE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mUid < 0 || TextUtils.isEmpty(mPkg) || mPkgInfo == null || mChannel == null) {
            Log.w(TAG, "Missing package or uid or packageinfo or channel");
            finish();
            return;
        }

        getActivity().setTitle(mChannel.getName());

        if (!TextUtils.isEmpty(mChannel.getConversationId()) && !mChannel.isDemoted()) {
            Intent intent = new SubSettingLauncher(mContext)
                    .setDestination(ConversationNotificationSettings.class.getName())
                    .setArguments(getArguments())
                    .setExtras(getIntent() != null ? getIntent().getExtras(): null)
                    .setSourceMetricsCategory(SettingsEnums.NOTIFICATION_TOPIC_NOTIFICATION)
                    .toIntent();
            if (mPreferenceFilter != null) {
                intent.setClass(mContext, ChannelPanelActivity.class);
            }
            startActivity(intent);
            finish();
            return;
        }

        for (NotificationPreferenceController controller : mControllers) {
            controller.onResume(mAppRow, mChannel, mChannelGroup, null, null, mSuspendedAppsAdmin,
                    mPreferenceFilter);
            controller.displayPreference(getPreferenceScreen());
        }
        updatePreferenceStates();
        animatePanel();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (NotificationPreferenceController controller : mControllers) {
            if (controller instanceof PreferenceManager.OnActivityResultListener) {
                ((PreferenceManager.OnActivityResultListener) controller)
                        .onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return  R.xml.channel_notification_settings;
    }

    @Override
    protected List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        mControllers = new ArrayList<>();
        mControllers.add(new HeaderPreferenceController(context, this));
        mControllers.add(new BlockPreferenceController(context, mDependentFieldListener, mBackend));
        mControllers.add(new ImportancePreferenceController(
                context, mDependentFieldListener, mBackend));
        mControllers.add(new MinImportancePreferenceController(
                context, mDependentFieldListener, mBackend));
        mControllers.add(new HighImportancePreferenceController(
                context, mDependentFieldListener, mBackend));
        mControllers.add(new AllowSoundPreferenceController(
                context, mDependentFieldListener, mBackend));
        mControllers.add(new SoundPreferenceController(context, this,
                mDependentFieldListener, mBackend));
        mControllers.add(new VibrationPreferenceController(context, mBackend));
        mControllers.add(new AppLinkPreferenceController(context));
        mControllers.add(new VisibilityPreferenceController(context, new LockPatternUtils(context),
                mBackend));
        mControllers.add(new LightsPreferenceController(context, mBackend));
        mControllers.add(new BadgePreferenceController(context, mBackend));
        mControllers.add(new DndPreferenceController(context, mBackend));
        mControllers.add(new NotificationsOffPreferenceController(context));
        mControllers.add(new ConversationPromotePreferenceController(context, this, mBackend));
        return new ArrayList<>(mControllers);
    }
}
