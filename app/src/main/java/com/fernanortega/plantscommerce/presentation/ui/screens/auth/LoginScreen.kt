package com.fernanortega.plantscommerce.presentation.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atMost
import com.fernanortega.plantscommerce.R
import com.fernanortega.plantscommerce.presentation.ui.components.RowIconTextField
import com.fernanortega.plantscommerce.presentation.ui.theme.PlantsCommerceTheme
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.LoginEvent
import com.fernanortega.plantscommerce.presentation.ui.viewmodels.LoginUiState

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
    ConstraintLayout(
        modifier = modifier
    ) {
        val (header, body, plant) = createRefs()

        val startGuideline = createGuidelineFromStart(if (isCompactScreen) 16.dp else 32.dp)
        val endGuideline = createGuidelineFromEnd(if (isCompactScreen) 16.dp else 32.dp)

        Column(
            modifier = Modifier
                .constrainAs(header) {
                    if (!isInLandscape) {
                        top.linkTo(parent.top)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    } else {
                        top.linkTo(parent.top)
                        start.linkTo(startGuideline)
                        end.linkTo(body.start)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.matchParent
                    }
                },
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
                    .align(
                        alignment = if (isInLandscape) Alignment.CenterHorizontally else Alignment.Start
                    )
                    .then(
                        if (!isInLandscape) {
                            Modifier.padding(start = if (isCompactScreen) 16.dp else 32.dp)
                        } else Modifier
                    )
            )

            if (isInLandscape) {
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
                .constrainAs(body) {
                    if (!isInLandscape) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(header.bottom, margin = 32.dp)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints.atMost(600.dp)
                        height = Dimension.fillToConstraints
                    } else {
                        top.linkTo(parent.top)
                        start.linkTo(header.end)
                        end.linkTo(endGuideline)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints.atMost(600.dp)
                        height = Dimension.matchParent
                    }
                },
            verticalArrangement = Arrangement.spacedBy(
                space = if (isCompactScreen) 12.dp else 16.dp,
                alignment = if (isInLandscape) Alignment.CenterVertically else Alignment.Top
            )
        ) {
            RowIconTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                icon = Icons.Outlined.AccountCircle,
            ) {
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = { onEvent(LoginEvent.OnEmailChange(it)) },
                    isError = uiState.emailError != null,
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
                        .weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    supportingText = {
                        uiState.emailError?.let {
                            Text(
                                text = it.asString()
                            )
                        }
                    }
                )
            }

            RowIconTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                icon = Icons.Outlined.Lock
            ) {
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { onEvent(LoginEvent.OnPasswordChange(it)) },
                    isError = uiState.passwordError != null,
                    visualTransformation = if (uiState.togglePassword) VisualTransformation.None else PasswordVisualTransformation(),
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { onEvent(LoginEvent.OnTogglePassword) }) {
                            Icon(
                                imageVector = if (uiState.togglePassword) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = stringResource(
                                    id = if (uiState.togglePassword) R.string.hide_password_toggle_label else R.string.show_password_toggle_label
                                )
                            )
                        }
                    },
                    supportingText = {
                        uiState.passwordError?.let {
                            Text(
                                text = it.asString()
                            )
                        }
                    }
                )
            }

            Button(
                onClick = {
                    if (!uiState.isLoading) {
                        onEvent(LoginEvent.OnLoginClick)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(18.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .size(8.dp)
                    )
                }
                Text(
                    text = if (uiState.isLoading) stringResource(id = R.string.signing_in)
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
        }

        if (!isInLandscape) {
            Image(
                painter = painterResource(id = R.mipmap.bottom_image_login),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(plant) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(startGuideline)
                    }
                    .size(
                        if (isCompactScreen) 150.dp else 200.dp
                    )
            )
        }
    }
}

@Preview(device = "spec:width=411.4dp,height=731.4dp", showSystemUi = true, showBackground = true)
@Composable
private fun LoginCompactPreview() {
    PlantsCommerceTheme {
        Surface {
            LoginScreen(
                uiState = LoginUiState(),
                isCompactScreen = true,
                onEvent = { /*TODO*/ },
                onRegisterNavigate = { },
                isInLandscape = false,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}


@Preview(
    device = "spec:width=411.4dp,height=731.4dp,orientation=landscape", showSystemUi = true,
    showBackground = true
)
@Composable
private fun LoginCompactLandscapePreview() {
    PlantsCommerceTheme {
        Surface {
            LoginScreen(
                uiState = LoginUiState(),
                isCompactScreen = true,
                onEvent = { /*TODO*/ },
                onRegisterNavigate = { },
                isInLandscape = true,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Preview(device = "id:pixel_c", showSystemUi = true, showBackground = true)
@Composable
private fun LoginMediumPreview() {
    PlantsCommerceTheme {
        Surface {
            LoginScreen(
                uiState = LoginUiState(),
                onEvent = { /*TODO*/ },
                onRegisterNavigate = { },
                isCompactScreen = false,
                isInLandscape = false,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Preview(device = "spec:parent=pixel_c", showBackground = true, showSystemUi = true)
@Composable
private fun LoginLandscapeMediumPreview() {
    PlantsCommerceTheme {
        Surface {
            LoginScreen(
                uiState = LoginUiState(),
                onEvent = { /*TODO*/ },
                onRegisterNavigate = { },
                isCompactScreen = false,
                isInLandscape = true,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}