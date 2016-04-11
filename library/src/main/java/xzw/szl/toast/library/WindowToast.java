package xzw.szl.toast.library;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import xzw.szl.toast.library.policy.Policy;

/**
 * Created by shilei on 16/4/5.
 */
public abstract class WindowToast<T extends View>{

    protected T mView;
    private WindowManager.LayoutParams mLayoutParams;
    private Context mContext;
    private WindowManager mManager;
    private boolean mAdded;
    private ViewGroup mLayout;
    private Queue<ToastEvent> mQueue;
    private boolean isShowing;
    private Policy mPolicy;
    private ToastEvent mCurEvent;
    private static WindowToastConfig mConfig;

    public static final long DEFAULT_DURATION = 1000;
    protected static WindowToast mWindowToast;

    public WindowToast(Context context) {
        if (context == null) {
            throw new NullPointerException("Context can not be null");
        }
        mContext = context;
        mManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mLayoutParams.gravity = Gravity.TOP;
        mAdded = false;
        mLayout = new FrameLayout(context);
        mManager.addView(mLayout, mLayoutParams);
        mAdded = true;
        isShowing = false;
        mQueue = new LinkedList<>();
    }

    public static void config(WindowToastConfig config) {
        mConfig = config;
    }



    public WindowToast setPolicy(Policy policy) {
        if (policy == null) {
            throw new NullPointerException("Policy could not be null");
        }
        mPolicy = policy;
        return this;
    }

    public static void showToast(Context context, CharSequence sequence) {
        showToast(context, sequence, DEFAULT_DURATION);
    }

    public static void showToast(Context context, CharSequence sequence, long duration) {
        Log.d("show", "showToast");
        if (sequence == null) {
            throw new NullPointerException("CharSequence could not be null");
        }

        if (duration <= 0) {
            throw new IllegalArgumentException("duration must be bigger than 0");
        }

        if (mConfig == null) {
            throw new IllegalStateException("Config could not be null");
        }

        if (!(mConfig.getView() instanceof TextView)) {
            throw new IllegalStateException("Config could not be null");
        }

        if (mWindowToast == null) {
            if (mConfig == null) {
                throw new IllegalStateException("Config could not be null");
            }
            try {
                mWindowToast = (WindowToast) mConfig.getSubClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new IllegalStateException("SubClass could not be created");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalStateException("SubClass could not be created");
            }
        }

        ToastEvent<CharSequence> event = new ToastEvent<>();
        event.content = sequence;
        event.duration = duration;
        mWindowToast.enqueueEvent(event);
    }


    private Animation.AnimationListener inAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            Log.d("show", "inAnim start");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mLayout.postDelayed(hideRunnable, mCurEvent.duration);
            Log.d("show", "inAnim end");
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private Animation.AnimationListener outAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            Log.d("show", "outAnim start");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.d("show", "outAnim end " + mQueue.isEmpty());
            mView.setVisibility(View.GONE);

            if (isAppIsInBackground(mContext) || mQueue.isEmpty()) {
                mManager.removeViewImmediate(mLayout);
                mAdded = false;
                isShowing = false;
                return;
            }

            show();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    public void show() {

        if (isAppIsInBackground(mContext)) {
            mQueue.clear();
            return;
        }
        if (!mAdded) {
            mManager.addView(mLayout, mLayoutParams);
        }
        if (mLayout.getChildCount() == 0) {
            mLayout.addView(mView);
        }
        Log.d("toast", "show");
        mCurEvent = mPolicy.nextEvent(mQueue);
        resolveToastEvent(mCurEvent);
        mView.setVisibility(View.VISIBLE);
        mView.startAnimation(mConfig.getFadeIn());
        mAdded = true;
        isShowing = true;
    }

    public abstract void resolveToastEvent(ToastEvent event);

    public void hide() {
        if (mAdded) {
            mView.clearAnimation();
            mView.startAnimation(mConfig.getFadeOut());
        }
    }
    public boolean isAdded() {
        return mAdded;
    }


    public void enqueueEvent(ToastEvent event) {
        mPolicy.enqueueEvent(mQueue, event);
        tryToShow();
    }

    private void tryToShow() {
        Log.d("show", "tryToShow  " + isShowing);
        if (!isShowing) {
            show();
        }
    }

    public boolean isAppIsInBackground(final Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                            break;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }
}

