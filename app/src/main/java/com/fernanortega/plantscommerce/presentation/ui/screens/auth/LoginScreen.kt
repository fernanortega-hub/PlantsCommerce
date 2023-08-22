package com.fernanortega.plantscommerce.presentation.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.fernanortega.plantscommerce.R
import com.fernanortega.plantscommerce.presentation.ui.theme.PlantsCommerceTheme
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.LoginEvent
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.LoginUiState
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    isCompactScreen: Boolean,
    isInLandscape: Boolean,
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    onRegisterNavigate: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .widthIn(max = 600.dp)
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .then(
                    if (isInLandscape) Modifier.fillMaxHeight() else Modifier
                ),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(id = R.mipmap.top_image_login),
                contentDescription = null,
                modifier = Modifier
                    .align(
                        alignment = if (isInLandscape) Alignment.CenterHorizontally else Alignment.End
                    )
                    .size(
                        150.dp
                    )
            )

            Text(
                text = stringResource(id = R.string.sign_in),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(start = if (isCompactScreen) 16.dp else 32.dp)
                    .align(
                        alignment = if (isInLandscape) Alignment.CenterHorizontally else Alignment.Start
                    )
            )

            if(isInLandscape) {
                Image(
                    painter = painterResource(id = R.mipmap.bottom_image_login),
                    contentDescription = null,
                    modifier = Modifier
                        .align(
                            alignment = Alignment.Start
                        )
                        .absoluteOffset {
                            IntOffset(0, 16)
                        }
                        .size(
                            if (isCompactScreen) 150.dp else 200.dp
                        )
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(
                    horizontal = if(isCompactScreen.not()) 32.dp else 0.dp
                )
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(
                space = if (isCompactScreen) 12.dp else 16.dp,
                alignment = if(isInLandscape) Alignment.CenterVertically else Alignment.Top
            )
        ) {
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onEvent(LoginEvent.OnEmailChange(it)) },
                isError = uiState.emailError,
                label = {
                    Text(
                        text = stringResource(id = R.string.email_label)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.email_example_placeholder)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { onEvent(LoginEvent.OnPasswordChange(it)) },
                isError = uiState.passwordError,
                visualTransformation = PasswordVisualTransformation(),
                label = {
                    Text(
                        text = stringResource(id = R.string.password_label)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.password_placeholder)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Button(
                onClick = { onEvent(LoginEvent.OnLoginClick) },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (uiState.isUiLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Text(
                    text = if (uiState.isUiLoading) stringResource(id = R.string.signing_in)
                    else stringResource(id = R.string.sign_in)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = stringResource(id = R.string.not_have_an_account_label),
                    style = MaterialTheme.typography.labelLarge
                )

                Divider(
                    modifier = Modifier
                        .weight(1f)
                )
            }

            OutlinedButton(
                onClick = onRegisterNavigate,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.sign_out)
                )
            }

            if(isInLandscape.not()) {
                Image(
                    painter = painterResource(id = R.mipmap.bottom_image_login),
                    contentDescription = null,
                    modifier = Modifier
                        .absoluteOffset {
                            IntOffset(x = -64, y = density.roundToInt())
                        }
                        .size(
                            200.dp
                        )
                )
            }
        }
    }
}

@Preview(device = "spec:width=411.4dp,height=731.4dp")
@Composable
private fun LoginCompactPreview() {
    PlantsCommerceTheme {
        Surface {
            LoginScreen(
                uiState = LoginUiState(),
                isCompactScreen = true,
                onEvent = { /*TODO*/ },
                onRegisterNavigate = { },
                isInLandscape = false
            )
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun LoginMediumPreview() {
    PlantsCommerceTheme {
        Surface {
            LoginScreen(
                uiState = LoginUiState(),
                onEvent = { /*TODO*/ },
                onRegisterNavigate = { },
                isCompactScreen = false,
                isInLandscape = false
            )
        }
    }
}