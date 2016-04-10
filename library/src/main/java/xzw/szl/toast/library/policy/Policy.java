package xzw.szl.toast.library.policy;

import java.util.Queue;

import xzw.szl.toast.library.ToastEvent;

/**
 * Created by shilei on 16/4/6.
 */
public abstract class Policy {

    private int maxSize;
    protected int minSize;

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public Policy() {}
    public Policy(int maxSize, int minSize) {
        this.maxSize = maxSize;
        this.minSize = minSize;
    }

    public abstract ToastEvent nextEvent(Queue<ToastEvent> queue);

    public void enqueueEvent(Queue<ToastEvent> queue, ToastEvent event) {
        if (queue.size() > maxSize) {
            queue.poll();
        }
        queue.offer(event);
    }
}
