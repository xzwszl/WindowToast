package xzw.szl.toast.library;

/**
 * Created by shilei on 16/4/6.
 */
public class ToastEvent<T> {
    public long duration;
    public T content;

    public ToastEvent(long duration) {
        this.duration = duration;
    }

    public ToastEvent() {}

    public void setContent(T content) {
        this.content = content;
    }
}
