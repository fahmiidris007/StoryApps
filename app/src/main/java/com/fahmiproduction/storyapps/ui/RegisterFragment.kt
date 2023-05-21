package com.fahmiproduction.storyapps.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fahmiproduction.storyapps.R
import com.fahmiproduction.storyapps.databinding.FragmentRegisterBinding
import com.fahmiproduction.storyapps.ui.customview.ButtonCustom
import com.fahmiproduction.storyapps.ui.customview.EmailCustom
import com.fahmiproduction.storyapps.ui.customview.PasswordCustom
import com.fahmiproduction.storyapps.viewmodel.AuthViewModel
import com.fahmiproduction.storyapps.viewmodel.ViewModelFactory


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var buttonCustom: ButtonCustom
    private lateinit var emailCustom: EmailCustom
    private lateinit var passwordCustom: PasswordCustom


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
        setRegister()
        setLogin()
    }

    private fun setButton() {
        buttonCustom = requireView().findViewById(R.id.btn_register)
        emailCustom = requireView().findViewById(R.id.et_email)
        passwordCustom = requireView().findViewById(R.id.et_password)
        buttonCustom.isEnabled = false
        emailCustom.addTextChangedListener {
            setButtonEnable()
        }
        passwordCustom.addTextChangedListener {
            setButtonEnable()
        }
    }

    private fun setButtonEnable() {
        val email = emailCustom.text
        val password = passwordCustom.text
        buttonCustom.isEnabled = (email != null && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()) && (password != null && password.toString().length >= 8)
    }

    private fun setRegister() {
        binding.btnRegister.setOnClickListener {
            val userName = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val factory = ViewModelFactory.getInstance(requireActivity())
            val authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

            authViewModel.register(userName, email, password)
                .observe(viewLifecycleOwner) { register ->
                    if (register.error) {
                        Toast.makeText(requireContext(), register.message, Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(requireContext(),
                            getString(R.string.register_success),
                            Toast.LENGTH_SHORT).show()
                        setLogin()
                    }
                }
        }
    }

    private fun setLogin() {
        binding.btnLogin.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.register_to_login)
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}