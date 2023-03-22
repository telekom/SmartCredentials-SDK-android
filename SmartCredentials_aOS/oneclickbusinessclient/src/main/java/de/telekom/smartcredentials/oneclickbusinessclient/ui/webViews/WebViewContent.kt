/*
 * Copyright (c) 2019 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.telekom.smartcredentials.oneclickbusinessclient.ui.webViews

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewContent(
    url: MutableState<String>
) {
    val showLoading = remember { mutableStateOf(false) }
    val showError = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showLoading.value) {
                LoadingWebView()
            } else if (showError.value) {
                ErrorWebView()
            }

            AndroidView(factory = { context ->
                WebView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(
                            view: WebView, url: String,
                            favicon: Bitmap?
                        ) {
                            showLoading.value = true
                        }

                        override fun onPageFinished(
                            view: WebView, url: String
                        ) {
                            showLoading.value = false
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            showError.value = true
                        }
                    }
                    loadUrl(url.value)
                }
            }, update = {
                it.loadUrl(url.value)
            })

        }
    }
}