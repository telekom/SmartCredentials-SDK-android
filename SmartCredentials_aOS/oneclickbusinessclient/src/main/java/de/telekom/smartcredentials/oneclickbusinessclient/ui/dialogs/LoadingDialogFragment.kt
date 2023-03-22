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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import de.telekom.smartcredentials.oneclickbusinessclient.R
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.ColorPrimary
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.LightGrey
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.MagentaColorPrimary

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
@Composable
fun LoadingDialogFragment(
    openDialog: Boolean,
    retrieveInfoInProgress: Boolean,
    retrieveInfoDone: Boolean,
    openPortalDone: Boolean,
    onDismiss: () -> Unit
) {
    if (openDialog) {
        Dialog(onDismissRequest = {}) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(20.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 36.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    CircularProgressIndicator(
                        Modifier
                            .width(48.dp)
                            .height(48.dp),
                        color = MagentaColorPrimary
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = 12.dp),
                        text = stringResource(id = R.string.processing).uppercase(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = MagentaColorPrimary,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.teleneooffice_extrabold))
                        )
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 20.dp)
                            .padding(horizontal = 20.dp),
                        color = LightGrey,
                        thickness = 1.dp
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (retrieveInfoInProgress) {
                            Text(
                                text = stringResource(id = R.string.loading_text_retrieving_transaction_token),
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.teleneooffice_extrabold))
                                )
                            )
                        } else {
                            Text(
                                text = stringResource(id = R.string.loading_text_retrieving_transaction_token),
                                style = TextStyle(
                                    color = LightGrey,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.teleneooffice_extrabold))
                                )
                            )
                        }

                        if (retrieveInfoDone) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_success),
                                contentDescription = ""
                            )
                        } else {
                            if (retrieveInfoInProgress) {
                                Text(
                                    text = stringResource(id = R.string.ellipsize),
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.teleneooffice_extrabold))
                                    )
                                )
                            } else {
                                Text(
                                    text = stringResource(id = R.string.ellipsize),
                                    style = TextStyle(
                                        color = LightGrey,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.teleneooffice_extrabold))
                                    )
                                )
                            }
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 12.dp)
                            .padding(horizontal = 20.dp),
                        color = LightGrey,
                        thickness = 1.dp
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (retrieveInfoDone) {
                            Text(
                                text = stringResource(id = R.string.loading_text_transferring_to_portal),
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.teleneooffice_extrabold))
                                )
                            )
                        } else {
                            Text(
                                text = stringResource(id = R.string.loading_text_transferring_to_portal),
                                style = TextStyle(
                                    color = LightGrey,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.teleneooffice_extrabold))
                                )
                            )
                        }


                        if (openPortalDone) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_success),
                                contentDescription = ""
                            )
                        } else {
                            if (retrieveInfoDone) {
                                Text(
                                    text = stringResource(id = R.string.ellipsize),
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.teleneooffice_extrabold))
                                    )
                                )
                            } else {
                                Text(
                                    text = stringResource(id = R.string.ellipsize),
                                    style = TextStyle(
                                        color = LightGrey,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.teleneooffice_extrabold))
                                    )
                                )
                            }

                        }
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 12.dp)
                            .padding(horizontal = 20.dp),
                        color = LightGrey,
                        thickness = 1.dp
                    )

                    Button(
                        onClick = {
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.70f)
                            .padding(top = 12.dp)
                            .height(height = 40.dp),
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

@Preview
@Composable
fun Preview() {
    LoadingDialogFragment(true, true, true, false, {})
}
