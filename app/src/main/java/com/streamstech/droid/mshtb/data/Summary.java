package com.streamstech.droid.mshtb.data;

/**
 * Created by AKASH-LAPTOP on 8/6/2017.
 */

public class Summary {
    private int totalTask;
    private int toDo;
    private int overdueTask;

    public int getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(int totalTask) {
        this.totalTask = totalTask;
    }

    public int getToDo() {
        return toDo;
    }

    public void setToDo(int toDo) {
        this.toDo = toDo;
    }

    public int getOverdueTask() {
        return overdueTask;
    }

    public void setOverdueTask(int overdueTask) {
        this.overdueTask = overdueTask;
    }
}
