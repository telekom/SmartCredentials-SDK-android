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

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.telekom.smartcredentials.oneclickbusinessclient.R
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.MagentaColorPrimary
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.TextColor

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
@Composable
fun LoadingWebView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            Modifier
                .width(64.dp)
                .height(64.dp),
            color = MagentaColorPrimary
        )
        Text(
            text = stringResource(id = R.string.loading_text),
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 24.dp),
            style = TextStyle(
                color = TextColor,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.telegrotesknext_medium))
            )
        )
    }
}