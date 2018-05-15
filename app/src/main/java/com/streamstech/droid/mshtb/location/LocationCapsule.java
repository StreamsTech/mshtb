package com.streamstech.droid.mshtb.location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 7/5/2016.
 */
public class LocationCapsule {

    private double accuracy;
    private double altitude;
    private float speed;
    private GeoLocation location;
    private float bearing;
    private String provider;
    private long time;
    private long devicetime;
    private List<CellIdData> cellids = new ArrayList<>();

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDevicetime() {
        return devicetime;
    }

    public void setDevicetime(long devicetime) {
        this.devicetime = devicetime;
    }

    public List<CellIdData> getCellids() {
        return cellids;
    }

    public void setCellids(List<CellIdData> cellids) {
        this.cellids = cellids;
    }

    public boolean isExpired()
    {
        if ( ((System.currentTimeMillis() - devicetime) / 1000) >= 30 * 60)
            return true;
        else
            return false;
    }
}
