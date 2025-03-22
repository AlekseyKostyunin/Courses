package com.alekseykostyunin.courses.presentation.entrance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.alekseykostyunin.courses.domain.usecase.AuthUserUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val authUserUseCase: AuthUserUseCase
) : ViewModel() {

    private var _isAuthorized = MutableStateFlow(false)
    val isAuthorized: StateFlow<Boolean> = _isAuthorized

    // Используем StateFlow для хранения состояния проверки
    private val _isInputValid = MutableStateFlow(false)
    val isInputValid: StateFlow<Boolean> get() = _isInputValid

    // Сообщения об ошибках
    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> get() = _emailError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> get() = _passwordError

    // Переменная для отслеживания текущей задачи проверки
    private var validationJob: Job? = null

    fun authUser(email: String, password: String) {
        _isAuthorized.value = authUserUseCase.invoke(email, password)
    }

    // Функция для проверки ввода
    fun validateInput(email: String, password: String) {
        // Отменяем предыдущую задачу, если она существует
        validationJob?.cancel()

        // Запускаем новую задачу с задержкой
        validationJob = viewModelScope.launch {
            // Задержка 500 мс (можно настроить)
            delay(2000)

            val isEmailValid = isEmailValid(email)
            val isPasswordValid = isPasswordValid(password)

            // Устанавливаем сообщения об ошибках
            _emailError.value = if (isEmailValid) null else "Введите корректный email"
            _passwordError.value = if (isPasswordValid) null else "Пароль должен содержать не менее 6 символов"

            // Обновляем состояние проверки
            _isInputValid.value = isEmailValid && isPasswordValid
        }
    }

    // Проверка email
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Проверка пароля
    fun isPasswordValid(password: String): Boolean {
        return password.length >= 6 // Пример: пароль должен быть не менее 6 символов
    }

}