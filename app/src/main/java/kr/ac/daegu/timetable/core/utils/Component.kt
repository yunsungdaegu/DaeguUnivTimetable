package kr.ac.daegu.timetable.core.utils

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kr.ac.daegu.timetable.R
import kr.ac.daegu.timetable.core.base.BaseEvent
import kr.ac.daegu.timetable.core.base.BaseViewModel

fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

interface EventBus {
    fun handleEvent(context: Context?, event: BaseEvent) {
        if (event is BaseEvent.NetworkFail)
            context?.toast(context.resources.getString(R.string.network))
        else if (event is BaseEvent.Error)
            context?.toast(String.format(
                context.resources.getString(R.string.error),
                "${event.throwable.cause}: ${event.throwable.message}"
            ))
    }
}

fun Context.toast(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun LifecycleOwner.setHandleEvent(event: EventBus, viewModel: BaseViewModel) =
    setHandleEvent(null, event, viewModel)

fun LifecycleOwner.setHandleEvent(context: Context?, event: EventBus, viewModel: BaseViewModel) {
    repeatOnStarted {
        viewModel.eventFlow.collect { event.handleEvent(context, it) }
    }
}