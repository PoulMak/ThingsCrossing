package com.app.thingscrossing.feature_account.presentation.profile

import androidx.annotation.StringRes

data class ProfileState(
    val isLoading: Boolean = false,
    val authKey: String? = null,
    val login: String = "",
    val password: String = "",
    @StringRes val errorMessageId: Int? = null,
)