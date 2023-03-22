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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.telekom.smartcredentials.oneclickbusinessclient.R
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.White90

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
@Composable
fun TopBar(
    title: String,
    backgroundColor: Color,
    onClose: () -> Unit
) {
    TopAppBar(
        elevation = 0.dp,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.telegrotesknext_bold))
                )
            )
        },
        backgroundColor = backgroundColor,
        navigationIcon = {
            IconButton(
                onClick = {},
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_telekom_white),
                    null,
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {
                onClose()
            }) {
                Icon(
                    painterResource(id = R.drawable.ic_close),
                    null,
                    tint = White90
                )
            }
        })
}