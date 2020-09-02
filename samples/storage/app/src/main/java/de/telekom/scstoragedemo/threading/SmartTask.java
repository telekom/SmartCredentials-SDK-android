package de.telekom.scstoragedemo.threading;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Alex.Graur@endava.com at 9/2/2020
 */
public class SmartTask {

    public interface TaskDescription {
        Object onBackground();
    }

    public interface MessageListener {
        void handleMessage(@NonNull Message message);
    }

    public interface FinishListener {
        void onFinish(@Nullable Object result);
    }

    public interface BrokenListener {
        void onBroken(@NonNull Exception e);
    }

    public class Register {
        private Integer id;

        private Register(@NonNull Integer id) {
            this.id = id;
        }

        @MainThread
        public SmartTask.Builder assign(@NonNull TaskDescription description) {
            taskMap.put(id, description);

            return new Builder(id);
        }
    }

    public class Builder {
        private Integer id;

        private Builder(@NonNull Integer id) {
            this.id = id;
        }


        @MainThread
        public SmartTask.Builder handle(@NonNull MessageListener listener) {
            messageMap.put(id, listener);

            return this;
        }

        @MainThread
        public SmartTask.Builder finish(@NonNull FinishListener listener) {
            finishMap.put(id, listener);

            return this;
        }

        @MainThread
        public SmartTask.Builder broken(@NonNull BrokenListener listener) {
            brokenMap.put(id, listener);

            return this;
        }

        @MainThread
        public void execute() {
            executor.execute(buildRunnable(id));
        }
    }

    private static class Holder {
        private Integer id;

        private Object object;

        private Holder(@NonNull Integer id, @Nullable Object object) {
            this.id = id;
            this.object = object;
        }
    }

    public static final int MESSAGE_FINISH = 0x65530;

    public static final int MESSAGE_BROKEN = 0x65531;

    public static final int MESSAGE_STOP = 0x65532;

    public static final String TAG_HOOK = "HOOK";

    private static final Integer ID_ACTIVITY = 0x65533;

    private static final Integer ID_FRAGMENT = 0x65535;

    public static class HookFragment extends Fragment {
        protected boolean postEnable = true;

        @Override
        public void onStop() {
            super.onStop();

            if (postEnable) {
                Message message = new Message();
                message.what = MESSAGE_STOP;
                post(message);
            }
        }
    }

    public static class HookSupportFragment extends androidx.fragment.app.Fragment {
        protected boolean postEnable = true;

        @Override
        public void onStop() {
            super.onStop();

            if (postEnable) {
                Message message = new Message();
                message.what = MESSAGE_STOP;
                post(message);
            }
        }
    }

    @MainThread
    public static Register with(@NonNull AppCompatActivity activity) {
        getInstance().registerHookToContext(activity);

        return getInstance().buildRegister(activity);
    }

    @MainThread
    public static Register with(@NonNull Fragment fragment) {
        getInstance().registerHookToContext(fragment);

        return getInstance().buildRegister(fragment);
    }

    @WorkerThread
    public static void post(@NonNull Message message) {
        getInstance().handler.sendMessage(message);
    }

    private static class SugarTaskHolder {
        public static final SmartTask INSTANCE = new SmartTask();
    }

    private static SmartTask getInstance() {
        return SugarTaskHolder.INSTANCE;
    }

    private static AtomicInteger count = new AtomicInteger(0);

    private Holder holder = null;

    private Map<Integer, TaskDescription> taskMap = new ConcurrentHashMap<>();

    private Map<Integer, MessageListener> messageMap = new ConcurrentHashMap<>();

    private Map<Integer, FinishListener> finishMap = new ConcurrentHashMap<>();

    private Map<Integer, BrokenListener> brokenMap = new ConcurrentHashMap<>();

