package xzw.szl.toast.library;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by shilei on 16/4/6.
 */
public class SimpleWindowToast extends WindowToast<TextView> {

    public SimpleWindowToast(Context context) {super(context);}

    @Override
    public void resolveToastEvent(ToastEvent event) {
        if (event == null) {
            throw new NullPointerException("ToastEvent could not be null");
        }
        mView.setText((CharSequence) event.content);
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

        if (mWindowToast == null) {
            mWindowToast = new SimpleWindowToast(context);
            mWindowToast.resolveWindowToastConfig();
        }

        ToastEvent<CharSequence> event = new ToastEvent<>();
        event.content = sequence;
        event.duration = duration;
        mWindowToast.enqueueEvent(event);
    }
}
