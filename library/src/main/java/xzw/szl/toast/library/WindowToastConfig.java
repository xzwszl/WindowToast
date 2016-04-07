package xzw.szl.toast.library;

import xzw.szl.toast.library.policy.Policy;

/**
 * Created by shilei on 16/4/7.
 */
public class WindowToastConfig {
    private static WindowToastConfig config;
    private WindowToastConfig() {}

    public static final int COVERPOLICY = 1;
    public static final int SEQUENCEPOLICY = 2;
    public static final int UNKNOWNPOLICY = 3;
    public static int curPolicy = 1;
    private static int animIn;
    private static int animOut;
    private static String policyName;
    private static int policyMinSize = 1;
    private static int policyMaxSize = 100;
    private static Policy mPolicy;
    private static int viewId;


    public static int getViewId() {
        return viewId;
    }

    public static WindowToastConfig configViewId(int viewId) {
        if (config == null) {
            config = new WindowToastConfig();
        }
        WindowToastConfig.viewId = viewId;
        return config;
    }

    public static int getCurPolicy() {
        return curPolicy;
    }

    public static WindowToastConfig configCurPolicy(int policy) {
        if (config == null) {
            config = new WindowToastConfig();
        }
        curPolicy = policy;
        return config;
    }

    public static int getAnimIn() {
        return animIn;
    }

    public static WindowToastConfig configAnimIn(int animIn) {
        if (config == null) {
            config = new WindowToastConfig();
        }
        WindowToastConfig.animIn = animIn;
        return config;
    }

    public static int getAnimOut() {
        return animOut;
    }

    public static WindowToastConfig configAnimOut(int animOut) {
        if (config == null) {
            config = new WindowToastConfig();
        }
        WindowToastConfig.animOut = animOut;
        return config;
    }

    public static String getPolicyName() {
        return policyName;
    }

    public static int getPolicyMinSize() {
        return policyMinSize;
    }

    public static WindowToastConfig configPolicyMinSize(int policyMinSize) {
        if (policyMinSize < 1) throw  new IllegalArgumentException("MinSize should be 1 at least");

        if (config == null) {
            config = new WindowToastConfig();
        }
        WindowToastConfig.policyMinSize = policyMinSize;
        return config;
    }

    public static int getPolicyMaxSize() {
        return policyMaxSize;
    }

    public static WindowToastConfig configPolicyMaxSize(int policyMaxSize) {
        if (policyMaxSize < 1) throw  new IllegalArgumentException("MaxSize should be 1 at least");
        if (config == null) {
            config = new WindowToastConfig();
        }
        WindowToastConfig.policyMaxSize = policyMaxSize;
        return config;
    }

    public static WindowToastConfig configPolicy(Policy policy) {
        if (config == null) {
            config = new WindowToastConfig();
        }
        if (policy == null) throw new NullPointerException("Policy could not be null");
        mPolicy = policy;
        policyName = policy.getClass().getName();
        return config;
    }

    public static Policy getPolicy() {
        return mPolicy;
    }
}
