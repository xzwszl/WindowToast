package xzw.szl.toast.library.policy;

import java.util.Queue;

import xzw.szl.toast.library.ToastEvent;

/**
 * Created by shilei on 16/4/6.
 */
public class SequencePolicy extends Policy {

    public SequencePolicy() {super();}

    @Override
    public ToastEvent nextEvent(Queue<ToastEvent> queue) {
        if (queue == null || queue.isEmpty()) {
            throw new IllegalArgumentException("ToastEvent Queue could not be empty");
        }
        return queue.poll();
    }
}
