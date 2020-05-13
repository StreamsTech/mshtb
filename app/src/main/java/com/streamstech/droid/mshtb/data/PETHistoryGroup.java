package com.streamstech.droid.mshtb.data;

import java.util.List;

/**
 * Created by AKASH-LAPTOP on 7/2/2018.
 */

public class PETHistoryGroup {
    private String date;
    private int count;
    private List<Object> groupList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Object> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Object> groupList) {
        this.groupList = groupList;
    }
}
