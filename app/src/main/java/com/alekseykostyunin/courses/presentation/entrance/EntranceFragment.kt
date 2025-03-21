package com.alekseykostyunin.courses.presentation.entrance

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.alekseykostyunin.courses.R
import com.alekseykostyunin.courses.databinding.FragmentEntranceBinding
import androidx.core.net.toUri


class EntranceFragment : Fragment() {

    private var _binding: FragmentEntranceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntranceBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.entranceButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password =  binding.passwordEditText.text.toString()
            if (isEmailValid(email) && isPasswordValid(password)) {
                Toast.makeText(requireContext(), "Вход выполнен!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_entrance_to_list_courses)
            } else {
                if (!isEmailValid(email)) {
                    binding.emailEditText.error = "Введите корректный email"
                }
                if (!isPasswordValid(password)) {
                    binding.passwordEditText.error = "Пароль должен содержать не менее 6 символов"
                }
            }
        }

        binding.buttonVk.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, "https://vk.com/".toUri())
            startActivity(intent)
        }
        binding.buttonOk.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, "https://ok.ru/".toUri())
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6 // Пример: пароль должен быть не менее 6 символов
    }

}