    private Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 8);

    private Handler handler = new Handler(Looper.getMainLooper(), message -> {
        if (message.what == MESSAGE_FINISH && message.obj instanceof Holder) {
            Holder result = (Holder) message.obj;

            taskMap.remove(result.id);
            messageMap.remove(result.id);
            brokenMap.remove(result.id);

            FinishListener listener = finishMap.remove(result.id);
            if (listener != null) {
                listener.onFinish(result.object);
            }

            getInstance().dispatchUnregister();
        } else if (message.what == MESSAGE_BROKEN && message.obj instanceof Holder) {
            Holder result = (Holder) message.obj;

            taskMap.remove(result.id);
            messageMap.remove(result.id);
            finishMap.remove(result.id);

            BrokenListener listener = brokenMap.remove(result.id);
            if (listener != null) {
                listener.onBroken((Exception) result.object);
            }

            getInstance().dispatchUnregister();
        } else if (message.what == MESSAGE_STOP) {
            resetHolder();

            taskMap.clear();
            messageMap.clear();
            finishMap.clear();
            brokenMap.clear();
        } else {
            for (MessageListener listener : messageMap.values()) {
                listener.handleMessage(message);
            }
        }

        return true;
    });

    private Register buildRegister(@NonNull AppCompatActivity activity) {
        holder = new Holder(ID_ACTIVITY, activity);

        return new Register(count.getAndIncrement());
    }

    private Register buildRegister(@NonNull Fragment fragment) {
        holder = new Holder(ID_FRAGMENT, fragment);

        return new Register(count.getAndIncrement());
    }

    private Runnable buildRunnable(@NonNull final Integer id) {
        return () -> {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            if (taskMap.containsKey(id)) {
                Message message = Message.obtain();

                try {
                    message.what = MESSAGE_FINISH;
                    message.obj = new Holder(id, taskMap.get(id).onBackground());
                } catch (Exception e) {
                    message.what = MESSAGE_BROKEN;
                    message.obj = new Holder(id, e);
                }

                post(message);
            }
        };
    }

    private void registerHookToContext(@NonNull AppCompatActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();

        HookFragment hookFragment = (HookFragment) manager.findFragmentByTag(TAG_HOOK);
        if (hookFragment == null) {
            hookFragment = new HookFragment();
            manager.beginTransaction().add(hookFragment, TAG_HOOK).commitAllowingStateLoss();
        }
    }

    private void registerHookToContext(@NonNull Fragment fragment) {
        androidx.fragment.app.FragmentManager manager = fragment.getChildFragmentManager();

        HookSupportFragment hookSupportFragment = (HookSupportFragment) manager.findFragmentByTag(TAG_HOOK);
        if (hookSupportFragment == null) {
            hookSupportFragment = new HookSupportFragment();
            manager.beginTransaction().add(hookSupportFragment, TAG_HOOK).commitAllowingStateLoss();
        }
    }

    private void dispatchUnregister() {
        if (holder == null || taskMap.size() > 0) {
            return;
        }

        if (holder.id.equals(ID_ACTIVITY) && holder.object instanceof AppCompatActivity) {
            unregisterHookToContext((AppCompatActivity) holder.object);
        } else if (holder.id.equals(ID_FRAGMENT) && holder.object instanceof Fragment) {
            unregisterHookToContext((Fragment) holder.object);
        }

        resetHolder();
    }

    private void unregisterHookToContext(@NonNull AppCompatActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();

        HookFragment hookFragment = (HookFragment) manager.findFragmentByTag(TAG_HOOK);
        if (hookFragment != null) {
            hookFragment.postEnable = false;
            manager.beginTransaction().remove(hookFragment).commitAllowingStateLoss();
        }
    }

    private void unregisterHookToContext(@NonNull Fragment fragment) {
        FragmentManager manager = fragment.getChildFragmentManager();

        HookSupportFragment hookSupportFragment = (HookSupportFragment) manager.findFragmentByTag(TAG_HOOK);
        if (hookSupportFragment != null) {
            hookSupportFragment.postEnable = false;
            manager.beginTransaction().remove(hookSupportFragment).commitAllowingStateLoss();
        }
    }

    private void resetHolder() {
        if (holder == null) {
            return;
        }

        holder.id = 0;
        holder.object = null;
        holder = null;
    }
}
