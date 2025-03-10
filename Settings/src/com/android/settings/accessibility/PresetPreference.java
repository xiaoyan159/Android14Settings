/*
 * Copyright (C) 2013 The Android Open Source Project
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
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.CaptioningManager.CaptionStyle;
import android.widget.TextView;

import com.android.internal.widget.SubtitleView;
import com.cariad.cea.settings.R;

/** Grid preference that allows the user to pick a captioning preset type. */
public class PresetPreference extends ListDialogPreference {

    private static final float DEFAULT_FONT_SIZE = 32f;
    private final CaptionHelper mCaptionHelper;

    public PresetPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCaptionHelper = new CaptionHelper(context);

        setDialogLayoutResource(R.layout.grid_picker_dialog);
        setListItemLayoutResource(R.layout.preset_picker_item);
    }

    @Override
    public boolean shouldDisableDependents() {
        return getValue() != CaptionStyle.PRESET_CUSTOM
                || super.shouldDisableDependents();
    }

    @Override
    protected void onBindListItem(View view, int index) {
        final View previewViewport = view.findViewById(R.id.preview_viewport);
        final SubtitleView previewText = view.findViewById(R.id.preview);
        final int value = getValueAt(index);
        mCaptionHelper.applyCaptionProperties(previewText, previewViewport, value);

        final float density = getContext().getResources().getDisplayMetrics().density;
        previewText.setTextSize(DEFAULT_FONT_SIZE * density);

        final CharSequence title = getTitleAt(index);
        if (title != null) {
            final TextView summary = view.findViewById(R.id.summary);
            summary.setText(title);
        }
    }
}
