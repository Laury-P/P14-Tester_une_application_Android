package com.kirabium.relayance.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.kirabium.relayance.databinding.ActivityAddCustomerBinding
import com.kirabium.relayance.ui.viewModel.AddCustomerViewModel
import com.kirabium.relayance.ui.viewModel.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCustomerBinding
    private val viewModel: AddCustomerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupToolbar()
        setupListeners()
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupBinding() {
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setupListeners() {
        binding.saveFab.setOnClickListener {
            viewModel.saveCustomer()
        }
        binding.nameEditText.doAfterTextChanged {
            viewModel.onNameChanged(it.toString())
        }
        binding.emailEditText.doAfterTextChanged {
            viewModel.onEmailChanged(it.toString())
        }
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is UiState.EmptyNameError -> binding.nameEditText.error = "Veuillez renseigner le nom du client"
                    is UiState.EmptyEmailError -> binding.emailEditText.error = "Veuillez renseigner l'email du client"
                    is UiState.EmptyFieldsError -> {
                        binding.nameEditText.error = "Veuillez remplir le formulaire"
                        binding.emailEditText.error = "Veuillez remplir le formulaire"
                    }
                    is UiState.InvalidEmailError -> binding.emailEditText.error = "Email invalide"
                    is UiState.Success -> {
                        Toast.makeText(this@AddCustomerActivity, "Client ajouté avec succès", Toast.LENGTH_SHORT).show()
                        onBackPressedDispatcher.onBackPressed()
                    }
                    else -> {}
                }
                }
        }
    }

}