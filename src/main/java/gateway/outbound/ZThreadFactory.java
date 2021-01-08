package gateway.outbound;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ztz
 * @description TODO
 * @date 2020/10/30 16:49
 */
public class ZThreadFactory implements ThreadFactory {
    private String threadName;
    private AtomicInteger threadNo = new AtomicInteger(1);

    private boolean daemon;
    private ThreadGroup group;

    public ZThreadFactory(String namePerfix) {
        this(namePerfix, false);
    }

    public ZThreadFactory(String namePerfix, boolean daemon) {
        this.daemon = daemon;
        SecurityManager manager = System.getSecurityManager();
        group = (manager != null) ? manager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.threadName = namePerfix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group, r, threadName + "-thread-" + threadNo.getAndIncrement(), 0);
        thread.setDaemon(this.daemon);
        return thread;
    }
}
