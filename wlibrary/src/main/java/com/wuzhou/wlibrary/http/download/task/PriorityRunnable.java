package com.wuzhou.wlibrary.http.download.task;

 /**
     *  Runnable对象的优先级封装
     *  by jixiang.
     *  date 2016/8/5 17:17.
     */
public class PriorityRunnable extends PriorityObject<Runnable> implements Runnable {

    public PriorityRunnable(Priority priority, Runnable obj) {
        super(priority, obj);
    }

    @Override
    public void run() {
        this.obj.run();
    }
}
