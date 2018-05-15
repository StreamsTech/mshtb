package com.streamstech.droid.mshtb.data;

/**
 * Created by AKASH-LAPTOP on 8/28/2017.
 */

public class UploadedItem {
    private long id;
    private String message;
    private String imageone;
    private String imagetwo;
    private boolean delivered;
    private long uploadedtime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageone() {
        return imageone;
    }

    public void setImageone(String imageone) {
        this.imageone = imageone;
    }

    public String getImagetwo() {
        return imagetwo;
    }

    public void setImagetwo(String imagetwo) {
        this.imagetwo = imagetwo;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public long getUploadedtime() {
        return uploadedtime;
    }

    public void setUploadedtime(long uploadedtime) {
        this.uploadedtime = uploadedtime;
    }
}
