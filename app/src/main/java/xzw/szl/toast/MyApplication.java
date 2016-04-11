package xzw.szl.toast;

import android.app.Application;
import android.view.Gravity;

import xzw.szl.toast.library.SimpleWindowToast;
import xzw.szl.toast.library.WindowToast;
import xzw.szl.toast.library.WindowToastConfig;

/**
 * Created by shilei on 16/4/6.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        WindowToastConfig config = new WindowToastConfig.Builder(this)
                .animationIn(R.anim.translatein)
                .animationOut(R.anim.translateout)
                .contentView(R.layout.view_toast)
                .subToastClass(SimpleWindowToast.class)
                .gravity(Gravity.TOP)
                .build();

        WindowToast.config(config);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
