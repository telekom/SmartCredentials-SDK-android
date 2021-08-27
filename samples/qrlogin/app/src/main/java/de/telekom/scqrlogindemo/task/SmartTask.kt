package de.telekom.scqrlogindemo.task

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.Process
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

@SuppressWarnings("All")
class SmartTask {
    companion object {

        val MESSAGE_STOP = 0x65532

        @MainThread
        fun with(activity: AppCompatActivity): Register? {
            getInstance().registerHookToContext(activity)
            return getInstance().buildRegister(activity)
        }

        @MainThread
        fun with(fragment: Fragment): Register? {
            getInstance().registerHookToContext(fragment)
            return getInstance().buildRegister(fragment)
        }

        @WorkerThread
        fun post(message: Message) {
            getInstance().handler.sendMessage(message)
        }

        private fun getInstance(): SmartTask {
            return SugarTaskHolder.INSTANCE
        }
    }

    interface TaskDescription {
        fun onBackground(): Any?
    }

    interface MessageListener {
        fun handleMessage(message: Message)
    }

    interface FinishListener {
        fun onFinish(result: Any?)
    }

    interface BrokenListener {
        fun onBroken(e: Exception)
    }

    private val count = AtomicInteger(0)

    private var holder: Holder? = null

    private val taskMap: MutableMap<Int, TaskDescription> = ConcurrentHashMap()

    private val messageMap: MutableMap<Int, MessageListener> = ConcurrentHashMap()

    private val finishMap: MutableMap<Int, FinishListener> = ConcurrentHashMap()

    private val brokenMap: MutableMap<Int, BrokenListener> = ConcurrentHashMap()

    inner class Register(private val id: Int) {
        @MainThread
        fun assign(description: TaskDescription): Builder {
            taskMap.put(id, description)
            return Builder(id)
        }
    }

    inner class Builder(private val id: Int) {
        @MainThread
        fun handle(listener: MessageListener): Builder {
            messageMap.put(id, listener)
            return this
        }

        @MainThread
        fun finish(listener: FinishListener): Builder {
            finishMap.put(id, listener)
            return this
        }

        @MainThread
        fun broken(listener: BrokenListener): Builder {
            brokenMap.put(id, listener)
            return this
        }

        @MainThread
        fun execute() {
            executor.execute(buildRunnable(id))
        }
    }

    private class Holder(var id: Int, var `object`: Any?)

    val MESSAGE_FINISH = 0x65530

    val MESSAGE_BROKEN = 0x65531

    val TAG_HOOK = "HOOK"

    private val ID_ACTIVITY = 0x65533

    private val ID_FRAGMENT = 0x65535

    class HookFragment : Fragment() {
        var postEnable = true
        override fun onStop() {
            super.onStop()
            if (postEnable) {
                val message = Message()
                message.what = MESSAGE_STOP
                post(message)
            }
        }
    }

    class HookSupportFragment : Fragment() {
        var postEnable = true
        override fun onStop() {
            super.onStop()
            if (postEnable) {
                val message = Message()
                message.what = MESSAGE_STOP
                post(message)
            }
        }
    }

    private object SugarTaskHolder {
        val INSTANCE = SmartTask()
    }

    private val executor: Executor = Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors() * 8
    )

    private val handler = Handler(Looper.getMainLooper()) { message: Message ->
        if (message.what == MESSAGE_FINISH && message.obj is Holder) {
            val result = message.obj as Holder
            taskMap.remove(result.id)
            messageMap.remove(result.id)
            brokenMap.remove(result.id)
            val listener = finishMap.remove(result.id)
            listener?.onFinish(result.`object`)
            getInstance().dispatchUnregister()
        } else if (message.what == MESSAGE_BROKEN && message.obj is Holder) {
            val result = message.obj as Holder
            taskMap.remove(result.id)
            messageMap.remove(result.id)
            finishMap.remove(result.id)
            val listener = brokenMap.remove(result.id)
            listener?.onBroken((result.`object` as Exception?)!!)
            getInstance().dispatchUnregister()
        } else if (message.what == MESSAGE_STOP) {
            resetHolder()
            taskMap.clear()
            messageMap.clear()
            finishMap.clear()
            brokenMap.clear()
        } else {
            for (listener in messageMap.values) {
                listener.handleMessage(message)
            }
        }
        true
    }

    private fun buildRegister(activity: AppCompatActivity): Register? {
        holder = Holder(ID_ACTIVITY, activity)
        return Register(count.getAndIncrement())
    }

    private fun buildRegister(fragment: Fragment): Register? {
        holder = Holder(ID_FRAGMENT, fragment)
        return Register(count.getAndIncrement())
    }

    private fun buildRunnable(id: Int): Runnable? {
        return Runnable {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)
            if (taskMap.containsKey(id)) {
                val message = Message.obtain()
                try {
                    message.what = MESSAGE_FINISH
                    message.obj = Holder(id, taskMap[id]!!.onBackground())
                } catch (e: Exception) {
                    message.what = MESSAGE_BROKEN
                    message.obj = Holder(id, e)
                }
                post(message)
            }
        }
    }

    private fun registerHookToContext(activity: AppCompatActivity) {
        val manager = activity.supportFragmentManager
        var hookFragment = manager.findFragmentByTag(TAG_HOOK) as HookFragment?
        if (hookFragment == null) {
            hookFragment = HookFragment()
            manager.beginTransaction().add(hookFragment, TAG_HOOK).commitAllowingStateLoss()
        }
    }

    private fun registerHookToContext(fragment: Fragment) {
        val manager = fragment.childFragmentManager
        var hookSupportFragment = manager.findFragmentByTag(TAG_HOOK) as HookSupportFragment?
        if (hookSupportFragment == null) {
            hookSupportFragment = HookSupportFragment()
            manager.beginTransaction().add(hookSupportFragment, TAG_HOOK).commitAllowingStateLoss()
        }
    }

    private fun dispatchUnregister() {
        if (holder == null || taskMap.size > 0) {
            return
        }
        if (holder!!.id == ID_ACTIVITY && holder!!.`object` is AppCompatActivity) {
            unregisterHookToContext((holder!!.`object` as AppCompatActivity?)!!)
        } else if (holder!!.id == ID_FRAGMENT && holder!!.`object` is Fragment) {
            unregisterHookToContext((holder!!.`object` as Fragment?)!!)
        }
        resetHolder()
    }

    private fun unregisterHookToContext(activity: AppCompatActivity) {
        val manager = activity.supportFragmentManager
        val hookFragment = manager.findFragmentByTag(TAG_HOOK) as HookFragment?
        if (hookFragment != null) {
            hookFragment.postEnable = false
            manager.beginTransaction().remove(hookFragment).commitAllowingStateLoss()
        }
    }

    private fun unregisterHookToContext(fragment: Fragment) {
        val manager = fragment.childFragmentManager
        val hookSupportFragment = manager.findFragmentByTag(TAG_HOOK) as HookSupportFragment?
        if (hookSupportFragment != null) {
            hookSupportFragment.postEnable = false
            manager.beginTransaction().remove(hookSupportFragment).commitAllowingStateLoss()
        }
    }

    private fun resetHolder() {
        if (holder == null) {
            return
        }
        holder!!.id = 0
        holder!!.`object` = null
        holder = null
    }
}