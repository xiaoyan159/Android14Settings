/*
 * Copyright (C) 2023 The Android Open Source Project
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
package com.android.settings.biometrics2.ui.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.cariad.cea.settings.R

/**
 * Icon Touch dialog
 */
class FingerprintEnrollEnrollingIconTouchDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        requireActivity().bindFingerprintEnrollEnrollingIconTouchDialog()
}

fun Context.bindFingerprintEnrollEnrollingIconTouchDialog(): AlertDialog =
    AlertDialog.Builder(this, R.style.Theme_AlertDialog)
        .setTitle(R.string.security_settings_fingerprint_enroll_touch_dialog_title)
        .setMessage(R.string.security_settings_fingerprint_enroll_touch_dialog_message)
        .setPositiveButton(R.string.security_settings_fingerprint_enroll_dialog_ok) {
            dialog: DialogInterface?, _: Int -> dialog?.dismiss()
        }
        .create()