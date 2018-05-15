package com.streamstech.droid.mshtb.location;

import android.location.Location;

import java.io.Serializable;

public class GPSLocationData implements Serializable
{
    private static final long serialVersionUID = -326456351564604276L;

    private double accuracy;
    private double altitude;
    private float speed;
    private GeoLocation location;
    private float bearing;
    private String provider;
    private long time;
    private long devicetime;

    public long getDevicetime() {
        return devicetime;
    }

    public void setDevicetime(long devicetime) {
        this.devicetime = devicetime;
    }

    private final static char SEPARATOR = '|';

    /**
     *
     * @param longitude is longitude of a point provided by data provider
     * @param latitude is latitude of a point provided by data provider
     * @param altitude is altitude of apoint provided by data provider
     * @param speed is speed of a point provided by data provider
     * @param providerName is the name of data provider
     * @param accuracy is the accuracy of a point provided by data provider
     * @param bearing is the bearing of a point provided by any data provider
     * @param time is the time of a point provided by data provider
     */
    public GPSLocationData(double longitude, double latitude, double altitude, float speed, String providerName, double accuracy, float bearing, long time)
    {
        this.location = new GeoLocation(longitude, latitude);
        this.altitude = altitude;
        this.speed = speed;
        this.provider = providerName;
        this.accuracy = accuracy;
        this.bearing = bearing;
        this.time = time;
    }

    /**
     * Constructor
     * @param GPSLocation is a object of Location
     */
    public GPSLocationData(Location GPSLocation)
    {
        this.location = new GeoLocation(GPSLocation.getLongitude(), GPSLocation.getLatitude());
        this.altitude = GPSLocation.getAltitude();
        if (GPSLocation.getSpeed() < 0) {
            this.speed = 0;
        }
        else {
            this.speed = GPSLocation.getSpeed();
        }
        this.provider = GPSLocation.getProvider();
        this.accuracy = GPSLocation.getAccuracy();
        this.bearing = GPSLocation.getBearing();
        this.time = GPSLocation.getTime();
        this.devicetime = System.currentTimeMillis();
    }

    /**
     * @param longitude is longitude of a point provided by data provider
     * @param latitude is latitude of a point provided by data provider
     * @param time is the time of a point provided by data provider
     */
    public GPSLocationData(double longitude, double latitude,long time)
    {
        this.location = new GeoLocation(longitude, latitude);
        this.altitude = altitude;
        this.time = time;
    }

    public GPSLocationData(double longitude, double latitude, long time, String provider)
    {
        this.location = new GeoLocation(longitude, latitude);
        this.altitude = altitude;
        this.time = time;
        this.provider = provider;
    }
    /**
     * @return the location
     */

    public GeoLocation getLocation()
    {
        return this.location;
    }

    /**
     * @return the altitude
     */
    public double getAltitude()
    {
        return altitude;
    }


    /**
     * @return the speed
     */
    public float getSpeed()
    {
        return speed;
    }

    /**
     * @return the heading
     */
    public String getProviderName()
    {
        return this.provider;
    }

    /**
     *
     * @return Accuracy
     */
    public double getAccuracy()
    {
        return accuracy;
    }

    /**
     * @return the bearing
     */
    public float getBearing()
    {
        return this.bearing;
    }

    /**
     * @return the time
     */

    public long getTime()
    {
        return this.time;
    }

    @Override
    public String toString()
    {
        String str = "XY: " + location.toString() + " | " + "ACCURACY(m): " + accuracy + " | " + "SPEED(km/h): " + (speed * 3.6) +
                " | " + "BEARING: " + bearing + " | " + "ALTITUDE: " + altitude + " | " + "PROVIDER: " + provider;
        return str;
    }


//    public String toReverseGeoocdedFormat()
//    {
//        return location.toStringWithComa();
//    }

//    public String toLogString(CellInfo cellInfo, String networkType, String wayPointsETA, boolean isOnNavigation, boolean isRoaming)
//    {
//        GeolyticManager manager = GeolyticManager.getInstance();
//        StringBuffer sb = new StringBuffer();
//
//        sb.append(isOnNavigation ? GeolyticManager.getInstance().getNavigationSessionId() : "null");
//
//        sb.append(SEPARATOR);
//        sb.append(manager.getApplicationSessionId());
//
//        sb.append(SEPARATOR);
//        sb.append(StringUtil.roundDownNumber(location.getLatitude(), 6));
//
//        sb.append(SEPARATOR);
//        sb.append(StringUtil.roundDownNumber(location.getLongitude(), 6));
//
//        sb.append(SEPARATOR);
//        sb.append(getAltitude());
//
//        sb.append(SEPARATOR);
//        sb.append(getBearing());
//
//        sb.append(SEPARATOR);
//        sb.append(getSpeed() * 3.6); // speed obtained in m/s, convert to km/h
//
//        sb.append(SEPARATOR);
//        sb.append(Utils.getFormattedTime(getDevicetime()/*getTime()*/));
//
//        sb.append(SEPARATOR);
//        sb.append(manager.getCumulativeDistance()); // Horizontal cumulative distance, set as 0, KM
//
//        sb.append(SEPARATOR);
//        sb.append(Utils.getFormattedTime(getDevicetime()));
//
//        sb.append(SEPARATOR);
//        sb.append(cellInfo.getCellID());
//
//        sb.append(SEPARATOR);
//        sb.append(cellInfo.getMNC());
//
//        sb.append(SEPARATOR);
//        sb.append(cellInfo.getMCC());
//
//        sb.append(SEPARATOR);
//        sb.append(cellInfo.getSignalStrength());
//
//        sb.append(SEPARATOR);
//        sb.append(cellInfo.getLAC());
//
//        sb.append(SEPARATOR);
//        sb.append(networkType);
//
//        sb.append(SEPARATOR);
//        sb.append(getAccuracy());
//
//        sb.append(SEPARATOR);
//        sb.append(wayPointsETA);
//
//        sb.append(SEPARATOR);
//        sb.append(""); // ARFCN is only implies for BBOS, so we just pass it as empty string
//
//        sb.append(SEPARATOR);
//        sb.append(isRoaming ? "t" : "f");
//
//        return sb.toString();
//    }
}
