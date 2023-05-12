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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.telekom.smartcredentials.oneclickbusinessclient.R
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.TextColor

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
@Composable
fun ErrorWebView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .padding(top = 48.dp)
                .size(100.dp),
            painter = painterResource(id = R.drawable.ic_wifi_error_magenta),
            contentDescription = "Wifi error",
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(id = R.string.website_error_title).uppercase(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 24.dp),
            style = TextStyle(
                color = TextColor,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.telegrotesknext_bold)),
                textAlign = TextAlign.Center
            )
        )

        Text(
            text = stringResource(id = R.string.website_error_description),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 8.dp),
            style = TextStyle(
                color = TextColor,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.telegrotesknext_regular)),
                textAlign = TextAlign.Center
            )
        )
    }
}