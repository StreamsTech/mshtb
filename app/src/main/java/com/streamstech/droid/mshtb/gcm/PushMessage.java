package com.streamstech.droid.mshtb.gcm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PushMessage {

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public static enum OperationType { DELETE, ADD, UPDATE, RELOAD};

    private String client;
    private String operation;
    private long id;
    private String sku;
    private String name;
    private boolean success;
    private String message;
    private String rawMessage;
    private String displayMessage;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void parse(String message)
    {
        //QR|DELETE|productid|Prodict Uniqe Id|ProductActivity name
        setRawMessage(message);
        String str[] = message.split("\\|");
        if (str.length < 5) {
            setSuccess(false);
            setMessage("Not a valid push");
            return;
        }
        else if (!str[0].equals("QR")) {
            setSuccess(false);
            setMessage("Not a push for this application");
            return;
        }

        setClient(str[0]);
        setOperation(str[1]);
        setId(Long.valueOf(str[2]));
        setSku(str[3]);
        setName(str[4]);
        setSuccess(true);
        setMessage("Valid push");

        if(getOperation().equalsIgnoreCase("delete"))
            setDisplayMessage("A product has been deleted");
        else if(getOperation().equalsIgnoreCase("add"))
            setDisplayMessage("A product has been added");
        else if(getOperation().equalsIgnoreCase("update"))
            setDisplayMessage("A product has been updated");
        else if(getOperation().equalsIgnoreCase("reload"))
            setDisplayMessage("All product list must be reloaded");
    }
}
