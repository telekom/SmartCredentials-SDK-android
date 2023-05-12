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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import de.telekom.smartcredentials.oneclickbusinessclient.OneClickClientConfiguration
import de.telekom.smartcredentials.oneclickbusinessclient.R
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.ColorPrimary
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.LightGrey
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.TextColor

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
@Composable
fun OneClickDialogFragment(
    showDialog: Boolean,
    config: OneClickClientConfiguration,
    onPositiveButtonClicked: () -> Unit,
    onDismissRequest: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismissRequest() }
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .align(Alignment.TopCenter)
                        .padding(top = 45.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 36.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(
                                    top = 69.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                )
                                .align(Alignment.CenterHorizontally),
                            text = stringResource(id = R.string.one_click_dialog_title).uppercase(),
                            style = TextStyle(
                                color = ColorPrimary,
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.teleneooffice_extrabold)),
                                textAlign = TextAlign.Center
                            )
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            text = stringResource(id = R.string.one_click_after_description),
                            style = TextStyle(
                                color = TextColor,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.teleneooffice_medium))
                            )
                        )


                        Button(
                            onClick = onPositiveButtonClicked,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp)
                                .height(40.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = ColorPrimary
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.one_click_dialog_positive_button).uppercase(),
                                style = TextStyle(color = Color.White)
                            )
                        }

                        Button(
                            onClick = { onDismissRequest() },
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 0.dp)
                                .height(height = 40.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White
                            ),
                            elevation = null
                        ) {
                            Text(
                                text = stringResource(id = R.string.one_click_dialog_negative_button).uppercase(),
                                style = TextStyle(color = ColorPrimary)
                            )
                        }


                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 16.dp),
                            text = stringResource(id = R.string.partnership_text, config.clientAppName),
                            style = TextStyle(
                                color = LightGrey,
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.teleneooffice_medium)),
                                textAlign = TextAlign.Center
                            )
                        )

                        Row(
                            modifier = Modifier
                                .wrapContentSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(end = 40.dp)
                                    .width(30.dp)
                                    .height(60.dp),
                                painter = painterResource(id = R.drawable.ic_telekom_magenta),
                                contentDescription = "Icon Telekom"
                            )
                            Image(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                                painter = painterResource(id = config.logoResId),
                                contentDescription = "Icon Odysee"
                            )
                        }
                    }
                }

                Surface(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(90.dp)
                        .align(Alignment.TopCenter),
                    color = ColorPrimary,
                ) {
                    Image(
                        modifier = Modifier
                            .padding(22.dp)
                            .align(Alignment.Center),
                        painter = painterResource(R.drawable.ic_rate),
                        contentDescription = "",
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }

}
