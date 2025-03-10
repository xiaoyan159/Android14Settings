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

package com.android.settings.biometrics.fingerprint;

import static android.hardware.biometrics.BiometricAuthenticator.TYPE_FINGERPRINT;

import android.app.settings.SettingsEnums;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.cariad.cea.settings.R;

/**
 * Displays parental consent information for fingerprint authentication.
 */
public class FingerprintEnrollParentalConsent extends FingerprintEnrollIntroduction {

    /**
     * List of string resources to log when recording the result of this activity in gms.
     * This must be updated when any strings are added/removed.
     */
    public static final int[] CONSENT_STRING_RESOURCES = new int[] {
            R.string.security_settings_fingerprint_enroll_consent_introduction_title,
            R.string.security_settings_fingerprint_enroll_introduction_consent_message,
            R.string.security_settings_fingerprint_enroll_introduction_footer_title_consent_1,
            R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_2,
            R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_3,
            R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_4,
            R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_5,
            R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDescriptionText(
                R.string.security_settings_fingerprint_enroll_introduction_consent_message);
    }

    @Override
    protected void onNextButtonClick(View view) {
        onConsentResult(true /* granted */);
    }

    @Override
    protected void onSkipButtonClick(View view) {
        onConsentResult(false /* granted */);
    }

    @Override
    protected void onEnrollmentSkipped(@Nullable Intent data) {
        onConsentResult(false /* granted */);
    }

    @Override
    protected void onFinishedEnrolling(@Nullable Intent data) {
        onConsentResult(true /* granted */);
    }

    private void onConsentResult(boolean granted) {
        final Intent result = new Intent();
        result.putExtra(EXTRA_KEY_MODALITY, TYPE_FINGERPRINT);
        setResult(granted ? RESULT_CONSENT_GRANTED : RESULT_CONSENT_DENIED, result);
        finish();
    }

    @Override
    protected boolean onSetOrConfirmCredentials(@Nullable Intent data) {
        // prevent challenge from being generated by default
        return true;
    }

    @StringRes
    @Override
    protected int getFooterTitle1() {
        return R.string.security_settings_fingerprint_enroll_introduction_footer_title_consent_1;
    }

    @StringRes
    @Override
    protected int getFooterMessage2() {
        return R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_2;
    }

    @StringRes
    @Override
    protected int getFooterMessage3() {
        return R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_3;
    }

    @StringRes
    protected int getFooterMessage4() {
        return R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_4;
    }

    @StringRes
    protected int getFooterMessage5() {
        return R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_5;
    }

    @StringRes
    protected int getFooterMessage6() {
        return R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_6;
    }

    @Override
    protected int getHeaderResDefault() {
        return R.string.security_settings_fingerprint_enroll_consent_introduction_title;
    }

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.FINGERPRINT_PARENTAL_CONSENT;
    }


    @Override
    protected void updateDescriptionText() {
        super.updateDescriptionText();
        setDescriptionText(
                R.string.security_settings_fingerprint_enroll_introduction_consent_message);
    }
}
