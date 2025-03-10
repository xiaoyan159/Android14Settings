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

package com.android.settings.homepage.contextualcards;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.cariad.cea.settings.R;

public class ContextualCardFeedbackDialog extends AlertActivity implements
        DialogInterface.OnClickListener {

    public static final String EXTRA_CARD_NAME = "card_name";
    public static final String EXTRA_FEEDBACK_EMAIL = "feedback_email";

    private static final String TAG = "CardFeedbackDialog";
    private static final String SUBJECT = "Settings Contextual Card Feedback - ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final AlertController.AlertParams alertParams = mAlertParams;
        alertParams.mMessage = getText(R.string.contextual_card_feedback_confirm_message);
        alertParams.mPositiveButtonText = getText(R.string.contextual_card_feedback_send);
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonText = getText(R.string.skip_label);

        setupAlert();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        final String cardName = getIntent().getStringExtra(EXTRA_CARD_NAME);
        final String email = getIntent().getStringExtra(EXTRA_FEEDBACK_EMAIL);
        final Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
        intent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT + cardName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Send feedback failed.", e);
        }
        finish();
    }
}
