package com.rogerio.viceri_test_tec.presentation.list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogerio.viceri_test_tec.data.local.UserEntity
import com.rogerio.viceri_test_tec.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _query = MutableStateFlow("")
    fun setQuery(q: String) {
        _query.value = q
        setPage(0) // reset page when searching
    }

    private val pageSize = 4
    val _currentPage = MutableStateFlow(0)

    /** Fluxo com todos os usuários filtrados */
    private val filteredUsers: StateFlow<List<UserEntity>> = _query
        .debounce(250)
        .flatMapLatest { q ->
            if (q.isBlank()) repo.observeUsers()
            else repo.search(q)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    /** Lista paginada exibida na tela */
    val users: StateFlow<List<UserEntity>> = combine(filteredUsers, _currentPage) { list, page ->
        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, list.size)

        if (fromIndex >= list.size) emptyList()
        else list.subList(fromIndex, toIndex)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    /** Total de páginas */
    val totalPages: StateFlow<Int> = filteredUsers.map { list ->
        if (list.isEmpty()) 1
        else (list.size + pageSize - 1) / pageSize
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 1)


    fun nextPage() {
        val max = totalPages.value - 1
        if (_currentPage.value < max) _currentPage.value++
    }

    fun prevPage() {
        if (_currentPage.value > 0) _currentPage.value--
    }

    fun setPage(page: Int) {
        _currentPage.value = page
    }

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true

            repo.fetchAndSaveUsers()

            _isLoading.value = false
        }
    }
}
