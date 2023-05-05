package com.app.thingscrossing.feature_account.presentation.registration

import androidx.annotation.StringRes

data class RegistrationState(
    val isLoading: Boolean = false,
    val authKey: String? = null,
    val login: String = "",
    val email: String = "",
    val password: String = "",
    val secondPassword: String = "",
    @StringRes val errorMessageId: Int? = null,
)