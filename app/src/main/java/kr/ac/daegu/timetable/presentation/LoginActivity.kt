package kr.ac.daegu.timetable.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kr.ac.daegu.timetable.R
import kr.ac.daegu.timetable.core.base.BaseEvent
import kr.ac.daegu.timetable.core.utils.EventBus
import kr.ac.daegu.timetable.core.utils.setHandleEvent
import kr.ac.daegu.timetable.core.utils.toast
import kr.ac.daegu.timetable.databinding.ActivityLoginBinding
import kr.ac.daegu.timetable.presentation.LoginViewModel.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), EventBus {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        setHandleEvent(this, this, viewModel)
        setLogoImage()
    }

    private fun setLogoImage() {
        Glide.with(this)
            .load(R.drawable.login_logo)
            .into(binding.logo)
    }

    override fun handleEvent(context: Context?, event: BaseEvent) {
        super.handleEvent(context, event)
        when (event) {
            LoginEvent.LoginSuccess -> {

            }
            LoginEvent.LoginFail -> {
                toast(resources.getString(R.string.login_fail))
            }
        }
    }
}