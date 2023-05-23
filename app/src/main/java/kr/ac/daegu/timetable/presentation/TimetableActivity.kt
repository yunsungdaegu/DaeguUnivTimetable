package kr.ac.daegu.timetable.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.github.tlaabs.timetableview.Schedule
import com.github.tlaabs.timetableview.Time
import dagger.hilt.android.AndroidEntryPoint
import kr.ac.daegu.timetable.R
import kr.ac.daegu.timetable.core.base.BaseEvent
import kr.ac.daegu.timetable.core.utils.EventBus
import kr.ac.daegu.timetable.core.utils.setHandleEvent
import kr.ac.daegu.timetable.core.utils.toast
import kr.ac.daegu.timetable.databinding.ActivityTimetableBinding
import kr.ac.daegu.timetable.presentation.TimetableViewModel.TimetableEvent.LoadingSuccess

@AndroidEntryPoint
class TimetableActivity : AppCompatActivity(), EventBus {

    private lateinit var binding: ActivityTimetableBinding
    private val viewModel: TimetableViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimetableBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        setHandleEvent(this, this, viewModel)
        setTimetable()
        setSupportActionBar(binding.toolbar)
    }

    override fun handleEvent(context: Context?, event: BaseEvent) {
        super.handleEvent(context, event)
        if (event is LoadingSuccess) {
            toast(resources.getString(R.string.timetable_welcome, event.name))
            setTimetable()
        }
    }

    private fun setTimetable() {
        viewModel.sel2?.let {
            it.forEach {
                binding.timetable.add(arrayListOf(Schedule().apply {
                    classTitle = it.NAME_KR
                    classPlace = it.SUUP_ROOMNAME
                    professorName = it.PROF_NM
                    startTime = Time(it.TM_START_H, it.TM_START_M)
                    endTime = Time(it.TM_END_H, it.TM_END_M)
                    day = it.YOIL.toInt() - 1
                }))
            }
        }
        binding.timetable.setOnStickerSelectEventListener { idx, schedules ->
            val schedule = viewModel.sel2?.get(idx)!!
            Log.d("log", "click schedule ${schedule.NAME_KR}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.timetable, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_opensource -> { // 오픈소스라이선스

            }
            R.id.menu_schedule -> { // 학기 재선택
                startActivity(Intent(this, SemesterActivity::class.java))
                finish()
            }
            R.id.menu_logout -> { // 로그아웃

            }
        }
        return super.onOptionsItemSelected(item)
    }
}