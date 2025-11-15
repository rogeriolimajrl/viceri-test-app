package com.rogerio.viceri_test_tec.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogerio.viceri_test_tec.data.local.UserEntity
import com.rogerio.viceri_test_tec.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user

    fun load(userId: Int) {
        viewModelScope.launch {
            try {
                val u = repo.getUserById(userId)
                _user.value = u
            } catch (t: Throwable) {
                // handle
            }
        }
    }
}
