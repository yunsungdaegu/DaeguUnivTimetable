package kr.ac.daegu.timetable.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kr.ac.daegu.timetable.R
import kr.ac.daegu.timetable.databinding.ActivityLoginBinding

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.vm = viewModel
        setContentView(binding.root)
        setLogoImage()
    }

    private fun setLogoImage() {
        Glide.with(this)
            .load(R.drawable.login_logo)
            .into(binding.logo)
    }
}