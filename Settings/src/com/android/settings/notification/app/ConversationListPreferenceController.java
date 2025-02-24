/*
 * Copyright (C) 2020 The Android Open Source Project
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

import android.app.settings.SettingsEnums;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.ConversationChannelWrapper;
import android.text.BidiFormatter;
import android.text.TextUtils;

import androidx.annotation.VisibleForTesting;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.cariad.cea.settings.R;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.AppPreference;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ConversationListPreferenceController extends AbstractPreferenceController {

    private static final String SUMMARY_KEY_SUFFIX = "_summary";
    protected final NotificationBackend mBackend;
    private PreferenceGroup mPreferenceGroup;

    public ConversationListPreferenceController(Context context, NotificationBackend backend) {
        super(context);
        mBackend = backend;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mPreferenceGroup = screen.findPreference(getPreferenceKey());
    }

    /**
     * Updates the conversation list.
     * @return true if this controller has content to display.
     */
    boolean updateList(List<ConversationChannelWrapper> conversations) {
        mPreferenceGroup.setVisible(false);
        mPreferenceGroup.removeAll();
        if (conversations != null) {
            populateConversations(conversations);
        }

        boolean hasContent = mPreferenceGroup.getPreferenceCount() != 0;
        if (hasContent) {
            Preference summaryPref = getSummaryPreference();
            if (summaryPref != null) {
                summaryPref.setKey(getPreferenceKey() + SUMMARY_KEY_SUFFIX);
                mPreferenceGroup.addPreference(summaryPref);
            }
            mPreferenceGroup.setVisible(true);
        }
        return hasContent;
    }

    abstract Preference getSummaryPreference();

    abstract boolean matchesFilter(ConversationChannelWrapper conversation);

    @VisibleForTesting
    void populateConversations(List<ConversationChannelWrapper> conversations) {
        AtomicInteger order = new AtomicInteger(100);
        conversations.stream()
                .filter(conversation -> !conversation.getNotificationChannel().isDemoted()
                        && matchesFilter(conversation))
                .sorted(mConversationComparator)
                .map(this::createConversationPref)
                .forEachOrdered(preference -> {
                    preference.setOrder(order.getAndIncrement());
                    mPreferenceGroup.addPreference(preference);
                });
    }

    private Preference createConversationPref(final ConversationChannelWrapper conversation) {
        AppPreference pref = new AppPreference(mContext);

        pref.setTitle(getTitle(conversation));
        pref.setSummary(getSummary(conversation));
        pref.setIcon(mBackend.getConversationDrawable(mContext, conversation.getShortcutInfo(),
                conversation.getPkg(), conversation.getUid(),
                conversation.getNotificationChannel().isImportantConversation()));
        pref.setKey(conversation.getNotificationChannel().getId());
        pref.setOnPreferenceClickListener(preference -> {
            getSubSettingLauncher(conversation, pref.getTitle()).launch();
            return true;
        });

        return pref;
    }

    CharSequence getSummary(ConversationChannelWrapper conversation) {
        return TextUtils.isEmpty(conversation.getGroupLabel())
                ? conversation.getParentChannelLabel()
                : mContext.getString(R.string.notification_conversation_summary,
                conversation.getParentChannelLabel(), conversation.getGroupLabel());
    }

    CharSequence getTitle(ConversationChannelWrapper conversation) {
        ShortcutInfo si = conversation.getShortcutInfo();
        return si != null
                ? BidiFormatter.getInstance().unicodeWrap(si.getLabel())
                : conversation.getNotificationChannel().getName();
    }

    SubSettingLauncher getSubSettingLauncher(ConversationChannelWrapper conversation,
            CharSequence title) {
        Bundle channelArgs = new Bundle();
        channelArgs.putInt(AppInfoBase.ARG_PACKAGE_UID, conversation.getUid());
        channelArgs.putString(AppInfoBase.ARG_PACKAGE_NAME, conversation.getPkg());
        channelArgs.putString(Settings.EXTRA_CHANNEL_ID,
                conversation.getNotificationChannel().getId());
        channelArgs.putString(Settings.EXTRA_CONVERSATION_ID,
                conversation.getNotificationChannel().getConversationId());

        return new SubSettingLauncher(mContext)
                .setDestination(ChannelNotificationSettings.class.getName())
                .setArguments(channelArgs)
                .setExtras(channelArgs)
                .setUserHandle(UserHandle.getUserHandleForUid(conversation.getUid()))
                .setTitleText(title)
                .setSourceMetricsCategory(SettingsEnums.NOTIFICATION_CONVERSATION_LIST_SETTINGS);
    }

    @VisibleForTesting
    Comparator<ConversationChannelWrapper> mConversationComparator =
            new Comparator<ConversationChannelWrapper>() {
                private final Collator sCollator = Collator.getInstance();
                @Override
                public int compare(ConversationChannelWrapper o1, ConversationChannelWrapper o2) {
                    if (o1.getShortcutInfo() != null && o2.getShortcutInfo() == null) {
                        return -1;
                    }
                    if (o1.getShortcutInfo() == null && o2.getShortcutInfo() != null) {
                        return 1;
                    }
                    if (o1.getShortcutInfo() == null && o2.getShortcutInfo() == null) {
                        return o1.getNotificationChannel().getId().compareTo(
                                o2.getNotificationChannel().getId());
                    }
                    if (o1.getShortcutInfo().getLabel() == null
                            && o2.getShortcutInfo().getLabel() != null) {
                        return 1;
                    }
                    if (o1.getShortcutInfo().getLabel() != null
                            && o2.getShortcutInfo().getLabel() == null) {
                        return -1;
                    }
                    return sCollator.compare(o1.getShortcutInfo().getLabel().toString(),
                            o2.getShortcutInfo().getLabel().toString());
                }
            };
}
