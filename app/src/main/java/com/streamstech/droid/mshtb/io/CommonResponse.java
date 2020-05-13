package com.streamstech.droid.mshtb.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/14/2016.
 */
public class CommonResponse {
    private int status;
    private String message;
    private int pagescount;
    private int pagesno;
    private int pagesize;
    private int totalitems;
    private long associatedid;
    private String headermessage;
    private List<Object> results = new ArrayList<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPagescount() {
        return pagescount;
    }

    public void setPagescount(int pagescount) {
        this.pagescount = pagescount;
    }

    public int getPagesno() {
        return pagesno;
    }

    public void setPagesno(int pagesno) {
        this.pagesno = pagesno;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotalitems() {
        return totalitems;
    }

    public void setTotalitems(int totalitems) {
        this.totalitems = totalitems;
    }

    public long getAssociatedid() {
        return associatedid;
    }

    public void setAssociatedid(long associatedid) {
        this.associatedid = associatedid;
    }

    public List<Object> getResults() {
        return results;
    }

    public void setResults(List<Object> results) {
        this.results = results;
    }

    public String getHeadermessage() {
        return headermessage;
    }

    public void setHeadermessage(String headermessage) {
        this.headermessage = headermessage;
    }
}
