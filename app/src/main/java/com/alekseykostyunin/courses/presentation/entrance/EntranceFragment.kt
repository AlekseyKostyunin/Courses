package com.alekseykostyunin.courses.presentation.entrance

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.alekseykostyunin.courses.R
import com.alekseykostyunin.courses.databinding.FragmentEntranceBinding
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.alekseykostyunin.courses.data.repository.UsersRepositoryImpl
import com.alekseykostyunin.courses.domain.repository.UsersRepository
import com.alekseykostyunin.courses.domain.usecase.AuthUserUseCase
import kotlinx.coroutines.launch


class EntranceFragment : Fragment() {

    private var _binding: FragmentEntranceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntranceBinding.inflate(inflater)

        val repository: UsersRepository = UsersRepositoryImpl
        val authUserUseCase = AuthUserUseCase(repository)
        val viewModel = UsersViewModel(authUserUseCase)

        // Изначально кнопка неактивна
        binding.entranceButton.isEnabled = false
        binding.entranceButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.dark_grey))

        // TextWatcher для отслеживания изменений в поле email
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                if (email.contains("[а-яА-ЯёЁ]".toRegex())) {
                    binding.emailEditText.error = "Кириллица запрещена в email"
                } else {
                    binding.emailEditText.error = null
                }
                viewModel.validateInput(email, password)
            }
        })

        // TextWatcher для отслеживания изменений в поле пароля
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                viewModel.validateInput(email, password)
            }
        })

        // Наблюдаем за ошибками email
        lifecycleScope.launch {
            viewModel.emailError.collect { error ->
                binding.emailEditText.error = error
            }
        }

        // Наблюдаем за ошибками пароля
        lifecycleScope.launch {
            viewModel.passwordError.collect { error ->
                binding.passwordEditText.error = error
            }
        }

        // Наблюдаем за изменениями состояния проверки
        lifecycleScope.launch {
            viewModel.isInputValid.collect { isValid ->
                binding.entranceButton.isEnabled = isValid
                // Меняем цвет фона в зависимости от состояния
                if (isValid) {
                    binding.entranceButton.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), R.color.green)
                    )
                } else {
                    binding.entranceButton.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), R.color.dark_grey)
                    )
                }
            }
        }

        // Обработчик нажатия на кнопку
        binding.entranceButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.authUser(email, password)
            if (viewModel.isAuthorized.value) {
                Toast.makeText(requireContext(), "Вход выполнен!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_entrance_to_list_courses)
            } else {
                Toast.makeText(requireContext(), "Неверный email или пароль!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.buttonVk.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, "https://vk.com/".toUri())
            startActivity(intent)
        }
        binding.buttonOk.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, "https://ok.ru/".toUri())
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}