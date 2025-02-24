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

package com.android.settings.accessibility;

import static com.android.settings.accessibility.AutoclickUtils.MAX_AUTOCLICK_DELAY_MS;
import static com.android.settings.accessibility.AutoclickUtils.MIN_AUTOCLICK_DELAY_MS;

import static com.google.common.truth.Truth.assertThat;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.cariad.cea.settings.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/** Tests for {@link AutoclickUtils}. */
@RunWith(RobolectricTestRunner.class)
public final class AutoclickUtilsTest {

    private final Context mContext = ApplicationProvider.getApplicationContext();

    @Test
    public void getAutoclickDelaySummary_minDelay_shouldReturnOnSummary() {
        final CharSequence summary = AutoclickUtils.getAutoclickDelaySummary(
                mContext, R.string.accessibilty_autoclick_delay_unit_second,
                MIN_AUTOCLICK_DELAY_MS);
        assertThat(summary.toString()).isEqualTo("0.2 seconds");
    }

    @Test
    public void getAutoclickDelaySummary_maxDelay_shouldReturnOnSummary() {
        final CharSequence summary = AutoclickUtils.getAutoclickDelaySummary(
                mContext, R.string.accessibilty_autoclick_delay_unit_second,
                MAX_AUTOCLICK_DELAY_MS);
        assertThat(summary.toString()).isEqualTo("1 second");
    }
}
