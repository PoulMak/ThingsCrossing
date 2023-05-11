package com.app.thingscrossing.feature_account.presentation.registration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.presentation.registration.components.TextFieldWithValidation
import com.app.thingscrossing.feature_account.presentation.util.AccountScreen
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.Block
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.LoadingDialog
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(stringResource(id = R.string.account)) })
        },
    ) { paddingValues ->
        Box(
            Modifier.padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                LoadingDialog(progression = null)
            }

            LaunchedEffect(key1 = null) {
                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is RegistrationViewModelEvent.NavigateEvent -> {
                            navController.navigate(event.route)
                        }
                    }
                }
            }

            Block(
                title = stringResource(id = R.string.registration), description = stringResource(
                    id = R.string.registration_description
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    FirstNameField(uiState = uiState, viewModel = viewModel)
                    LastNameField(uiState = uiState, viewModel = viewModel)
                    UsernameField(uiState = uiState, viewModel = viewModel)

                    Spacer(modifier = Modifier.padding(10.dp))

                    EmailField(uiState = uiState, viewModel = viewModel)
                    PasswordField(uiState = uiState, viewModel = viewModel)
                    PasswordAgainField(uiState = uiState, viewModel = viewModel)

                    Spacer(modifier = Modifier.padding(10.dp))

                    Button(enabled = uiState.registrationAvailable, onClick = {
                        viewModel.onEvent(RegistrationEvent.SignUp)
                    }) {
                        Text(
                            text = stringResource(id = R.string.make_register),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    TextButton(onClick = { navController.navigate(AccountScreen.LoginScreen.route) }) {
                        Text(text = stringResource(id = R.string.already_have_account))
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FirstNameField(
    uiState: RegistrationState,
    viewModel: RegistrationViewModel
) {
    TextFieldWithValidation(
        value = uiState.firstName,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words
        ),
        onValueChange = { firstName ->
            viewModel.onEvent(
                RegistrationEvent.FirstNameChange(
                    firstName
                )
            )
        },
        label = R.string.first_name_label,
        placeholder = R.string.first_name_placeholder,
        isValid = uiState.isFirstNameValid,
        invalidText = stringResource(id = R.string.first_name_invalid),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LastNameField(
    uiState: RegistrationState,
    viewModel: RegistrationViewModel
) {
    TextFieldWithValidation(
        value = uiState.lastName,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words
        ),
        onValueChange = { lastName ->
            viewModel.onEvent(
                RegistrationEvent.LastNameChange(
                    lastName
                )
            )
        },
        label = R.string.last_name_label,
        placeholder = R.string.last_name_placeholder,
        isValid = uiState.isLastNameValid,
        invalidText = stringResource(id = R.string.last_name_invalid),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UsernameField(
    uiState: RegistrationState,
    viewModel: RegistrationViewModel
) {
    TextFieldWithValidation(
        value = uiState.username,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Ascii
        ),
        onValueChange = { username ->
            viewModel.onEvent(
                RegistrationEvent.UsernameChange(
                    username
                )
            )
        },
        label = R.string.login_label,
        placeholder = R.string.login_placeholder,
        isValid = uiState.isUsernameValid,
        invalidText = stringResource(id = R.string.username_invalid),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmailField(
    uiState: RegistrationState,
    viewModel: RegistrationViewModel
) {
    TextFieldWithValidation(
        value = uiState.email,
        onValueChange = { email ->
            viewModel.onEvent(
                RegistrationEvent.EmailChange(
                    email
                )
            )
        },
        label = R.string.email_label,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        placeholder = R.string.email_placeholder,
        isValid = uiState.isEmailValid,
        invalidText = stringResource(id = R.string.email_invalid)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PasswordField(
    uiState: RegistrationState,
    viewModel: RegistrationViewModel
) {
    TextFieldWithValidation(
        value = uiState.password,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        onValueChange = { password ->
            viewModel.onEvent(
                RegistrationEvent.PasswordChange(
                    password
                )
            )
        },
        label = R.string.password_label,
        placeholder = R.string.password_placeholder,
        isValid = uiState.isPasswordValid,
        invalidText = stringResource(id = R.string.password_invalid),
        trailingIcon = {
            IconButton(onClick = { viewModel.onEvent(RegistrationEvent.ToggleShowPassword) }) {
                Icon(
                    if (uiState.isPasswordVisible) {
                        Icons.Default.VisibilityOff
                    } else {
                        Icons.Default.Visibility
                    },
                    stringResource(id = R.string.toggle_show_password_icon_description)
                )
            }
        },
        visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PasswordAgainField(
    uiState: RegistrationState,
    viewModel: RegistrationViewModel
) {
    TextFieldWithValidation(
        value = uiState.secondPassword,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        onValueChange = { secondPassword ->
            viewModel.onEvent(
                RegistrationEvent.SecondPasswordChange(secondPassword)
            )
        },
        label = R.string.second_password_label,
        placeholder = R.string.password_placeholder,
        isValid = uiState.isSecondPasswordValid,
        invalidText = stringResource(id = R.string.second_password_invalid),
        trailingIcon = {
            IconButton(onClick = { viewModel.onEvent(RegistrationEvent.ToggleShowSecondPassword) }) {
                Icon(
                    if (uiState.isSecondPasswordVisible) {
                        Icons.Default.VisibilityOff
                    } else {
                        Icons.Default.Visibility
                    },
                    stringResource(id = R.string.toggle_show_second_password_icon_description)
                )
            }
        },
        visualTransformation = if (uiState.isSecondPasswordVisible) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}