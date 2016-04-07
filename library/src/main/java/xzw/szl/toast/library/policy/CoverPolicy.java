package xzw.szl.toast.library.policy;

import java.util.Queue;

import xzw.szl.toast.library.ToastEvent;

/**
 * Created by shilei on 16/4/6.
 */
public class CoverPolicy extends Policy{

    public CoverPolicy() {super();}

    @Override
    public ToastEvent nextEvent(Queue<ToastEvent> queue) {
        if (queue == null || queue.isEmpty()) {
            throw new IllegalArgumentException("ToastEvent Queue could not be empty");
        }

        while (queue.size() > minSize) {
            queue.poll();
        }
        return queue.poll();
    }
}
