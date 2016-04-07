package xzw.szl.toast;

import android.app.Application;

import xzw.szl.toast.library.WindowToastConfig;

/**
 * Created by shilei on 16/4/6.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        WindowToastConfig.configAnimIn(R.anim.translatein)
                .configAnimOut(R.anim.translateout)
                .configPolicyMinSize(1)
                .configPolicyMaxSize(10)
                .configCurPolicy(WindowToastConfig.SEQUENCEPOLICY)
                .configViewId(R.layout.view_toast);
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
