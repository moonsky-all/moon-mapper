package com.moonsky.processing.util;

/**
 * @author benshaoye
 */
public class OnceRunner {

    private volatile boolean processed = false;

    public OnceRunner() { }

    public synchronized void run(Runnable runner) {
        if (processed) {
            return;
        }
        runner.run();
        processed = true;
    }
}
