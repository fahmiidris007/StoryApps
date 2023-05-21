package com.fahmiproduction.storyapps.ui

import android.content.Intent
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
import com.fahmiproduction.storyapps.databinding.FragmentLoginBinding
import com.fahmiproduction.storyapps.ui.customview.ButtonCustom
import com.fahmiproduction.storyapps.ui.customview.EmailCustom
import com.fahmiproduction.storyapps.ui.customview.PasswordCustom
import com.fahmiproduction.storyapps.viewmodel.AuthViewModel
import com.fahmiproduction.storyapps.viewmodel.ViewModelFactory


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var buttonCustom: ButtonCustom
    private lateinit var emailCustom: EmailCustom
    private lateinit var passwordCustom: PasswordCustom


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
        setLogin()
        setRegister()
    }

    private fun setButton(){
        buttonCustom = requireView().findViewById(R.id.btn_login)
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

    private fun setLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val factory = ViewModelFactory.getInstance(requireActivity())
            val authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

            authViewModel.login(email, password).observe(viewLifecycleOwner) { login ->
                if (login.error) {
                    Toast.makeText(requireContext(), login.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(),
                        getString(R.string.login_success),
                        Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setRegister() {
        binding.btnRegister.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.login_to_register)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}