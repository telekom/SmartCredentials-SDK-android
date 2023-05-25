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
package de.telekom.smartcredentials.oneclickbusinessclient.ui.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import de.telekom.smartcredentials.oneclickbusinessclient.R
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.ColorPrimary
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.MagentaColorPrimary
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.TextColor

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
@Composable
fun RetrieveErrorDialogFragment(
    showDialog: Boolean,
    onDismissRequest: () -> Unit
) {

    if (showDialog) {
        Dialog(onDismissRequest = {
            onDismissRequest()
        }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(),
                shape = RoundedCornerShape(20.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = 16.dp),
                        text = stringResource(id = R.string.error),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = MagentaColorPrimary,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.teleneooffice_extrabold))
                        )
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 22.dp)
                            .padding(horizontal = 16.dp),
                        text = stringResource(id = R.string.invalid_retrieve_error_message),
                        style = TextStyle(
                            color = TextColor,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.teleneooffice_medium))
                        )
                    )

                    Button(
                        onClick = {
                            onDismissRequest()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.70f)
                            .padding(
                                top = 12.dp,
                                bottom = 16.dp
                            )
                            .align(Alignment.CenterHorizontally)
                            .height(height = 50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White
                        ),
                        elevation = null
                    ) {
                        Text(
                            text = stringResource(id = R.string.dismiss).uppercase(),
                            style = TextStyle(color = ColorPrimary)
                        )
                    }

                }
            }
        }
    }

}
