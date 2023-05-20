package kr.ac.daegu.timetable.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.ac.daegu.timetable.R
import kr.ac.daegu.timetable.core.base.BaseEvent
import kr.ac.daegu.timetable.core.utils.EventBus
import kr.ac.daegu.timetable.core.utils.setHandleEvent
import kr.ac.daegu.timetable.core.utils.toast
import kr.ac.daegu.timetable.databinding.ActivitySemesterBinding
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class SemesterActivity : AppCompatActivity(), EventBus {

    private lateinit var binding: ActivitySemesterBinding
    private val viewModel: SemesterViewModel by viewModels()

    @SuppressLint("SimpleDateFormat")
    val y = SimpleDateFormat("yyyy").format(Date()).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySemesterBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        setSpinner()
        setHandleEvent(this, this, viewModel)

        binding.next.setOnClickListener {
            val s1 = binding.semesterYear.selectedIndex
            val s2 = binding.semester.selectedIndex
            if ((s1 == -1) or (s2 == -1))
                toast(resources.getString(R.string.semester_error))
            else
                viewModel.doNext("${y - s1}", "${s2 + 1}")
        }
    }

    private fun setSpinner() {
        val year = buildList {
            for (i in 0..5)
                add((y-i).toString())
        }
        binding.semesterYear.setItems(year)
    }

    override fun handleEvent(context: Context?, event: BaseEvent) {
        super.handleEvent(context, event)
    }
}