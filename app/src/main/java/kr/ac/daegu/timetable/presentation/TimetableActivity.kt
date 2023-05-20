package kr.ac.daegu.timetable.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.ac.daegu.timetable.databinding.ActivityTimetableBinding

@AndroidEntryPoint
class TimetableActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimetableBinding
    private val viewModel: TimetableViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimetableBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }
}