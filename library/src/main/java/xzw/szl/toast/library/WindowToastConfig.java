package xzw.szl.toast.library;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import xzw.szl.toast.library.policy.CoverPolicy;
import xzw.szl.toast.library.policy.Policy;

/**
 * Created by shilei on 16/4/7.
 */
public class WindowToastConfig {
    private static WindowToastConfig config;
    private WindowToastConfig() {
        mGravity = Gravity.TOP;
    }

    private View mView;
    private Animation mFadeIn;
    private Animation mFadeOut;
    private Policy mPolicy;
    private int mGravity;

    public Class<WindowToast> getSubClass() {
        return mSubClass;
    }

    public void setSubClass(Class<WindowToast> subClass) {
        mSubClass = subClass;
    }

    private Class<WindowToast> mSubClass;


    public int getGravity() {
        return mGravity;
    }

    public void setGravity(int gravity) {
        this.mGravity = mGravity;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        this.mView = mView;
    }

    public Policy getPolicy() {
        return mPolicy;
    }

    public void setPolicy(Policy mPolicy) {
        this.mPolicy = mPolicy;
    }

    public Animation getFadeOut() {
        return mFadeOut;
    }

    public void setFadeOut(Animation mFadeOut) {
        this.mFadeOut = mFadeOut;
    }

    public Animation getFadeIn() {
        return mFadeIn;
    }

    public void setFadeIn(Animation mFadeIn) {
        this.mFadeIn = mFadeIn;
    }

    public static class Builder {

        private Context mContext;
        private WindowToastConfig config;
        public Builder(Context context) {
            mContext = context;
            config = new WindowToastConfig();
        }

        private Builder(){}

        public Builder animationIn(int animIn) {
            Animation animation = AnimationUtils.loadAnimation(mContext, animIn);
            animationIn(animation);
            return this;
        }

        public Builder animationIn(Animation animation) {
            config.setFadeIn(animation);
            return this;
        }

        public Builder animationOut(int animOut) {
            Animation animation = AnimationUtils.loadAnimation(mContext, animOut);
            animationOut(animation);
            return this;
        }

        public Builder animationOut(Animation animation) {
            config.setFadeOut(animation);
            return this;
        }

        public Builder policy(Policy policy) {
            config.setPolicy(policy);
            return this;
        }

        public Builder contentView(int layoutId) {
            View view = LayoutInflater.from(mContext).inflate(layoutId, null);
            contentView(view);
            return this;
        }

        public Builder contentView(View view) {
            config.setView(view);
            return this;
        }

        public Builder subToastClass(Class<WindowToast> cls) {
            config.setSubClass(cls);
            return this;
        }

        /**
         * gravity like GRAVITY.TOP
         * */
        public Builder gravity(int gravity) {
            config.setGravity(gravity);
            return this;
        }

        public WindowToastConfig build() {

            if (config.getFadeIn() == null) {
                throw new IllegalStateException("FadeIn animation should be initialized");
            }

            if (config.getFadeOut() == null) {
                throw new IllegalStateException("FadeOut animation should be initialized");
            }

            if (config.getView() == null) {
                throw new IllegalStateException("Layout should be initialized");
            }

            if (config.getSubClass() == null) {
                throw new IllegalStateException("Toast subClass should be initialized");
            }

            if (config.getPolicy() == null) {
                config.setPolicy(new CoverPolicy());
            }

            return config;
        }
    }
}
