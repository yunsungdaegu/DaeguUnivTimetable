package kr.ac.daegu.timetable.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.tlaabs.timetableview.Schedule
import com.github.tlaabs.timetableview.Time
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ac.daegu.timetable.R
import kr.ac.daegu.timetable.core.base.BaseEvent
import kr.ac.daegu.timetable.core.utils.EventBus
import kr.ac.daegu.timetable.core.utils.setHandleEvent
import kr.ac.daegu.timetable.core.utils.toast
import kr.ac.daegu.timetable.data.login.repository.datasource.StudentDataStore
import kr.ac.daegu.timetable.databinding.ActivityTimetableBinding
import kr.ac.daegu.timetable.domain.login.model.Student
import kr.ac.daegu.timetable.presentation.TimetableViewModel.TimetableEvent.LoadingSuccess
import javax.inject.Inject

@AndroidEntryPoint
class TimetableActivity : AppCompatActivity(), EventBus {

    private lateinit var binding: ActivityTimetableBinding
    private val viewModel: TimetableViewModel by viewModels()

    val location = HashMap<String, Pair<String, String>>()
    init{
        location.apply {
            put("인문대1", Pair("35.898338194614794", "128.85007428752198"))
            put("인문대2", Pair("35.89884431893771", "128.85026880648877"))
            put("법행대1", Pair("35.902672259404056", "128.84364615993678"))
            put("법행대2", Pair("35.902672259404056", "128.84364615993678"))
            put("경영대", Pair("35.90094564384539", "128.85088533760504"))
            put("경영대강당동", Pair("35.900751797981464", "128.85162286500622"))
            put("종합", Pair("35.90177806646565", "128.84260924533748"))
            put("사회대1", Pair("35.90139718930187", "128.84276654454627"))
            put("사회대2", Pair("35.90126487483562", "128.84228723660604"))
            put("과생대2", Pair("35.89945814135824", "128.84829788907544"))
            put("과생대1", Pair("35.89910879770903", "128.84816240180245"))
            put("과생대3-1", Pair("35.899776383697734", "128.8484049664283"))
            put("과생대3-2", Pair("35.90003539341992", "128.8485549676097"))
            put("과생대3-3", Pair("35.90028089124027", "128.84870465548667"))
            put("공과대본", Pair("35.89911538752586", "128.85518412159456"))
            put("공과대1", Pair("35.89908623805201", "128.8556125979842"))
            put("공과대2", Pair("35.89908706822092", "128.85453834086778"))
            put("공과대3", Pair("35.89959849732557", "128.85555533917264"))
            put("공과대6", Pair("35.90019189645361", "128.8556411825964"))
            put("정통대1", Pair("35.89966477601667", "128.85476501728837"))
            put("정통대2", Pair("35.90019737353843", "128.85441196656475"))
            put("교수학습", Pair("35.89969540766611", "128.85059041757071"))
            put("과생대5", Pair("35.902855621975505", "128.8558695164331"))
            put("과생대6", Pair("35.90218389873355", "128.8570610523032"))
            put("조예대1", Pair("35.90223677349487", "128.84457193427832"))
            put("조예대2", Pair("35.90298796941333", "128.8456775469145"))
            put("조예대5", Pair("35.8988567091404", "128.84697705237528"))
            put("조예대3", Pair("35.8988567091404", "128.84697705237528"))
            put("사범대1", Pair("35.90010749097452", "128.84650496887943"))
            put("사범대2", Pair("35.900460987775794", "128.84549149272843"))
            put("재과대1", Pair("35.89999699623858", "128.85294537182048"))
            put("재과대2", Pair("35.89999699623858", "128.85294537182048"))
        }
    }

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
            val room = schedule.SUUP_ROOMNAME.split("-")[0]
            if (location[room] == null)
                toast("강의실을 찾을 수 없습니다")
            else {
                val uri = Uri.parse("geo: ${location[room]?.first},${location[room]?.second}?z=18")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.timetable, menu)
        return true
    }

    @Inject
    lateinit var dataStore: StudentDataStore
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_opensource -> { // 오픈소스라이선스

            }
            R.id.menu_schedule -> { // 학기 재선택
                startActivity(Intent(this, SemesterActivity::class.java))
                finish()
            }
            R.id.menu_logout -> { // 로그아웃
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        dataStore.saveStudent(Student("", ""))
                    }.runCatching {
                    }.onSuccess {
                        moveTaskToBack(true)
                        finishAndRemoveTask()
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }
}