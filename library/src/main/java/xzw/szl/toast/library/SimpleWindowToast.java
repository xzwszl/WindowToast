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
}
