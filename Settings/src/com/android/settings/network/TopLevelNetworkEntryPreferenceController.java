/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.android.settings.network;

import android.content.Context;
import android.text.BidiFormatter;

import com.cariad.cea.settings.R;
import com.android.settings.Utils;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.core.BasePreferenceController;

public class TopLevelNetworkEntryPreferenceController extends BasePreferenceController {

    private final MobileNetworkPreferenceController mMobileNetworkPreferenceController;

    public TopLevelNetworkEntryPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        mMobileNetworkPreferenceController = new MobileNetworkPreferenceController(mContext);
    }

    @Override
    public int getAvailabilityStatus() {
        // TODO(b/281597506): Update the ActivityEmbeddingUtils.isEmbeddingActivityEnabled
        //   while getting the new API.
        return (Utils.isDemoUser(mContext)
            && !ActivityEmbeddingUtils.isEmbeddingActivityEnabled(mContext))
                ? UNSUPPORTED_ON_DEVICE : AVAILABLE;
    }

    @Override
    public CharSequence getSummary() {
        if (mMobileNetworkPreferenceController.isAvailable()) {
            return BidiFormatter.getInstance()
                    .unicodeWrap(mContext.getString(R.string.network_dashboard_summary_mobile));
        } else {
            return BidiFormatter.getInstance()
                    .unicodeWrap(mContext.getString(R.string.network_dashboard_summary_no_mobile));
        }
    }
}
