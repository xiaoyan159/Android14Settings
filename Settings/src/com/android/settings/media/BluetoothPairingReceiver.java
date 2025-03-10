/*
 * Copyright 2020 The Android Open Source Project
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

package com.android.settings.media;

import android.app.settings.SettingsEnums;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.cariad.cea.settings.R;
import com.android.settings.bluetooth.BluetoothPairingDetail;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.media.MediaOutputConstants;

/**
 * BroadcastReceiver for handling media output intent
 */
public class BluetoothPairingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(MediaOutputConstants.ACTION_LAUNCH_BLUETOOTH_PAIRING,
                intent.getAction())) {
            context.startActivity(new SubSettingLauncher(context)
                    .setDestination(BluetoothPairingDetail.class.getName())
                    .setTitleRes(R.string.bluetooth_pairing_page_title)
                    .setSourceMetricsCategory(SettingsEnums.BLUETOOTH_PAIRING_RECEIVER)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .toIntent());
        }
    }
}
