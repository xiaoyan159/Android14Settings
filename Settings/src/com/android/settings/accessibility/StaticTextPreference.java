/*
 * Copyright (C) 2019 The Android Open Source Project
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

import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.cariad.cea.settings.R;

/**
 * A custom {@link android.widget.TextView} preference that removes the title and summary
 * restriction from platform {@link Preference} implementation and the icon location is kept as
 * gravity top instead of center.
 */
public class StaticTextPreference extends Preference {

    StaticTextPreference(Context context) {
        super(context);
        setLayoutResource(R.layout.preference_static_text);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
    }
}
