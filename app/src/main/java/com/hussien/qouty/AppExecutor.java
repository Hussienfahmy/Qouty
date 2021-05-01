package com.hussien.qouty;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static AppExecutor appExecutor;
    private static final Object LOCK = new Object();
    private final Executor diskIo;
    private final MainThreadExecutor mainThreadExecutor;

    public static AppExecutor getInstance(){
        if (appExecutor == null){
            synchronized (LOCK){
                if (appExecutor == null){
                    appExecutor = new AppExecutor(Executors.newSingleThreadExecutor(), new MainThreadExecutor());
                }
            }
        }
        return appExecutor;
    }

    public AppExecutor(Executor diskIo, MainThreadExecutor mainThreadExecutor) {
        this.diskIo = diskIo;
        this.mainThreadExecutor = mainThreadExecutor;
    }

    public Executor getDiskIo() {
        return diskIo;
    }

    public MainThreadExecutor getMainThreadExecutor() {
        return mainThreadExecutor;
    }

    public static class MainThreadExecutor implements Executor {

        private final Handler handler = new Handler(Looper.getMainLooper());

        private MainThreadExecutor(){}

        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    }
}